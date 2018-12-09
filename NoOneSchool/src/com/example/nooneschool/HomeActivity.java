package com.example.nooneschool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.home.DeliveryActivity;
import com.example.nooneschool.home.FoodsActivity;
import com.example.nooneschool.home.HomeService;
import com.example.nooneschool.home.SearchActivity;
import com.example.nooneschool.home.ShopActivity;
import com.example.nooneschool.home.adapter.AdAdapter;
import com.example.nooneschool.home.adapter.ShopAdapter;
import com.example.nooneschool.home.list.AdList;
import com.example.nooneschool.home.list.ShopList;
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

public class HomeActivity extends Activity {

	private SwipeRefreshLayout swipeRefreshLayout;
	private LinearLayout search;
	private ImageView weather;
	private ImageView delivery;
	private LinearLayout rice;
	private LinearLayout noodle;
	private LinearLayout snack;
	private LinearLayout more;
	private ListView shop;
	private List<ShopList> shoplist;
	private ShopAdapter shopadapter;

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
	private Runnable getShopDataRunnable;
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
		setContentView(R.layout.activity_home);

		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_refresh_layout);
		search = (LinearLayout) findViewById(R.id.home_search_ll);
		weather = (ImageView) findViewById(R.id.home_weather_iv);
		delivery = (ImageView) findViewById(R.id.home_deliveryman_iv);
		rice = (LinearLayout) findViewById(R.id.home_rice_ll);
		noodle = (LinearLayout) findViewById(R.id.home_noodle_ll);
		snack = (LinearLayout) findViewById(R.id.home_snack_ll);
		more = (LinearLayout) findViewById(R.id.home_more_ll);
		shop = (ListView) findViewById(R.id.home_shop_lv);

		vpBanner = (ViewPager) findViewById(R.id.home_ad_vp);
		llIndexContainer = (LinearLayout) findViewById(R.id.home_ad_ll);
		rlBanner = (RelativeLayout) findViewById(R.id.home_ad_rl);

		swipeRefreshLayout.setOnRefreshListener(new onRefresh());
		search.setOnClickListener(new onHomeClick());
		weather.setOnClickListener(new onHomeClick());
		delivery.setOnClickListener(new onHomeClick());
		rice.setOnClickListener(new onHomeClick());
		noodle.setOnClickListener(new onHomeClick());
		snack.setOnClickListener(new onHomeClick());
		more.setOnClickListener(new onHomeClick());

		bannerHeight = DensityUtil.getWindowWidth(this) * 6 / 16;
		LayoutParams layoutParams = (LayoutParams) rlBanner.getLayoutParams();
		layoutParams.height = bannerHeight;
		rlBanner.setLayoutParams(layoutParams);

		shopadapter = new ShopAdapter(HomeActivity.this);
		shop.setAdapter(shopadapter);
		
		list = new ArrayList<>();
		list.add(new AdList("1", "1","1"));
		list.add(new AdList("2", "2","1"));
		list.add(new AdList("3", "3","1"));
		
		dealWithTheView(list);
		getAdData();
		getMealData();
		
		shop.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long lid) {

					String mealid = ((ShopList) parent.getAdapter().getItem(position)).getId();
					Intent intent = new Intent(HomeActivity.this, ShopActivity.class);
					intent.putExtra("shopid", mealid).putExtra("id", id);
					startActivity(intent);

			}
		});
	}

	public void getAdData() {

		getAdDataRunnable = new Runnable() {
			@Override
			public void run() {
				final String result = HomeService.AdServiceByPost(0);

				runOnUiThread(new Runnable() {
					public void run() {
						if (result != null && !result.equals("[]")) {
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

	public void getMealData() {

		getShopDataRunnable = new Runnable() {
			@Override
			public void run() {
				final String result = HomeService.ShopServiceByPost();
				runOnUiThread(new Runnable() {
					public void run() {
						if (result != null && !result.equals("[]")) {
							try {

								shoplist = new ArrayList<>();
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

									shoplist.add(new ShopList(id, name, address, send, delivery, sale, imgurl));
								}
								shopadapter.setData(shoplist);

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (result != null) {
							shoplist = new ArrayList<>();
							shopadapter.setData(shoplist);
						} else {
							shoplist = new ArrayList<>();
							shopadapter.setData(shoplist);
						}
						Utility.setListViewHeight(shop);
					}
				});
			}
		};

		cachedThreadPool.execute(getShopDataRunnable);
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
			getMealData();
		}

	}

	public class onHomeClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_search_ll:
				startActivity(new Intent(HomeActivity.this, SearchActivity.class).putExtra("id", id));
				break;
			case R.id.home_weather_iv:

				break;
			case R.id.home_deliveryman_iv:
				startActivity(new Intent(HomeActivity.this, DeliveryActivity.class).putExtra("id", id));
				break;
			case R.id.home_rice_ll:
				startActivity(new Intent(HomeActivity.this, FoodsActivity.class).putExtra("type", 1).putExtra("id", id));
				break;
			case R.id.home_noodle_ll:
				startActivity(new Intent(HomeActivity.this, FoodsActivity.class).putExtra("type", 2).putExtra("id", id));
				break;
			case R.id.home_snack_ll:
				startActivity(new Intent(HomeActivity.this, FoodsActivity.class).putExtra("type", 3).putExtra("id", id));
				break;
			case R.id.home_more_ll:
				startActivity(new Intent(HomeActivity.this, FoodsActivity.class).putExtra("type", 4).putExtra("id", id));
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

		adapter = new AdAdapter(ivList, list, HomeActivity.this);
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
