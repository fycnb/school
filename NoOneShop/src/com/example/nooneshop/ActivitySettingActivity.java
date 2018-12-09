package com.example.nooneshop;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneshop.adapter.ActivityAdapter;
import com.example.nooneshop.list.ActivityList;
import com.example.nooneshop.service.MyService;
import com.example.nooneshop.utils.ListItemClickHelp;
import com.example.nooneshop.utils.MyToast;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ActivitySettingActivity extends Activity implements ListItemClickHelp {

	private ImageView back;
	private ImageView add;
	private ListView listview;
	private TextView empty;
	private PopupWindow window;
	private TextView start;
	private TextView edit;
	private TextView delete;
	private SwipeRefreshLayout swipeRefreshLayout;
	private SwipeRefreshLayout emptyRefreshLayout;

	private List<ActivityList> list;
	private ActivityAdapter adapter;
	private ListItemClickHelp callback = this;
	private int STATE = 0;
	private int WHICH = 0;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_setting);

		id = getIntent().getStringExtra("id");
		back = (ImageView) findViewById(R.id.activity_back_iv);
		add = (ImageView) findViewById(R.id.activity_add_iv);
		listview = (ListView) findViewById(R.id.activity_list_lv);
		empty = (TextView) findViewById(R.id.activity_empty_tv);
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_refresh_layout);
		emptyRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_empty_layout);

		swipeRefreshLayout.setOnRefreshListener(new onRefresh());
		emptyRefreshLayout.setOnRefreshListener(new onRefresh());

		add.setOnClickListener(new onActivityClick());
		back.setOnClickListener(new onActivityClick());

		View contentView = LayoutInflater.from(ActivitySettingActivity.this).inflate(R.layout.popup_window2, null,
				false);
		start = (TextView) contentView.findViewById(R.id.pop_start_tv);
		edit = (TextView) contentView.findViewById(R.id.pop_edit_tv);
		delete = (TextView) contentView.findViewById(R.id.pop_delete_tv);
		window = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		window.setOutsideTouchable(true);
		window.setTouchable(true);

		start.setOnClickListener(new onActivityClick());
		edit.setOnClickListener(new onActivityClick());
		delete.setOnClickListener(new onActivityClick());

		adapter = new ActivityAdapter(ActivitySettingActivity.this, callback);
		listview.setAdapter(adapter);
		listview.setEmptyView(emptyRefreshLayout);

		getActivity();
	}

	public class onRefresh implements OnRefreshListener {

		@Override
		public void onRefresh() {
			new Handler().postDelayed(new Runnable() {// 模拟耗时操作
				@Override
				public void run() {
					swipeRefreshLayout.setRefreshing(false);// 取消刷新
					swipeRefreshLayout.setEnabled(true);
					emptyRefreshLayout.setRefreshing(false);// 取消刷新
					emptyRefreshLayout.setEnabled(true);
				}
			}, 4000);
			swipeRefreshLayout.setEnabled(false);
			emptyRefreshLayout.setEnabled(false);
			getActivity();
		}

	}

	public class onActivityClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.activity_back_iv:
				finish();
				break;
			case R.id.activity_add_iv:
				startActivityForResult(
						new Intent(ActivitySettingActivity.this, AddActivityActivity.class).putExtra("id", id), 1);
				break;
			case R.id.pop_start_tv:
				switch (STATE) {
				case 0:
					updateState(list.get(WHICH).getActivityid(), 1);
					break;
				case 1:
					updateState(list.get(WHICH).getActivityid(), 0);
					break;
				}
				break;
			case R.id.pop_edit_tv:
				startActivityForResult(new Intent(ActivitySettingActivity.this, AddActivityActivity.class)
						.putExtra("activityid", list.get(WHICH).getActivityid())
						.putExtra("start", list.get(WHICH).getStartime()).putExtra("end", list.get(WHICH).getEndtime())
						.putExtra("type", list.get(WHICH).getTypeNumber())
						.putExtra("context", list.get(WHICH).getContext()), 1);
				window.dismiss();
				break;
			case R.id.pop_delete_tv:
				deleteGoods(list.get(WHICH).getActivityid());
				break;
			}
		}

	}

	public void getActivity() {
		new Thread(new Runnable() {
			public void run() {
				final String result = MyService.ActivityServiceByPost(id, "NoOneShop/noone/shop/activity/get?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								list = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String activityid = j.getString("activityid");
									int type = j.getInt("type");
									int state = j.getInt("state");
									String startime = j.getString("startime");
									String endtime = j.getString("endtime");
									String context = j.getString("context");

									list.add(new ActivityList(activityid, type, state, startime, endtime, context));
								}
								adapter.setDatas(list);

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (result != null) {
							list = new ArrayList<>();
							adapter.setDatas(list);
							empty.setText("通过右上角按钮添加活动");
						} else {
							empty.setText("请检查网络");
						}
					}
				});
			}
		}).start();
	}

	public void deleteGoods(final String i) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String result = MyService.GoodsServiceByPost(i, "NoOneShop/noone/shop/activity/delete?");

				runOnUiThread(new Runnable() {
					public void run() {
						Log.i("fyc", result);
						if (result == null) {
							MyToast.Toast(ActivitySettingActivity.this, "请检查网络!");
						} else if (result.equals("100")) {
							getActivity();
							window.dismiss();
						} else if (result.equals("200")) {
							MyToast.Toast(ActivitySettingActivity.this, "删除失败!");
						}
					}
				});
			}
		}).start();
	}

	public void updateState(final String i, final int state) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String result = MyService.UpdateGoodsServiceByPost(i, state,
						"NoOneShop/noone/shop/activity/update?");

				runOnUiThread(new Runnable() {
					public void run() {
						if (result == null) {
							MyToast.Toast(ActivitySettingActivity.this, "请检查网络!");
						} else if (result.equals("100")) {
							getActivity();
							window.dismiss();
						} else if (result.equals("200")) {
							MyToast.Toast(ActivitySettingActivity.this, "更新失败!");
						}
					}
				});
			}
		}).start();
	}

	@Override
	public void onClick(View item, View widget, int position, int which) {
		switch (item.getId()) {
		case R.id.activity_more_iv:
			WHICH = position;
			switch (list.get(position).getStateNumber()) {
			case 0:
				STATE = 0;
				start.setText("开启");
				break;
			case 2:
				STATE = 1;
				start.setText("关闭");
				break;
			case 1:
				STATE = 1;
				start.setText("取消");
				break;
			}
			window.showAsDropDown(item);
			break;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 0:

			break;
		case 1:
			getActivity();
			break;
		}
	}
}
