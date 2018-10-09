package com.example.nooneschool.home;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.HomeActivity;
import com.example.nooneschool.R;
import com.example.nooneschool.R.layout;
import com.example.nooneschool.SmoothListView.SmoothListView;
import com.example.nooneschool.home.adapter.AdapterFood;
import com.example.nooneschool.home.adapter.AdapterMeal;
import com.example.nooneschool.home.list.ListAd;
import com.example.nooneschool.home.list.ListFood;
import com.example.nooneschool.home.list.ListMeal;
import com.example.nooneschool.util.ColorUtil;
import com.example.nooneschool.util.DataUtil;
import com.example.nooneschool.util.DensityUtil;
import com.example.nooneschool.util.SQLite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

public class OtherFoodActivity extends Activity implements SmoothListView.ISmoothListViewListener{

	private static SQLite helper;
	
	private LinearLayout back;
	private SmoothListView smoothListView;
	private BannerView headerBannerView;
	private List<ListAd> bannerList;
	private List<ListMeal> mealList;
	private View itemHeaderBannerView;

	private AdapterMeal mealadapter;
	
	private int titleViewHeight = 65;
	private int bannerViewTopMargin;
	private int bannerViewHeight = 180;
	private boolean isScrollIdle = true;
	
	private ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(3, 5,1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128));
	private Runnable getMealDataRunnable;
	private Runnable getAdDataRunnable;
	private Runnable refreshMealDataRunnable;
	private Boolean flag = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_food);
		
		back = (LinearLayout) findViewById(R.id.food_back_ll);
		smoothListView = (SmoothListView) findViewById(R.id.food_function_sv);
		helper = new SQLite(this);
		bannerList = DataUtil.getBannerData(this);
		headerBannerView = new BannerView(OtherFoodActivity.this);
		headerBannerView.fillView(bannerList, smoothListView);
		headerBannerView.getAdapt().setFlag(false);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		mealList = DataUtil.getNoDataMeal(DensityUtil.getWindowHeight(this) - DensityUtil.dip2px(this, 300));
		mealadapter = new AdapterMeal(this, mealList);
		smoothListView.setAdapter(mealadapter);

		smoothListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (!((ListMeal) parent.getAdapter().getItem(position)).getIsNoData()&&flag) {

					String mealid = ((ListMeal) parent.getAdapter().getItem(position)).getId();
					String imgurl = ((ListMeal) parent.getAdapter().getItem(position)).getImgurl();
					String name = ((ListMeal) parent.getAdapter().getItem(position)).getName();
					Intent intent = new Intent(OtherFoodActivity.this, MealActivity.class);
					intent.putExtra("id", mealid);
					intent.putExtra("imgurl", imgurl);
					intent.putExtra("name", name);
					startActivity(intent);
				}

			}
		});
		smoothListView.setRefreshEnable(true);
		smoothListView.setLoadMoreEnable(false);
		smoothListView.setSmoothListViewListener(this);

		smoothListView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {
			@Override
			public void onSmoothScrolling(View view) {
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (isScrollIdle && bannerViewTopMargin < 0)
					return;

				// 获取广告头部View、自身的高度、距离顶部的高度
				if (itemHeaderBannerView == null) {
					itemHeaderBannerView = smoothListView.getChildAt(1);
				}
				if (itemHeaderBannerView != null) {
					bannerViewTopMargin = DensityUtil.px2dip(OtherFoodActivity.this, itemHeaderBannerView.getTop());
					bannerViewHeight = DensityUtil.px2dip(OtherFoodActivity.this, itemHeaderBannerView.getHeight());
				}
				// 处理标题栏颜色渐变
				handleTitleBarColorEvaluate();
			}
		});
		
		onRefresh();
		
	}
	private void handleTitleBarColorEvaluate() {
		float fraction;
		if (bannerViewTopMargin > 0) {
			fraction = 1f - bannerViewTopMargin * 1f / 60;
			if (fraction < 0f)
				fraction = 0f;
			back.setAlpha(fraction);
			return;
		}

		float space = Math.abs(bannerViewTopMargin) * 1f;
		fraction = space / (bannerViewHeight - titleViewHeight);
		if (fraction < 0f)
			fraction = 0f;
		if (fraction > 1f)
			fraction = 1f;
		back.setAlpha(1f);
	}
	@Override
	public void onRefresh() {
		flag = false;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				flag = true;
				smoothListView.stopRefresh();
				smoothListView.setRefreshTime("刚刚");
				smoothListView.setRefreshUsable(true);
			}
		}, 2000);
		smoothListView.setRefreshUsable(false);
		refreshMealData();
		getAdData();
	}

	@Override
	public void onLoadMore() {
		flag = false;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				flag = true;
				smoothListView.stopLoadMore();
			}
		}, 2000);
		getMealData();
	}
	
