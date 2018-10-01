package com.example.nooneschool.my;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.my.adapter.MyOrderDetailAdapter;
import com.example.nooneschool.my.service.MyOrderDetailService;
import com.example.nooneschool.util.DensityUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyOrderDeatilActivity extends Activity implements View.OnClickListener {
	private TextView tv_state;
	private TextView tv_restaurant;
	private TextView tv_time;
	private TextView tv_memo;
	private TextView tv_iphone;
	private TextView tv_orderid;

	private ImageView iv_return;
	private RelativeLayout rl_iphone;
	private LinearLayout ll_list;
	private MyOrderDetailAdapter mDetailAdapter;
	private ListView lv_detail;
	private List<MyOrderDetail> mDetails;
	private ExecutorService singleThreadExeutor;

	private String orderid;
	private String restaurant;
	private String time;
	private String memo;
	private String state;
	private String iphone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_deatil);
		init();

	}

	private void init() {
		singleThreadExeutor = Executors.newSingleThreadExecutor();

		tv_state = (TextView) findViewById(R.id.detail_state_textview);
		tv_restaurant = (TextView) findViewById(R.id.detail_restaurant_textview);
		tv_time = (TextView) findViewById(R.id.detail_time_textview);
		tv_memo = (TextView) findViewById(R.id.deatil_memo_textview);
		tv_iphone = (TextView) findViewById(R.id.detail_iphone_textview);
		tv_orderid = (TextView) findViewById(R.id.detail_orderid_textview);

		lv_detail = (ListView) findViewById(R.id.detail_listview);

		ll_list = (LinearLayout) findViewById(R.id.detail_list_linearlayout);
		rl_iphone = (RelativeLayout) findViewById(R.id.detail_iphone_relativelayout);

		iv_return = (ImageView) findViewById(R.id.orderdetail_return_imageview);

		Intent intent = getIntent();
		orderid = intent.getStringExtra("orderid");
		restaurant = intent.getStringExtra("name");
		time = intent.getStringExtra("time");
		memo = intent.getStringExtra("memo");
		state = intent.getStringExtra("state");
		iphone = intent.getStringExtra("iphone");

		tv_state.setText(state);
		tv_time.setText(time);
		tv_memo.setText(memo);
		tv_restaurant.setText(restaurant);
		tv_iphone.setText(iphone);
		tv_orderid.setText(orderid);

		iv_return.setOnClickListener(this);
		rl_iphone.setOnClickListener(this);

		getdata(orderid);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.orderdetail_return_imageview:
			MyOrderDeatilActivity.this.finish();
			break;

		case R.id.detail_iphone_relativelayout:
			Intent intent = new Intent(Intent.ACTION_DIAL);
			Uri data = Uri.parse("tel:" + iphone);
			intent.setData(data);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private void getdata(final String orderid) {
		Runnable runnable = new Runnable() {
			public void run() {
				final String result = MyOrderDetailService.MyOrderDetailByPost(orderid);
				if (result != null) {
					try {
						JSONArray ja = new JSONArray(result);
						mDetails = new ArrayList<>();
						for (int i = 0; i < ja.length(); i++) {
							JSONObject j = (JSONObject) ja.get(i);
							String name = j.getString("name");
							String number = j.getString("number");
							String image = j.getString("image");
							String total = j.getString("total");

							mDetails.add(new MyOrderDetail(image, name, number, total));
							mDetailAdapter = new MyOrderDetailAdapter(MyOrderDeatilActivity.this, mDetails);

						}

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								switch (mDetails.size()) {
								case 1:
									setListHeigh(55);
									break;
								case 2:
									setListHeigh(110);
									break;
								case 3:
									setListHeigh(165);
									break;
								default:
									setListHeigh(210);
									break;
								}

								lv_detail.setAdapter(mDetailAdapter);
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {

				}
			}
		};
		singleThreadExeutor.execute(runnable);
	}

	private void setListHeigh(float dpValue) {
		int heigh = DensityUtil.dip2px(MyOrderDeatilActivity.this, dpValue);
		LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				heigh);
		ll_list.setLayoutParams(linearParams);
	}
}
