package com.example.nooneshop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneshop.adapter.AllOrderAdapter;
import com.example.nooneshop.list.AllOrderList;
import com.example.nooneshop.service.MyService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class AllOrderActivity extends Activity {

	private ImageView back;
	private Spinner month;
	private Spinner state;
	private Spinner sort;
	private ListView listview;
	private TextView empty;
	private int state1 = 0;
	private int state2 = 0;
	private int state3 = 0;
	private adapter ad;
	private AllOrderAdapter adapter;
	private List<AllOrderList> list;
	private SwipeRefreshLayout swipeRefreshLayout;
	private SwipeRefreshLayout emptyRefreshLayout;

	private ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(128));
	private Runnable getOrderDataRunnable;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_order);

		id = getIntent().getStringExtra("id");
		back = (ImageView) findViewById(R.id.allorder_back_iv);
		month = (Spinner) findViewById(R.id.allorder_month_spn);
		state = (Spinner) findViewById(R.id.allorder_state_spn);
		sort = (Spinner) findViewById(R.id.allorder_sort_spn);
		listview = (ListView) findViewById(R.id.allorder_list_lv);
		empty = (TextView) findViewById(R.id.allorder_empty_lv);
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.allorder_refresh_layout);
		emptyRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.allorder_empty_layout);

		swipeRefreshLayout.setOnRefreshListener(new onRefresh());
		emptyRefreshLayout.setOnRefreshListener(new onRefresh());

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startActivity(new Intent(AllOrderActivity.this, OrderDetailActivity.class)
						.putExtra("id", list.get(position).getOrderid()).putExtra("time", list.get(position).getTime())
						.putExtra("state", list.get(position).getState()));
			}
		});
		month.setOnItemSelectedListener(new typeSelect(1));
		state.setOnItemSelectedListener(new typeSelect(2));
		sort.setOnItemSelectedListener(new typeSelect(3));

		setData();

		adapter = new AllOrderAdapter(AllOrderActivity.this);
		listview.setAdapter(adapter);
		listview.setEmptyView(emptyRefreshLayout);
		getOrder(state1, state2, state3);

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
			getOrder(state1, state2, state3);
		}

	}

	public class typeSelect implements OnItemSelectedListener {

		private int i;

		public typeSelect(int i) {
			this.i = i;
		}

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			switch (i) {
			case 1:
				state1 = position;
				break;
			case 2:
				state2 = position;
				break;
			case 3:
				state3 = position;
				break;
			}
			getOrder(state1, state2, state3);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}

	}

	public void getOrder(final int state1, final int state2, final int state3) {
		getOrderDataRunnable = new Runnable() {

			@Override
			public void run() {
				final String result = MyService.AllOrderServiceByPost(id, state1, state2, state3,
						"NoOneShop/noone/shop/order/getall?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								list = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String orderid = j.getString("orderid");
									String time = j.getString("time");
									int state = j.getInt("state");

									list.add(new AllOrderList(orderid, time, state));
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
							empty.setText("请检查网络");
						}
					}
				});
			}
		};

		cachedThreadPool.execute(getOrderDataRunnable);
	}

	public class adapter extends BaseAdapter {

		List<String> datas = new ArrayList<>();

		public void setDatas(List<String> datas) {
			this.datas = datas;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHodler hodler = null;
			if (convertView == null) {
				hodler = new ViewHodler();
				convertView = LayoutInflater.from(AllOrderActivity.this).inflate(R.layout.spinner_classify, null);
				hodler.mTextView = (TextView) convertView.findViewById(R.id.spinner_classify);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
			}

			hodler.mTextView.setText(datas.get(position));

			return convertView;
		}

		private class ViewHodler {
			TextView mTextView;
		}

	}

	public void setData() {
		List<String> list1 = new ArrayList<>();
		list1.add("全部");
		list1.add("本月");
		list1.add("近三个月");
		ad = new adapter();
		month.setAdapter(ad);
		ad.setDatas(list1);
		List<String> list2 = new ArrayList<>();
		list2.add("全部");
		list2.add("完成");
		list2.add("未完成");
		ad = new adapter();
		state.setAdapter(ad);
		ad.setDatas(list2);
		List<String> list3 = new ArrayList<>();
		list3.add("升序");
		list3.add("降序");
		ad = new adapter();
		sort.setAdapter(ad);
		ad.setDatas(list3);
	}
}