public void getAdData() {
		
		getAdDataRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				final String result = HomeService.HomeServiceByPost(0, null, "NoOneService/GetAdServlet?");

				runOnUiThread(new Runnable() {
					public void run() {
						if (result != null && !result.equals("[]")) {
							try {
								List<ListAd> temp = new ArrayList<>();
								temp.addAll(bannerList);
								bannerList.clear();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String id = j.getString("id");
									String imgurl = j.getString("imgurl");
									bannerList.add(new ListAd(id, imgurl));
								}

								headerBannerView.notifyChange(bannerList);
								SQLiteDatabase db = helper.getWritableDatabase();
								for (int i = 0; i < bannerList.size(); i++) {
									if (temp.get(i).getId() == null) {
										ContentValues values = new ContentValues();
										values.put("imgid", bannerList.get(i).getId());
										values.put("imgurl", bannerList.get(i).getImgurl());
										db.insert("image", null, values);
									} else if (!temp.get(i).getImgurl().equals(bannerList.get(i).getImgurl())) {
										ContentValues values = new ContentValues();
										values.put("imgurl", bannerList.get(i).getImgurl());
										db.update("image", values, "imgid=?", new String[] { temp.get(i).getId() });
									}
								}
								db.close();

							} catch (Exception e) {
								headerBannerView.getAdapt().setFlag(true);
								e.printStackTrace();
							}
						} else {
							headerBannerView.getAdapt().setFlag(true);
						}
					}
				});
			}
		};
		cachedThreadPool.execute(getAdDataRunnable);
	}
	
	public void refreshMealData() {
		refreshMealDataRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				final String result = HomeService.HomeServiceByPost(0, "all", "NoOneService/GetRestaurantServlet?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								mealList.clear();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String id = j.getString("id");
									String name = j.getString("name");
									String address = j.getString("address");
									String send = j.getString("send");
									String delivery = j.getString("delivery");
									String sale = j.getString("sale");
									String imgurl = j.getString("imgurl");

									mealList.add(new ListMeal(id, name, address, send, delivery, sale, imgurl));
								}
								mealadapter.setData(mealList);
								mealadapter.notifyDataSetChanged();

								smoothListView.setLoadMoreEnable(true);

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (result != null)
							smoothListView.setLoadMoreEnable(false);

					}
				});
			}
		};
		cachedThreadPool.execute(refreshMealDataRunnable);
	}

	public void getMealData() {
		
		getMealDataRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mealList.get(0).getIsNoData()) {
					mealList = new ArrayList<ListMeal>();
				} else {
					List<ListMeal> temp = new ArrayList<>();
					temp.addAll(mealList);
					for (int i = temp.size(); temp.get(i - 1).getIsNoData() && i > 0; --i) {
						mealList.remove(i-1);
					}
				}
				final String result = HomeService.HomeServiceByPost(mealList.size(), "all",
						"NoOneService/GetRestaurantServlet?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {

								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String id = j.getString("id");
									String name = j.getString("name");
									String address = j.getString("address");
									String send = j.getString("send");
									String delivery = j.getString("delivery");
									String sale = j.getString("sale");
									String imgurl = j.getString("imgurl");

									mealList.add(new ListMeal(id, name, address, send, delivery, sale, imgurl));
									mealList.get(0).setIsNoData(false);
								}
								mealadapter.setData(mealList);

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if(result != null){
							smoothListView.setLoadMoreEnable(false);
						}
						smoothListView.stopLoadMore();
					}
				});
			}
		};
		cachedThreadPool.execute(getMealDataRunnable);
	}
}
