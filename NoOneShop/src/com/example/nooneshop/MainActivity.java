package com.example.nooneshop;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {

	private TabHost tabhost;
	private String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tabhost = getTabHost();
		id = getIntent().getStringExtra("id");
		Intent one = new Intent(MainActivity.this, HomeActivity.class).putExtra("id", id);
		tabhost.addTab(tabhost.newTabSpec("one").setIndicator(createTabIcon(R.color.home, "首页"))
				.setContent(one));

		Intent two = new Intent(MainActivity.this, MyActivity.class).putExtra("id", id);
		tabhost.addTab(tabhost.newTabSpec("two").setIndicator(createTabIcon(R.color.my, "我的"))
				.setContent(two));
		PushManager.startWork(getApplicationContext(),PushConstants.LOGIN_TYPE_API_KEY,"GUkYGzQj3T8KDBeHVRfY71yO");
	}
	
	public View createTabIcon(int resId, String title) {

		View view = LayoutInflater.from(this).inflate(R.layout.tab, null);

		ImageView iv = (ImageView) view.findViewById(R.id.iv);
		iv.setImageResource(resId);

		TextView tv = (TextView) view.findViewById(R.id.tv);
		tv.setText(title);
		tv.setGravity(Gravity.CENTER);
		return view;
	}
	
}
