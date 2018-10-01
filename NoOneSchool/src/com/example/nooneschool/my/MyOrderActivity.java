package com.example.nooneschool.my;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.my.adapter.MyOrderAdapter;
import com.example.nooneschool.my.service.CancelOrderService;
import com.example.nooneschool.my.service.MyOrderService;
import com.example.nooneschool.util.ListItemClickHelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MyOrderActivity extends Activity implements View.OnClickListener, ListItemClickHelp {
	private ImageView iv_return;
	private MyOrderAdapter mMyOrderAdapter;
	private ListView lv_myorder;
	private List<MyOrder> mMyOrders;
	private String userid = "1";
	private ExecutorService singleThreadExeutor;
	private ListItemClickHelp listitemclickhelp = this;
	private String state;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		init();
		getdata(userid);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		init();
		getdata(userid);
	}

	private void init() {
		iv_return = (ImageView) findViewById(R.id.myorder_return_imageview);
		lv_myorder = (ListView) findViewById(R.id.myorder_listview);
		iv_return.setOnClickListener(this);
		singleThreadExeutor = Executors.newSingleThreadExecutor();

		Intent intent = getIntent();
		state = intent.getStringExtra("state");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myorder_return_imageview:
			MyOrderActivity.this.finish();
			break;

		default:
			break;
		}
	}

	private void getdata(final String userid) {
		Runnable runnable = new Runnable() {
			public void run() {
				final String result = MyOrderService.MyOrderByPost(userid, state);
				if (result != null) {
					try {
						JSONArray ja = new JSONArray(result);
						mMyOrders = new ArrayList<>();

						for (int i = 0; i < ja.length(); i++) {
							JSONObject j = (JSONObject) ja.get(i);
							String name = j.getString("name");
							String time = j.getString("time");
							String image = j.getString("image");
							String state = j.getString("state");
							String total = j.getString("total");
							String orderid = j.getString("orderid");
							String memo = j.getString("memo");
							String iphone = j.getString("iphone");
							mMyOrders.add(new MyOrder(name, total, time, state, image, orderid, memo, iphone));
							mMyOrderAdapter = new MyOrderAdapter(MyOrderActivity.this, mMyOrders, listitemclickhelp);

						}

						runOnUiThread(new Runnable() {
							@Override
							public void run() {

								lv_myorder.setAdapter(mMyOrderAdapter);
								lv_myorder.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
										MyOrder myOrder = (MyOrder) mMyOrderAdapter.getItem(position);
										final String orderid = myOrder.getOrderid();
										final String name = myOrder.getName();
										final String time = myOrder.getTime();
										final String memo = myOrder.getMemo();
										final String state = myOrder.getState();
										final String iphone = myOrder.getIphone();

										runOnUiThread(new Runnable() {
											public void run() {
												Intent intent = new Intent(MyOrderActivity.this,
														MyOrderDeatilActivity.class);
												intent.putExtra("orderid", orderid);
												intent.putExtra("name", name);
												intent.putExtra("time", time);
												intent.putExtra("memo", memo);
												intent.putExtra("state", state);
												intent.putExtra("iphone", iphone);
												startActivity(intent);
											}
										});

									}

								});

							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					// 没有返回结果
				}
			}
		};
		singleThreadExeutor.execute(runnable);

	}

	@Override
	public void onClick(View item, View widget, int position, int which) {
		final String orderid = mMyOrders.get(position).getOrderid();
		final String name = mMyOrders.get(position).getName();
		final String image = mMyOrders.get(position).getImage();

		switch (which) {
		case R.id.myorder_cancel_button:
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					final String result = CancelOrderService.CancelOrderByPost(userid, orderid);
					if (result != null) {
						try {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									getdata(userid);
									mMyOrderAdapter.notifyDataSetChanged();
									Toast.makeText(MyOrderActivity.this, result, Toast.LENGTH_SHORT).show();
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						// 没有返回结果
					}
				}
			};
			singleThreadExeutor.execute(runnable);
			break;
		case R.id.myorder_comment_button:
			Intent intent = new Intent(MyOrderActivity.this, PublishCommentActivity.class);
			intent.putExtra("userid", userid);
			intent.putExtra("orderid", orderid);
			intent.putExtra("name", name);
			intent.putExtra("image", image);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
