package com.example.nooneschool.home;

import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.util.DensityUtil;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

public class ShopActivity extends TabActivity {

	private TextView name;
	private TextView address;
	private ImageView img;
	private ImageView back;
	private TextView refresh;
	private LinearLayout show;

	private TabHost tabhost;
	private String id;
	private String shopid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);

		name = (TextView) findViewById(R.id.restaurant_name_textview);
		address = (TextView) findViewById(R.id.restaurant_address_textview);
		img = (ImageView) findViewById(R.id.restaurant_img_imageview);
		back = (ImageView) findViewById(R.id.restaurant_back_imageview);
		refresh = (TextView) findViewById(R.id.restaurant_refresh_textview);
		show = (LinearLayout) findViewById(R.id.restaurant_show_ll);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getShopInfo();
			}
		});
		shopid = getIntent().getStringExtra("shopid");
		id = getIntent().getStringExtra("id");

		tabhost = getTabHost();

		Intent one = new Intent(ShopActivity.this, MenuActivity.class);
		one.putExtra("shopid", shopid).putExtra("id", id);
		tabhost.addTab(tabhost.newTabSpec("one").setIndicator(createTabIcon(R.color.home, "点菜"))
				.setContent(one));

		Intent two = new Intent(ShopActivity.this, CommentActivity.class);
		two.putExtra("shopid", shopid);
		tabhost.addTab(tabhost.newTabSpec("two").setIndicator(createTabIcon(R.color.dating, "评价"))
				.setContent(two.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

//		Intent three = new Intent(ShopActivity.this, MerchantActivity.class);
//		three.putExtra("shopid", shopid);
//		tabhost.addTab(tabhost.newTabSpec("three").setIndicator(createTabIcon(R.color.lesson, "商家"))
//				.setContent(three.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		upTabhost();
		tabhost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				upTabhost();
			}
		});
		getShopInfo();
		
	}

	private void upTabhost() {
		TabWidget tabw = tabhost.getTabWidget();
		for (int i = 0; i < tabw.getChildCount(); i++) {
			LinearLayout ll = (LinearLayout) tabw.getChildAt(i);
			TextView tv = (TextView) ll.findViewById(R.id.tv);

			if (tabhost.getCurrentTab() == i) {
				tv.setTextColor(0xff000000);
				tv.setTextSize(DensityUtil.px2sp(ShopActivity.this, 25));
			} else {
				tv.setTextColor(0xffcdcdcd);
				tv.setTextSize(DensityUtil.px2sp(ShopActivity.this, 20));
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

	public void getShopInfo() {
		new Thread(new Runnable() {
			public void run() {
				final String result = HomeService.ShopInfoServiceByPost(shopid);

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								refresh.setVisibility(8);
								show.setVisibility(0);
								JSONObject j = new JSONObject(result);

								String adS = j.getString("address");
								String nameS = j.getString("name");
								String imgS = j.getString("imgurl");

								address.setText(adS);
								name.setText(nameS);
								DownImage downImage = new DownImage(imgS, img.getWidth(), img.getHeight());
								downImage.loadImage(new ImageCallBack() {

									@Override
									public void getDrawable(Drawable drawable) {
										img.setImageDrawable(drawable);
									}
								});
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							refresh.setVisibility(0);
							show.setVisibility(8);
						}
					}
				});
			}
		}).start();
	}
}