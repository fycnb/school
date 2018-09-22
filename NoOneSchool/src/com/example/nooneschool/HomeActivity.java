package com.example.nooneschool;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.SmoothListView.SmoothListView;
import com.example.nooneschool.home.BannerView;
import com.example.nooneschool.home.HomeService;
import com.example.nooneschool.home.ListAd;
import com.example.nooneschool.home.ListFood;
import com.example.nooneschool.home.AdapterMeal;
import com.example.nooneschool.home.ListMeal;
import com.example.nooneschool.home.MealActivity;
import com.example.nooneschool.home.OtherFoodActivity;
import com.example.nooneschool.home.WeatherActivity;
import com.example.nooneschool.home.AdapterFood;
import com.example.nooneschool.util.ColorUtil;
import com.example.nooneschool.util.DataUtil;
import com.example.nooneschool.util.DensityUtil;
import com.example.nooneschool.util.ImageUtils;
import com.example.nooneschool.util.SQLite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends Activity implements SmoothListView.ISmoothListViewListener {

	private static SQLite helper;
	
	private RelativeLayout rl_title;
	private SmoothListView smoothListView;
	private BannerView headerBannerView;
	private GridView gvFood;
	private ImageView weather;
	
	private AdapterMeal mealadapter;

	private List<ListAd> bannerList;
	private List<ListFood> foodList;
	private List<ListMeal> mealList;

	private int titleViewHeight = 65;
	private boolean isScrollIdle = true;
	private int bannerViewTopMargin;
	private View itemHeaderBannerView;
	private int bannerViewHeight = 180;
	private String type = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		smoothListView = (SmoothListView) findViewById(R.id.home_function_sv);
		rl_title = (RelativeLayout) findViewById(R.id.home_title_rl);
		weather = (ImageView) findViewById(R.id.home_weather_imageview);
		
		helper = new SQLite(this);
		
		// new Thread() {
		// public void run() {
		// BitmapDrawable img1 = (BitmapDrawable)
		// getResources().getDrawable(R.drawable.img_01);
		//
		// ImageUtils.saveImage(img1.getBitmap(),
		// Environment.getExternalStorageDirectory() + ImageUtils.PATH,
		// "img_01.png");
		//
		// BitmapDrawable img2 = (BitmapDrawable)
		// getResources().getDrawable(R.drawable.img_02);
		//
		// ImageUtils.saveImage(img2.getBitmap(),
		// Environment.getExternalStorageDirectory() + ImageUtils.PATH,
		// "img_02.png");
		//
		// BitmapDrawable img3 = (BitmapDrawable)
		// getResources().getDrawable(R.drawable.img_03);
		//
		// ImageUtils.saveImage(img3.getBitmap(),
		// Environment.getExternalStorageDirectory() + ImageUtils.PATH,
		// "img_03.png");
		//
		// BitmapDrawable img4 = (BitmapDrawable)
		// getResources().getDrawable(R.drawable.img_04);
		//
		// ImageUtils.saveImage(img4.getBitmap(),
		// Environment.getExternalStorageDirectory() + ImageUtils.PATH,
		// "img_04.png");
		// }
		// }.start();

		weather.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this,WeatherActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		
		//广告初始化
		bannerList = DataUtil.getBannerData(this);
		headerBannerView = new BannerView(HomeActivity.this);
		headerBannerView.fillView(bannerList, smoothListView);

		
		
		//食品标签初始化
		foodList = DataUtil.getFoodData();
		View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.header_food_layout, smoothListView, false);
        gvFood =(GridView) view.findViewById(R.id.food_type_gridview);

        int itemWidth = DensityUtil.dip2px(this, 40);
        int size = foodList.size();
        int itemPaddingH = DensityUtil.dip2px(this, 8);
        int gridviewWidth = size * (itemWidth + itemPaddingH*2);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gvFood.setLayoutParams(params);
        gvFood.setColumnWidth(itemWidth);
        gvFood.setHorizontalSpacing(itemPaddingH);
        gvFood.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
        gvFood.setNumColumns(size);
        AdapterFood adapter = new AdapterFood(this,foodList);
        gvFood.setAdapter(adapter);
        
        gvFood.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 7:
					Intent intent = new Intent(HomeActivity.this,OtherFoodActivity.class);
					startActivity(intent);
					break;

				default:
					type = position+"";
					refreshMealData();
					Toast.makeText(HomeActivity.this, type, 0).show();
					break;
				}
			}
		});
        
        smoothListView.addHeaderView(view);

        //餐厅初始化
		mealList = DataUtil.getNoDataMeal(DensityUtil.getWindowHeight(this) - DensityUtil.dip2px(this, 400));
		mealadapter = new AdapterMeal(this, mealList);
		smoothListView.setAdapter(mealadapter);

		smoothListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if(!((ListMeal)parent.getAdapter().getItem(position)).getIsNoData()){

					String mealid = ((ListMeal)parent.getAdapter().getItem(position)).getId();
					Intent intent = new Intent(HomeActivity.this, MealActivity.class);
					intent.putExtra("di", mealid);
					startActivity(intent);	
				}
				
			}
		});
		smoothListView.setRefreshEnable(true);
		smoothListView.setLoadMoreEnable(true);
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
					bannerViewTopMargin = DensityUtil.px2dip(HomeActivity.this, itemHeaderBannerView.getTop());
					bannerViewHeight = DensityUtil.px2dip(HomeActivity.this, itemHeaderBannerView.getHeight());
				}
				// 处理标题栏颜色渐变
				handleTitleBarColorEvaluate();
			}
		});

	}

	private void handleTitleBarColorEvaluate() {
		float fraction;
		if (bannerViewTopMargin > 0) {
			fraction = 1f - bannerViewTopMargin * 1f / 60;
			if (fraction < 0f)
				fraction = 0f;
			rl_title.setAlpha(fraction);
			return;
		}

		float space = Math.abs(bannerViewTopMargin) * 1f;
		fraction = space / (bannerViewHeight - titleViewHeight);
		if (fraction < 0f)
			fraction = 0f;
		if (fraction > 1f)
			fraction = 1f;
		rl_title.setAlpha(1f);

		if (fraction >= 1f) {
			rl_title.setBackgroundColor(this.getResources().getColor(R.color.blue));
		} else {
			rl_title.setBackgroundColor(
					ColorUtil.getNewColorByStartEndColor(this, fraction, R.color.title, R.color.blue));
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				smoothListView.stopRefresh();
				smoothListView.setRefreshTime("刚刚");
			}
		}, 2000);
		refreshMealData();
		getAdData("all");

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				smoothListView.stopLoadMore();
			}
		}, 2000);

		getMealData();
	}

	
	public void getAdData(final String type) {
		new Thread() {
			public void run() {
				String result = HomeService.HomeServiceByPost(type, "");
				if (result != null) {
					try {
						List<ListAd> temp = bannerList;
						bannerList.clear();
						JSONArray ja = new JSONArray(result);
						for (int i = 0; i < ja.length(); i++) {
							JSONObject j = (JSONObject) ja.get(i);

							String id = j.getString("id");
							String imgname = j.getString("imgname");
							String imgurl = j.getString("imgurl");
							byte[] imgbit = j.getString("imgbit").getBytes();
							bannerList.add(new ListAd(id, imgname, imgbit, imgurl));
						}
						SQLiteDatabase db = helper.getWritableDatabase();
						for(int i = 0;i<bannerList.size();i--){
							if(temp.get(i).getId()==null){
								ContentValues values = new ContentValues();
								values.put("imgid", bannerList.get(i).getId());
								values.put("imgblob", bannerList.get(i).getImgbit());
								values.put("imgurl", bannerList.get(i).getImgurl());
								values.put("imgname", bannerList.get(i).getName());
								db.insert("image", null, values);
							}
							
							if(!temp.get(i).getName().equals(bannerList.get(i).getName())){
								ContentValues values = new ContentValues();
								values.put("imgblob", bannerList.get(i).getImgbit());
								values.put("imgurl", bannerList.get(i).getImgurl());
								values.put("imgname", bannerList.get(i).getName());
								db.update("image", values, "imgid=?", new String[]{bannerList.get(i).getId()});
							}
							if(!temp.get(i).getImgbit().equals(bannerList.get(i).getImgbit())){
								ContentValues values = new ContentValues();
								values.put("imgblob", bannerList.get(i).getImgbit());
								db.update("image", values, "imgid=?", new String[]{bannerList.get(i).getId()});
							}
						}
						db.close();
						headerBannerView.notifyChange(bannerList);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}.start();
	}
	
	
	
	public void refreshMealData() {
		new Thread() {
			public void run() {
				String result = HomeService.HomeServiceByPost(type, "");
				if (result != null) {
					try {

						mealList.clear();
						JSONArray ja = new JSONArray(result);
						for (int i = 0; i < ja.length(); i++) {
							JSONObject j = (JSONObject) ja.get(i);

							String id = j.getString("id");
							String name = j.getString("name");
							String money = j.getString("money");
							String address = j.getString("address");
							String sale = j.getString("sale");
							String imgname = j.getString("imgname");
							String imgurl = j.getString("imgurl");

							mealList.add(new ListMeal(id, name, money, address, sale, imgname, imgurl));
						}
						mealadapter.setData(mealList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}.start();
	}

	public void getMealData() {
		new Thread() {
			public void run() {
				String result = HomeService.HomeServiceByPost(type, "");
				if (result != null) {
					try {

						JSONArray ja = new JSONArray(result);
						for (int i = 0; i < ja.length(); i++) {
							JSONObject j = (JSONObject) ja.get(i);

							String id = j.getString("id");
							String name = j.getString("name");
							String money = j.getString("money");
							String address = j.getString("address");
							String sale = j.getString("sale");
							String imgname = j.getString("imgname");
							String imgurl = j.getString("imgurl");

							mealList.add(new ListMeal(id, name, money, address, sale, imgname, imgurl));
						}
						mealadapter.notifyDataSetChanged();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}.start();
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		headerBannerView.enqueueBannerLoopMessage();
	}

	@Override
	protected void onStop() {
		super.onStop();
		headerBannerView.removeBannerLoopMessage();
	}

}
