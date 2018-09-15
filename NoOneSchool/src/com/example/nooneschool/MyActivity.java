package com.example.nooneschool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.nooneschool.my.CollectionActivity;
import com.example.nooneschool.my.CustomerServiceActivity;
import com.example.nooneschool.my.MemberCenterActivity;
import com.example.nooneschool.my.MemberRechargeActivity;
import com.example.nooneschool.my.RecentlyBrowseActivity;
import com.example.nooneschool.my.SettingActivity;
import com.example.nooneschool.my.SignInActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class MyActivity extends Activity implements View.OnClickListener {
	private TextView tv_username;
	private TextView tv_account;
	private TextView tv_vip;
	private ImageView iv_headportrait;
	private Button btn_signin;

	private GridView gv_function;
	private List<Map<String, Object>> functionList;
	private SimpleAdapter adapter;

	private String vip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		init();

	}

	private void init() {
		tv_username = (TextView) findViewById(R.id.my_username_textview);
		tv_account = (TextView) findViewById(R.id.my_account_textview);
		tv_vip = (TextView) findViewById(R.id.my_vip_textview);
		iv_headportrait = (ImageView) findViewById(R.id.my_headportrait_imageview);
		btn_signin = (Button) findViewById(R.id.my_signin_button);
		gv_function = (GridView) findViewById(R.id.my_function_gridview);

		// 向function_gridview中插入数据
		functiondata();

		// 获取数据
		String username = "cjq";
		String account = "15822899062";
		vip = "有";
		String signin = "未签到";
		// Bitmap headportrait =
		// BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);

		// 显示数据
		tv_username.setText(username);
		tv_account.setText(account);

		if (vip.equals("无")) {
			tv_vip.setTextColor(Color.parseColor("#AAAAAA"));
		} else if (vip.equals("有")) {
			tv_vip.setTextColor(Color.parseColor("#FF0000"));
		} else {
			Log.i("cjq", "vip color error");
		}
		
		if (signin.equals("未签到")) {
			btn_signin.setTextColor(Color.parseColor("#AAAAAA"));
		} else if (signin.equals("已签到")) {
			btn_signin.setTextColor(Color.parseColor("#FF0000"));
		} else {
			Log.i("cjq", "signin color error");
		}

		iv_headportrait.setBackgroundResource(R.drawable.ic_launcher);

		// 点击事件
		tv_vip.setOnClickListener(this);
		btn_signin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_vip_textview:
			if (vip.equals("无")) {
				Intent intent = new Intent(MyActivity.this, MemberRechargeActivity.class);
				startActivity(intent);
			} else if (vip.equals("有")) {
				Intent intent = new Intent(MyActivity.this, MemberCenterActivity.class);
				startActivity(intent);
			} else {
				Log.i("cjq", "vip click error");
			}
			break;
		case R.id.my_signin_button:
			Intent intent = new Intent(MyActivity.this, SignInActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private void functiondata() {
		int icno[] = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher };
		String name[] = { "设置", "收藏", "最近浏览", "客服", "会员中心" };

		functionList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < icno.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("icon", icno[i]);
			map.put("name", name[i]);
			functionList.add(map);
		}

		String[] str = { "icon", "name" };
		int[] i = { R.id.function_icon_imageview, R.id.function_name_textview };

		adapter = new SimpleAdapter(this, functionList, R.layout.item_my_function_girdview, str, i);
		gv_function.setAdapter(adapter);
		gv_function.setOnItemClickListener(new functiongirdview());
	}

	public class functiongirdview implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String name = functionList.get(position).get("name").toString();
			if (name.equals("设置")) {
				Intent intent = new Intent(MyActivity.this, SettingActivity.class);
				startActivity(intent);
			} else if (name.equals("收藏")) {
				Intent intent = new Intent(MyActivity.this, CollectionActivity.class);
				startActivity(intent);
			} else if (name.equals("最近浏览")) {
				Intent intent = new Intent(MyActivity.this, RecentlyBrowseActivity.class);
				startActivity(intent);
			} else if (name.equals("客服")) {
				Intent intent = new Intent(MyActivity.this, CustomerServiceActivity.class);
				startActivity(intent);
			} else if (name.equals("会员中心")) {
				Intent intent = new Intent(MyActivity.this, MemberCenterActivity.class);
				startActivity(intent);
			} else {
				Log.i("cjq", "function gridview error");
			}

		}

	}

}
