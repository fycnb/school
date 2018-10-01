package com.example.nooneschool.home;

import com.example.nooneschool.DatingActivity;
import com.example.nooneschool.HomeActivity;
import com.example.nooneschool.LessonActivity;
import com.example.nooneschool.MainActivity;
import com.example.nooneschool.MyActivity;
import com.example.nooneschool.R;
import com.example.nooneschool.R.layout;
import com.example.nooneschool.util.DensityUtil;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;

public class MealActivity extends TabActivity {

	private TextView nametv;
	private TextView ad;
	private ImageView img;
	private ImageView back;

	private TabHost tabhost;
	private String id;
	private String name;
	private String imgurl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal);

		nametv = (TextView) findViewById(R.id.restaurant_name_textview);
		ad = (TextView) findViewById(R.id.restaurant_ad_textview);
		img = (ImageView) findViewById(R.id.restaurant_img_imageview);
		back = (ImageView) findViewById(R.id.restaurant_back_imageview);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		name = intent.getStringExtra("name");
		imgurl = intent.getStringExtra("imgurl");

		nametv.setText(name);
		DownImage downImage = new DownImage(imgurl, img.getWidth(), img.getHeight());
		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {
				img.setImageDrawable(drawable);
			}
		});

		tabhost = getTabHost();

		Intent one = new Intent(MealActivity.this, MenuActivity.class);
		one.putExtra("id", id);
		tabhost.addTab(tabhost.newTabSpec("one").setIndicator(createTabIcon(R.color.home, "点菜"))
				.setContent(one.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		Intent two = new Intent(MealActivity.this, CommentActivity.class);
		two.putExtra("id", id);
		tabhost.addTab(tabhost.newTabSpec("two").setIndicator(createTabIcon(R.color.dating, "评价"))
				.setContent(two.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		Intent three = new Intent(MealActivity.this, MerchantActivity.class);
		three.putExtra("id", id);
		tabhost.addTab(tabhost.newTabSpec("three").setIndicator(createTabIcon(R.color.lesson, "商家"))
				.setContent(three.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		upTabhost();
		tabhost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				upTabhost();
			}
		});

	}

	private void upTabhost() {
		TabWidget tabw = tabhost.getTabWidget();
		for (int i = 0; i < tabw.getChildCount(); i++) {
			LinearLayout ll = (LinearLayout) tabw.getChildAt(i);
			TextView tv = (TextView) ll.findViewById(R.id.tv);

			if (tabhost.getCurrentTab() == i) {
				tv.setTextColor(0xff000000);
				tv.setTextSize(DensityUtil.px2sp(MealActivity.this, 25));
			} else {
				tv.setTextColor(0xffcdcdcd);
				tv.setTextSize(DensityUtil.px2sp(MealActivity.this, 20));
			}
		}
	}

	public View createTabIcon(int resId, String title) {

		View view = LayoutInflater.from(this).inflate(R.layout.tab1, null);

		ImageView iv = (ImageView) view.findViewById(R.id.iv);
		iv.setImageResource(resId);

		TextView tv = (TextView) view.findViewById(R.id.tv);
		tv.setText(title);
		return view;
	}
}
