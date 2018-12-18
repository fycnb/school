package com.example.nooneschool.home;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.home.adapter.AdapterLeftMenu;
import com.example.nooneschool.home.adapter.AdapterRightMenu;
import com.example.nooneschool.home.list.ListMenu;
import com.example.nooneschool.util.DataUtil;
import com.example.nooneschool.util.ListItemClickHelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity implements ListItemClickHelp {

	private TextView empty;
	private ListView menuLeftList;
	private ListView menuRightList;
	private AdapterLeftMenu leftAdapter;
	private AdapterRightMenu rightAdapter;
	private List<ListMenu> listmenu;
	private String id;
	private ListItemClickHelp callback = this;

	private ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(128));
	private Runnable getFoodsDataRunnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		empty = (TextView) findViewById(R.id.menu_empty_tv);
		menuLeftList = (ListView) findViewById(R.id.menu_left_listview);
		menuRightList = (ListView) findViewById(R.id.menu_right_listview);

		Intent intent = getIntent();
		id = intent.getStringExtra("id");

		leftAdapter = new AdapterLeftMenu(this, DataUtil.getLeftMenuData());
		menuLeftList.setAdapter(leftAdapter);
		menuLeftList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				leftAdapter.setCheck(position);
				getFoodsData(position);
				leftAdapter.notifyDataSetInvalidated();
			}
		});
		getFoodsData(0);

	}

	public void getFoodsData(final int what) {
		getFoodsDataRunnable = new Runnable() {

			@Override
			public void run() {
				final String result = HomeService.HomeServiceByPost(what, id, "NoOneService/GetMenuServlet?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								listmenu = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String id = j.getString("id");
									String name = j.getString("name");
									String imgurl = j.getString("imgurl");
									String money = j.getString("money");
									listmenu.add(new ListMenu(id, name, imgurl, money));
								}
								rightAdapter = new AdapterRightMenu(MenuActivity.this, listmenu, callback);
								menuRightList.setAdapter(rightAdapter);
								menuRightList.setEmptyView(empty);

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (result != null) {
							listmenu = new ArrayList<>();
							rightAdapter = new AdapterRightMenu(MenuActivity.this, listmenu, callback);
							menuRightList.setAdapter(rightAdapter);
							menuRightList.setEmptyView(empty);
							empty.setText("暂无商品");
						} else {
							listmenu = new ArrayList<>();
							rightAdapter = new AdapterRightMenu(MenuActivity.this, listmenu, callback);
							menuRightList.setAdapter(rightAdapter);
							menuRightList.setEmptyView(empty);
							empty.setText("请检查网络");
						}
					}
				});
			}
		};

		cachedThreadPool.execute(getFoodsDataRunnable);
	}

	@Override
	public void onClick(View item, View widget, int position, int which) {
		// TODO Auto-generated method stub
		switch (item.getId()) {
		case R.id.foods_add_iv:
			Toast.makeText(MenuActivity.this, position + "", 0).show();
			break;
		}
	}
}
