package com.example.nooneschool.my;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.MyOrderDeatilActivity;
import com.example.nooneschool.R;
import com.example.nooneschool.my.adapter.MyOrderAdapter;
import com.example.nooneschool.my.service.MyOrderService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MyOrderActivity extends Activity implements View.OnClickListener {
	private ImageView iv_return;
	private MyOrderAdapter mMyOrderAdapter;
	private ListView lv_myorder;
	private List<MyOrder> mMyOrders;
	private String userid = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		init();
		getdata(userid);
	}

	private void init() {
		iv_return = (ImageView) findViewById(R.id.myorder_return_imageview);
		lv_myorder = (ListView) findViewById(R.id.myorder_listview);
		iv_return.setOnClickListener(this);
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
		new Thread() {
			public void run() {
				final String result = MyOrderService.MyOrderByPost(userid);
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
							mMyOrders.add(new MyOrder(name, total, time, state, image, orderid));
							mMyOrderAdapter = new MyOrderAdapter(MyOrderActivity.this, mMyOrders);

						}

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								
								lv_myorder.setAdapter(mMyOrderAdapter);
								lv_myorder.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
										final String orderid = ((MyOrder) mMyOrderAdapter.getItem(position))
												.getOrderid();
										runOnUiThread(new Runnable() {
											public void run() {
												Intent intent = new Intent(MyOrderActivity.this,
														MyOrderDeatilActivity.class);
												intent.putExtra("orderid", orderid);
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

				}
			}
		}.start();
	}

}
