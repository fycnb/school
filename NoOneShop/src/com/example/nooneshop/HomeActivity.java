package com.example.nooneshop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneshop.adapter.OrderAdapter;
import com.example.nooneshop.list.DetailList;
import com.example.nooneshop.list.OrderList;
import com.example.nooneshop.service.HomeService;
import com.example.nooneshop.utils.ListItemClickHelp;
import com.example.nooneshop.utils.MyToast;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends Activity implements ListItemClickHelp {

	private ListView listview;
	private TextView empty;
	private List<OrderList> list;
	private OrderAdapter adapter;
	private SwipeRefreshLayout swipeRefreshLayout;
	private SwipeRefreshLayout emptyRefreshLayout;
	private ListItemClickHelp callback = this;
	private ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(128));
	private Runnable getOrderDataRunnable;
	private Runnable submitOrderRunnable;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		id = getIntent().getStringExtra("id");
		listview = (ListView) findViewById(R.id.home_order_lv);
		empty = (TextView) findViewById(R.id.home_empty_tv);
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_refresh_layout);
		emptyRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_empty_layout);

		swipeRefreshLayout.setOnRefreshListener(new onRefresh());
		emptyRefreshLayout.setOnRefreshListener(new onRefresh());

		adapter = new OrderAdapter(HomeActivity.this, callback);
		listview.setAdapter(adapter);
		listview.setEmptyView(emptyRefreshLayout);

		getOrderData();
	}

	@Override
	protected void onResume() {
		getOrderData();
		super.onResume();
	}

	@Override
	public void onClick(View item, View widget, int position, int which) {
		switch (which) {
		case R.id.order_submit_btn:
			if (list.get(position).getState() == 1)
				submitOrderData(list.get(position).getOrderid(), 2);
			else
				submitOrderData(list.get(position).getOrderid(), -1);
			break;
		case R.id.order_finish_btn:
			submitOrderData(list.get(position).getOrderid(), 1);
			break;
		}
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
			getOrderData();
		}

	}

	private void getOrderData() {
		getOrderDataRunnable = new Runnable() {

			@Override
			public void run() {
				final String result = HomeService.HomeServiceByPost(id, "NoOneShop/noone/shop/order/get?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								list = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String orderid = j.getString("id");
									String detail = j.getString("detail");
									String sender = j.getString("sender");
									String memo = j.getString("memo");
									String time = j.getString("time");
									int state = j.getInt("state");

									List<DetailList> detaillist = new ArrayList<>();
									JSONArray ja2 = new JSONArray(detail);
									for (int n = 0; n < ja2.length(); n++) {
										JSONObject m = (JSONObject) ja2.get(n);
										String id = m.getString("id");
										String img = m.getString("img");
										String name = m.getString("name");
										int number = m.getInt("number");
										float moneyOne = (float) m.getDouble("money");

										detaillist.add(new DetailList(id, img, name, number, moneyOne));
									}
									list.add(new OrderList(orderid, memo, sender, time, state, detaillist));
								}
								adapter.setDatas(list);

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (result != null) {
							list = new ArrayList<>();
							adapter.setDatas(list);
							empty.setText("暂无订单");
						} else {
							list = new ArrayList<>();
							adapter.setDatas(list);
							empty.setText("请检查网络");
						}
					}
				});
			}
		};

		cachedThreadPool.execute(getOrderDataRunnable);
	}

	private void submitOrderData(final String id, final int state) {
		submitOrderRunnable = new Runnable() {

			@Override
			public void run() {
				final String result = HomeService.HomeServiceByPost(id, state, "NoOneShop/noone/shop/order/submit?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result == null) {
							MyToast.Toast(HomeActivity.this, "检查网络!");
						} else if (result.equals("100")) {
							MyToast.Toast(HomeActivity.this, "操作成功!");
							getOrderData();
						} else if (result.equals("200")) {
							MyToast.Toast(HomeActivity.this, "操作失败!");
							getOrderData();
						}
					}
				});
			}
		};

		cachedThreadPool.execute(submitOrderRunnable);
	}

}
