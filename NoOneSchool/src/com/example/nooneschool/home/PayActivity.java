package com.example.nooneschool.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.home.adapter.AddressAdapter;
import com.example.nooneschool.home.adapter.OrderAdapter2;
import com.example.nooneschool.home.list.OrderList;
import com.example.nooneschool.util.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PayActivity extends Activity {

	private ImageView back;
	private ImageView add;
	private ListView address;
	private ListView order;
	private TextView cancel;
	private TextView pay;
	private EditText memo;
	private TextView empty;
	private TextView money;
	private AlertDialog dialog;
	private EditText school;
	private EditText floor;
	private EditText home;
	private Button popcancel;
	private Button popsubmit;

	private List<OrderList> listorder = new ArrayList<>();
	private List<String> listaddress = new ArrayList<>();
	private String id;
	private OrderAdapter2 orderadapter;
	private AddressAdapter addressadapter;
	private SharedPreferences sp;
	private int max = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);

		back = (ImageView) findViewById(R.id.pay_back_iv);
		add = (ImageView) findViewById(R.id.pay_add_iv);
		address = (ListView) findViewById(R.id.pay_address_lv);
		order = (ListView) findViewById(R.id.pay_order_lv);
		cancel = (TextView) findViewById(R.id.pay_cancel_tv);
		pay = (TextView) findViewById(R.id.pay_pay_tv);
		memo = (EditText) findViewById(R.id.pay_memo_et);
		empty = (TextView) findViewById(R.id.pay_empty_tv);
		money = (TextView) findViewById(R.id.pay_money_tv);

		sp = getApplicationContext().getSharedPreferences("address", Context.MODE_PRIVATE);
		while (!sp.getString(max + "", "0").equals("0")) {
			listaddress.add(new String(sp.getString(max + "", "0")));
			max++;
		}
		listorder = (List<OrderList>) getIntent().getSerializableExtra("list");
		id = getIntent().getStringExtra("id");

		back.setOnClickListener(new onPayClick());
		add.setOnClickListener(new onPayClick());
		cancel.setOnClickListener(new onPayClick());
		pay.setOnClickListener(new onPayClick());

		addressadapter = new AddressAdapter(PayActivity.this, listaddress);
		address.setAdapter(addressadapter);
		address.setEmptyView(empty);
		address.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		address.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				addressadapter.setSelect(position);
			}
		});
		Utility.setListViewHeight(address);

		orderadapter = new OrderAdapter2(PayActivity.this, listorder);
		order.setAdapter(orderadapter);
		Utility.setListViewHeight(order);

		money.setText("共" + allMoney() + "元");

	}

	public class onPayClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.pay_back_iv:
				finish();
				break;
			case R.id.pay_add_iv:
				AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this, R.style.logstyle);
				View logview = View.inflate(PayActivity.this, R.layout.dialog_address, null);
				builder.setView(logview);
				dialog = builder.show();
				school = (EditText) logview.findViewById(R.id.address_school_et);
				floor = (EditText) logview.findViewById(R.id.address_floor_et);
				home = (EditText) logview.findViewById(R.id.address_home_et);
				popcancel = (Button) logview.findViewById(R.id.address_cancel_btn);
				popsubmit = (Button) logview.findViewById(R.id.address_submit_btn);

				popcancel.setOnClickListener(new onPayClick());
				popsubmit.setOnClickListener(new onPayClick());

				break;
			case R.id.pay_cancel_tv:
				setResult(1, getIntent());
				finish();
				break;
			case R.id.pay_pay_tv:
				if (addressadapter.getSelect() == -1) {
					Toast.makeText(PayActivity.this, "请选择地址!", 0).show();
				} else {
					pay(id, listorder, memo.getText().toString(), listaddress.get(addressadapter.getSelect()));
				}
				break;
			case R.id.address_cancel_btn:
				dialog.dismiss();
				break;
			case R.id.address_submit_btn:
				if (school.getText().toString().equals("") || floor.getText().toString().equals("")
						|| home.getText().toString().equals("")) {
					Toast.makeText(PayActivity.this, "请填完整", 0).show();
				} else {
					Editor editor = sp.edit();
					editor.putString(max + "", school.getText().toString() + "-" + floor.getText().toString() + "-"
							+ home.getText().toString());
					editor.commit();
					listaddress.add(new String(sp.getString(max + "", "0")));
					addressadapter.notifyDataSetChanged();
					Utility.setListViewHeight(address);
					max++;
					dialog.dismiss();
				}
				break;
			}
		}

	}

	public float allMoney() {
		float money = 0;
		for (int i = 0; i < listorder.size(); i++) {
			money += listorder.get(i).getMomey() * listorder.get(i).getNumber();
		}
		return money;
	}

	public void pay(final String id, final List<OrderList> listorder, final String memo, final String address) {
		new Thread(new Runnable() {
			public void run() {
				JSONArray js = new JSONArray();
				try {
					for (int i = 0; i < listorder.size(); i++) {
						JSONObject json = new JSONObject();
						String id = listorder.get(i).getId();
						String img = listorder.get(i).getImg();
						String name = listorder.get(i).getName();
						float money = listorder.get(i).getMomey();
						float number = listorder.get(i).getNumber();

						json.put("id", id);
						json.put("img", img);
						json.put("name", name);
						json.put("money", money);
						json.put("number", number);
						js.put(json);
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				final String result = HomeService.OrderServiceByPost(id, js.toString(),memo,address);
				runOnUiThread(new Runnable() {
					public void run() {
						if (result == null) {
							
						} else if (result.equals("100")) {
							
						} else if (result.equals("200")) {
							
						}
					}
				});
			}
		}).start();
	}
}
