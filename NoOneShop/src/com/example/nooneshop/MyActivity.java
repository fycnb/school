package com.example.nooneshop;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneshop.service.MyService;
import com.example.nooneshop.utils.DownImage;
import com.example.nooneshop.utils.DownImage.ImageCallBack;
import com.example.nooneshop.utils.MyToast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

public class MyActivity extends Activity {

	private ImageView set;
	private ImageView img;
	private TextView name;
	private Switch state;
	private TextView refresh;
	private LinearLayout allOrder;
	private LinearLayout goodsManage;
	private LinearLayout activityset;
	private LinearLayout shopDetail;
	private PopupWindow window;
//	private TextView setting;
	private TextView about;
	private TextView logout;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);

		id = getIntent().getStringExtra("id");
		set = (ImageView) findViewById(R.id.my_set_iv);
		img = (ImageView) findViewById(R.id.my_img_iv);
//		refresh = (ImageView) findViewById(R.id.my_refresh_iv);
		name = (TextView) findViewById(R.id.my_name_tv);
		state = (Switch) findViewById(R.id.my_state_switch);
		allOrder = (LinearLayout) findViewById(R.id.my_allorder_ll);
		goodsManage = (LinearLayout) findViewById(R.id.my_goodsmanage_ll);
		activityset = (LinearLayout) findViewById(R.id.my_activitysetting_ll);
		shopDetail = (LinearLayout) findViewById(R.id.my_shopdetail_ll);

		View contentView = LayoutInflater.from(MyActivity.this).inflate(R.layout.popup_window, null, false);
//		setting = (TextView) contentView.findViewById(R.id.pop_set_tv);
		about = (TextView) contentView.findViewById(R.id.pop_about_tv);
		logout = (TextView) contentView.findViewById(R.id.pop_logout_tv);
		refresh = (TextView) contentView.findViewById(R.id.pop_refresh_tv);
		window = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		window.setOutsideTouchable(true);
		window.setTouchable(true);
//		setting.setOnClickListener(new onClick());
		about.setOnClickListener(new onClick());
		logout.setOnClickListener(new onClick());

		set.setOnClickListener(new onClick());
		refresh.setOnClickListener(new onClick());
		allOrder.setOnClickListener(new onClick());
		goodsManage.setOnClickListener(new onClick());
		activityset.setOnClickListener(new onClick());
		shopDetail.setOnClickListener(new onClick());

		getShopInfo();

		state.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					openShop();
				} else {
					closeShop();
				}
			}
		});
	}

	public class onClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.my_set_iv:
				window.showAsDropDown(set);
				break;
			case R.id.pop_refresh_tv:
				getShopInfo();
				window.dismiss();
				break;
			case R.id.my_allorder_ll:
				startActivity(new Intent(MyActivity.this, AllOrderActivity.class).putExtra("id", id));
				break;
			case R.id.my_goodsmanage_ll:
				startActivity(new Intent(MyActivity.this, GoodsManageActivity.class).putExtra("id", id));
				break;
			case R.id.my_activitysetting_ll:
				startActivity(new Intent(MyActivity.this, ActivitySettingActivity.class).putExtra("id", id));
				break;
			case R.id.my_shopdetail_ll:
				startActivityForResult(new Intent(MyActivity.this, ShopDetailActivity.class).putExtra("id", id),1);
				break;
//			case R.id.pop_set_tv:
//				startActivity(new Intent(MyActivity.this, SettingActivity.class));
//				window.dismiss();
//				break;
			case R.id.pop_about_tv:
				startActivity(new Intent(MyActivity.this, AboutActivity.class));
				window.dismiss();
				break;
			case R.id.pop_logout_tv:
				MyApplication.editor.putString("id", "null");
				MyApplication.editor.commit();
				window.dismiss();
				startActivity(new Intent(MyActivity.this, LoginActivity.class));
				finish();
				break;
			}
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 1:
			getShopInfo();
			break;
		}
	}
	public void getShopInfo() {
		new Thread(new Runnable() {
			public void run() {
				final String result = MyService.ActivityServiceByPost(id, "NoOneShop/noone/shop/info?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result == null) {
							MyToast.Toast(MyActivity.this, "请检查网络!");
						} else if (result.equals("200")) {
							MyToast.Toast(MyActivity.this, "请重新登录!");
						} else {
							try {
								JSONArray ja = new JSONArray(result);

								JSONObject j = (JSONObject) ja.get(0);

								String ne = j.getString("name");
								int se = j.getInt("state");
								String ig = j.getString("img");
								name.setText(ne);
								if (se == 1)
									state.setChecked(true);
								else
									state.setChecked(false);
								DownImage downImage = new DownImage(ig, img.getWidth(), img.getHeight());
								downImage.loadImage(new ImageCallBack() {

									@Override
									public void getDrawable(Drawable drawable) {
										img.setImageDrawable(drawable);
									}
								});

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
		}).start();
	}

	public void openShop() {
		new Thread(new Runnable() {
			public void run() {
				final String result = MyService.ActivityServiceByPost(id, "NoOneShop/noone/shop/open?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result == null) {
							MyToast.Toast(MyActivity.this, "请检查网络!");
							state.setChecked(false);
						} else if (result.equals("200")) {
							MyToast.Toast(MyActivity.this, "请重新操作!");
							state.setChecked(false);
						} else if (result.equals("100")) {
							MyToast.Toast(MyActivity.this, "店铺开启!");
						}
					}
				});
			}
		}).start();
	}

	public void closeShop() {
		new Thread(new Runnable() {
			public void run() {
				final String result = MyService.ActivityServiceByPost(id, "NoOneShop/noone/shop/close?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result == null) {
							MyToast.Toast(MyActivity.this, "请检查网络!");
							state.setChecked(true);
						} else if (result.equals("200")) {
							MyToast.Toast(MyActivity.this, "请重新操作!");
							state.setChecked(true);
						} else if (result.equals("100")) {
							MyToast.Toast(MyActivity.this, "店铺关闭!");
						}
					}
				});
			}
		}).start();
	}
}
