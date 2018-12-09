package com.example.nooneschool.home;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.home.adapter.AdAdapter;
import com.example.nooneschool.home.adapter.FoodsAdapter;
import com.example.nooneschool.home.list.AdList;
import com.example.nooneschool.home.list.FoodsList;
import com.example.nooneschool.util.DensityUtil;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;
import com.example.nooneschool.util.FixedSpeedScroller;
import com.example.nooneschool.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FoodsActivity extends Activity {

	private SwipeRefreshLayout swipeRefreshLayout;
	private ImageView back;
	private ImageView search;
	private TextView refresh;
	private ListView foods;
	private List<FoodsList> foodslist;
	private FoodsAdapter foodsadapter;

	private ViewPager vpBanner;
	private LinearLayout llIndexContainer;
	private RelativeLayout rlBanner;

	private static final int BANNER_TYPE = 0;
	private static final int BANNER_TIME = 5000;
	private List<ImageView> ivList;
	private List<AdList> list;
	private int bannerHeight;
	private int bannerCount;
	private AdAdapter adapter;
	private ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(128));
	private Runnable getAdDataRunnable;
	private Runnable getFoodsDataRunnable;
	private int type;
	private String id;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == BANNER_TYPE) {
				vpBanner.setCurrentItem(vpBanner.getCurrentItem() + 1);
				enqueueBannerLoopMessage();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foods);

		type = getIntent().getIntExtra("type", 3);
		id = getIntent().getStringExtra("id");
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.foods_refresh_layout);
		back = (ImageView) findViewById(R.id.foods_back_iv);
		search = (ImageView) findViewById(R.id.foods_search_iv);
		refresh = (TextView) findViewById(R.id.foods_refresh_tv);
		foods = (ListView) findViewById(R.id.foods_shop_lv);
		
		swipeRefreshLayout.setOnRefreshListener(new onRefresh());
		back.setOnClickListener(new onFoodsClick());
		search.setOnClickListener(new onFoodsClick());
		refresh.setOnClickListener(new onFoodsClick());

		vpBanner = (ViewPager) findViewById(R.id.foods_ad_vp);
		llIndexContainer = (LinearLayout) findViewById(R.id.foods_ad_ll);
		rlBanner = (RelativeLayout) findViewById(R.id.foods_ad_rl);

		bannerHeight = DensityUtil.getWindowWidth(this) * 6 / 16;
		LayoutParams layoutParams = (LayoutParams) rlBanner.getLayoutParams();
		layoutParams.height = bannerHeight;
		rlBanner.setLayoutParams(layoutParams);

		foodsadapter = new FoodsAdapter(FoodsActivity.this);
		foods.setAdapter(foodsadapter);

		list = new ArrayList<>();
		list.add(new AdList("1", "1","1"));
		list.add(new AdList("2", "2","1"));
		list.add(new AdList("3", "3","1"));

		dealWithTheView(list);
		getAdData();
		getFoodsData();
		
		foods.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long lid) {

					String id1 = ((FoodsList) parent.getAdapter().getItem(position)).getAddress();
					Intent intent = new Intent(FoodsActivity.this, ShopActivity.class);
					intent.putExtra("shopid", id1).putExtra("id", id);
					startActivity(intent);

			}
		});
	}

	public void getAdData() {

		getAdDataRunnable = new Runnable() {
			@Override
			public void run() {
				final String result = HomeService.AdServiceByPost(type);

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null) {
							try {
								list = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String id = j.getString("id");
									String imgurl = j.getString("imgurl");
									String url = j.getString("url");

									list.add(new AdList(id, imgurl,url));
								}
								dealWithTheView(list);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
		};
		cachedThreadPool.execute(getAdDataRunnable);
	}

	public void getFoodsData() {

		getFoodsDataRunnable = new Runnable() {
			@Override
			public void run() {
				final String result = HomeService.FoodsServiceByPost(type);

				runOnUiThread(new Runnable() {
					public void run() {
						if (result == null) {
							swipeRefreshLayout.setVisibility(8);
							search.setVisibility(8);
							refresh.setVisibility(0);
						} else {
							swipeRefreshLayout.setVisibility(0);
							search.setVisibility(0);
							refresh.setVisibility(8);
							try {
								foodslist = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String id = j.getString("id");
									String name = j.getString("name");
									String address = j.getString("shopid");
									String money = j.getString("money");
									String sale = j.getString("sale");
									String imgurl = j.getString("img");
									foodslist.add(new FoodsList(id, name, address, imgurl, money, sale));
								}

								foodsadapter.setData(foodslist);
								Utility.setListViewHeight(foods);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
		};
		cachedThreadPool.execute(getFoodsDataRunnable);
	}

	public class onRefresh implements OnRefreshListener {

		@Override
		public void onRefresh() {
			new Handler().postDelayed(new Runnable() {// 模拟耗时操作
				@Override
				public void run() {
					swipeRefreshLayout.setRefreshing(false);// 取消刷新
					swipeRefreshLayout.setEnabled(true);
				}
			}, 4000);
			swipeRefreshLayout.setEnabled(false);
			getFoodsData();
		}

	}

	public class onFoodsClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.foods_back_iv:
				finish();
				break;
			case R.id.foods_search_iv:
				startActivity(new Intent(FoodsActivity.this, SearchActivity.class).putExtra("id", id));
				break;
			case R.id.foods_refresh_tv:
				getFoodsData();
				break;
			}
		}

	}

	private void dealWithTheView(List<AdList> list) {
		ivList = new ArrayList<ImageView>();
		bannerCount = list.size();
		if (bannerCount == 2) {
			list.addAll(list);
		}

		createImageViews(list);

		adapter = new AdAdapter(ivList, list, FoodsActivity.this);
		vpBanner.setAdapter(adapter);

		addIndicatorImageViews();
		setViewPagerChangeListener();
		controlViewPagerSpeed(vpBanner, 500);

	}

	// 创建要显示的ImageView
	private void createImageViews(List<AdList> list) {
		for (int i = 0; i < list.size(); i++) {
			final ImageView imageView = new ImageView(this);
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(params);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			DownImage downImage = new DownImage(list.get(i).getImgurl(), bannerHeight * 16 / 9, bannerHeight);
			downImage.loadImage(new ImageCallBack() {

				@Override
				public void getDrawable(Drawable drawable) {
					imageView.setImageDrawable(drawable);
				}
			});
			ivList.add(imageView);
		}
	}

	// 添加指示图标
	private void addIndicatorImageViews() {
		llIndexContainer.removeAllViews();
		if (bannerCount < 2)
			return;
		for (int i = 0; i < bannerCount; i++) {
			ImageView iv = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(this, 5),
					DensityUtil.dip2px(this, 5));
			lp.leftMargin = DensityUtil.dip2px(this, (i == 0) ? 0 : 7);
			iv.setLayoutParams(lp);
			iv.setBackgroundResource(R.drawable.xml_round_orange_grey_sel);
			iv.setEnabled(i == 0);
			llIndexContainer.addView(iv);
		}
	}

	// 为ViewPager设置监听器
	private void setViewPagerChangeListener() {
		vpBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if (ivList == null || ivList.size() == 0)
					return;
				int newPosition = position % bannerCount;
				for (int i = 0; i < bannerCount; i++) {
					llIndexContainer.getChildAt(i).setEnabled(i == newPosition);
				}
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	// 添加Banner循环消息到队列
	public void enqueueBannerLoopMessage() {
		if (ivList == null || ivList.size() <= 1)
			return;
		mHandler.sendEmptyMessageDelayed(BANNER_TYPE, BANNER_TIME);
	}

	// 移除Banner循环的消息
	public void removeBannerLoopMessage() {
		if (mHandler.hasMessages(BANNER_TYPE)) {
			mHandler.removeMessages(BANNER_TYPE);
		}
	}

	// 反射设置ViewPager的轮播速度
	private void controlViewPagerSpeed(ViewPager viewPager, int speedTimeMillis) {
		try {
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(this, new AccelerateDecelerateInterpolator());
			scroller.setDuration(speedTimeMillis);
			field.set(viewPager, scroller);
		} catch (Exception e) {
		}
	}
}
