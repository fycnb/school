package com.example.nooneshop;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneshop.adapter.OrderDetailAdapter;
import com.example.nooneshop.list.DetailList;
import com.example.nooneshop.service.MyService;
import com.example.nooneshop.utils.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class OrderDetailActivity extends Activity {

	private LinearLayout show;
	private TextView orderid;
	private TextView state;
	private TextView money;
	private TextView time;
	private ListView listview;
	private Button refresh;
	private ImageView back;
	
	private List<DetailList> list;
	private OrderDetailAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		
		show = (LinearLayout) findViewById(R.id.orderdetail_show_ll);
		orderid = (TextView) findViewById(R.id.orderdetail_id_tv);
		state = (TextView) findViewById(R.id.orderdetail_state_tv);
		money = (TextView) findViewById(R.id.orderdetail_info_tv);
		time = (TextView) findViewById(R.id.orderdetail_itime_tv);
		listview = (ListView) findViewById(R.id.orderdetail_detail_lv);
		refresh = (Button) findViewById(R.id.orderdetail_refresh_btn);
		back = (ImageView) findViewById(R.id.orderdetail_back_iv);
		
		final Intent intent = getIntent();
		orderid.setText(intent.getStringExtra("id"));
		state.setText(intent.getStringExtra("state"));
		time.setText(intent.getStringExtra("time"));
		
		

		adapter = new OrderDetailAdapter(OrderDetailActivity.this);
		listview.setAdapter(adapter);
		Utility.setListViewHeight(listview);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		getDetail(intent.getStringExtra("id"));
		
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getDetail(intent.getStringExtra("id"));
			}
		});
	}
	
	public void getDetail(final String id) {
		new Thread(new Runnable() {
			public void run() {
				final String result = MyService.OneOrderServiceByPost(id, "NoOneShop/noone/shop/order/getone?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								list = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								float total = 0; 
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String id = j.getString("id");
									String img = j.getString("img");
									String name = j.getString("name");
									float money = (float) j.getDouble("money");
									int number = j.getInt("number");
									total +=money*number;
									list.add(new DetailList(id, img, name, number, money));
								}
								money.setText("共"+total+"元");
								adapter.setDatas(list);
								Utility.setListViewHeight(listview);
								show.setVisibility(0);
								refresh.setVisibility(8);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else{
							show.setVisibility(8);
							refresh.setVisibility(0);
						}
					}
				});
			}
		}).start();
	}
}
