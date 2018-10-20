package com.example.nooneschool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.dating.DatingService;
import com.example.nooneschool.dating.FlockActivity;
import com.example.nooneschool.dating.FriendActivity;
import com.example.nooneschool.dating.InfoActivity;
import com.example.nooneschool.dating.RoomActivity;
import com.example.nooneschool.dating.TempInfoActivity;
import com.example.nooneschool.dating.adapter.AdapterMessage;
import com.example.nooneschool.dating.list.ListMessage;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class DatingActivity extends TabActivity {

	private ImageView address;
	private ImageView add;
	private LinearLayout search;
	private String id;
	private TabHost tabhost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dating);
		
		tabhost = getTabHost();
		
		address = (ImageView) findViewById(R.id.dating_address_imageview);
		add = (ImageView) findViewById(R.id.dating_add_imageview);
		search = (LinearLayout) findViewById(R.id.dating_search_ll);
		
		Intent one = new Intent(DatingActivity.this, TempInfoActivity.class);
		one.putExtra("id", id);
		tabhost.addTab(tabhost.newTabSpec("one").setIndicator("信息")
				.setContent(one.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		Intent two = new Intent(DatingActivity.this, FriendActivity.class);
		two.putExtra("id", id);
		tabhost.addTab(tabhost.newTabSpec("two").setIndicator("好友")
				.setContent(two.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		Intent three = new Intent(DatingActivity.this, FlockActivity.class);
		three.putExtra("id", id);
		tabhost.addTab(tabhost.newTabSpec("three").setIndicator("群聊")
				.setContent(three.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		Intent four = new Intent(DatingActivity.this, RoomActivity.class);
		four.putExtra("id", id);
		tabhost.addTab(tabhost.newTabSpec("four").setIndicator("聊天室")
				.setContent(four.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

		Intent five = new Intent(DatingActivity.this, InfoActivity.class);
		five.putExtra("id", id);
		tabhost.addTab(tabhost.newTabSpec("five").setIndicator("新朋友")
				.setContent(five.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
		
	}
	
	

}
