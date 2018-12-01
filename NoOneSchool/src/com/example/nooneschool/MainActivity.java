package com.example.nooneschool;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabhost = getTabHost();

		Intent one = new Intent(MainActivity.this, HomeActivity.class);
		tabhost.addTab(tabhost.newTabSpec("one").setIndicator(createTabIcon(R.color.home, "首页")).setContent(one));

//		Intent two = new Intent(MainActivity.this, DatingActivity.class);
//		tabhost.addTab(tabhost.newTabSpec("two").setIndicator(createTabIcon(R.color.dating, "社交"))
//				.setContent(two.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		Intent three = new Intent(MainActivity.this, LessonActivity.class);
		tabhost.addTab(tabhost.newTabSpec("three").setIndicator(createTabIcon(R.color.lesson, "课表"))
				.setContent(three.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		Intent four = new Intent(MainActivity.this, MyActivity.class);
		tabhost.addTab(tabhost.newTabSpec("four").setIndicator(createTabIcon(R.color.my, "我的"))
				.setContent(four.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

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
