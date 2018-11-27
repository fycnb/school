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

public class OtherFoodActivity extends Activity{

	private SmoothListView smoothListView;
	private BannerView foodBanner;
	private List<ListAd> adList;
	private List<ListFood> foodList;
	
	private AdapterFood foodAdapter;
	
	
	private int titleViewHeight = 65;
	private int bannerViewTopMargin;
	private int bannerViewHeight = 180;
	private boolean isScrollIdle = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_food);
		
		smoothListView = (SmoothListView) findViewById(R.id.food_function_sv);
		
		adList = DataUtil.getBannerData(this);
		foodBanner = new BannerView(OtherFoodActivity.this);
		foodBanner.fillView(adList, smoothListView);
		foodBanner.getAdapt().setFlag(false);
		
		
		foodList = DataUtil.getNoDataFood(DensityUtil.getWindowHeight(this) - DensityUtil.dip2px(this, 300));
		foodAdapter = new AdapterFood(this, foodList);
		smoothListView.setAdapter(foodAdapter);

//		smoothListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				// TODO Auto-generated method stub
//				if (!((ListMeal) parent.getAdapter().getItem(position)).getIsNoData()) {
//
//					String mealid = ((ListMeal) parent.getAdapter().getItem(position)).getId();
//					Intent intent = new Intent(OtherFoodActivity.this, MealActivity.class);
//					intent.putExtra("id", mealid);
//					startActivity(intent);
//				}
//
//			}
//		});
		smoothListView.setRefreshEnable(false);
		smoothListView.setLoadMoreEnable(false);
//		smoothListView.setSmoothListViewListener(this);
//
//		smoothListView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {
//			@Override
//			public void onSmoothScrolling(View view) {
//			}
//
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//				isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
//			}
//
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//				if (isScrollIdle && bannerViewTopMargin < 0)
//					return;
//
//				// 获取广告头部View、自身的高度、距离顶部的高度
//				if (itemHeaderBannerView == null) {
//					itemHeaderBannerView = smoothListView.getChildAt(1);
//				}
//				if (itemHeaderBannerView != null) {
//					bannerViewTopMargin = DensityUtil.px2dip(HomeActivity.this, itemHeaderBannerView.getTop());
//					bannerViewHeight = DensityUtil.px2dip(HomeActivity.this, itemHeaderBannerView.getHeight());
//				}
//				// 处理标题栏颜色渐变
//				handleTitleBarColorEvaluate();
//			}
//		});
//		
	}
}
