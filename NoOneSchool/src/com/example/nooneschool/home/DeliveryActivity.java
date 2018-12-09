package com.example.nooneschool.home;

import java.util.ArrayList;

import com.example.nooneschool.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class DeliveryActivity extends Activity {

	private ImageView back;
	private RadioGroup type;
	private EditText detail;
	private EditText fee;
	private EditText buy;
	private LinearLayout show;
	private EditText address;
	private EditText starth;
	private EditText startm;
	private EditText endh;
	private EditText endm;
	private CheckBox verify;
	private Button submit;
	private Button select;
	private ListPopupWindow listPopupWindow;

	private String id;
	private ArrayList<String> list = new ArrayList<>();
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery);

		id = getIntent().getStringExtra("id");
		back = (ImageView) findViewById(R.id.delivery_back_iv);
		type = (RadioGroup) findViewById(R.id.delivery_type_rg);
		detail = (EditText) findViewById(R.id.delivery_detail_et);
		fee = (EditText) findViewById(R.id.delivery_fee_et);
		buy = (EditText) findViewById(R.id.delivery_buy_et);
		show = (LinearLayout) findViewById(R.id.delivery_buy_ll);
		address = (EditText) findViewById(R.id.delivery_address_et);
		starth = (EditText) findViewById(R.id.delivery_starth_et);
		startm = (EditText) findViewById(R.id.delivery_startm_et);
		endh = (EditText) findViewById(R.id.delivery_endh_et);
		endm = (EditText) findViewById(R.id.delivery_endm_et);
		verify = (CheckBox) findViewById(R.id.delivery_verify_cb);
		submit = (Button) findViewById(R.id.delivery_submit_btn);
		select = (Button) findViewById(R.id.delivery_address_btn);

		back.setOnClickListener(new onDeliveryClick());
		submit.setOnClickListener(new onDeliveryClick());
		select.setOnClickListener(new onDeliveryClick());

		starth.addTextChangedListener(new onDelivery(1));
		startm.addTextChangedListener(new onDelivery(2));
		endh.addTextChangedListener(new onDelivery(3));
		endm.addTextChangedListener(new onDelivery(4));

		type.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.delivery_take_rb:
					show.setVisibility(8);
					break;
				case R.id.delivery_buy_rb:
					show.setVisibility(0);
					break;
				}
			}
		});

		verify.setOnCheckedChangeListener(new onCheckBoxChange());

		sp = getApplicationContext().getSharedPreferences("address", Context.MODE_PRIVATE);
		for (int i = 0; !sp.getString(i + "", "0").equals("0"); i++) {
			list.add(sp.getString(i + "", "0"));
		}

	}

	private class onDelivery implements TextWatcher {

		private int type;

		public onDelivery(int type) {
			this.type = type;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			switch (type) {
			case 1:
				if (Integer.parseInt(starth.getText().toString()) >= 24) {
					starth.setText(starth.getText().toString().subSequence(0, 1));
					starth.setSelection(1);
					starth.requestFocus();
				}
				if (starth.getText().toString().length() == 2) {
					startm.requestFocus();
				}
				break;
			case 2:
				if (Integer.parseInt(startm.getText().toString()) >= 60){
					startm.setText(startm.getText().toString().subSequence(0, 1));
					startm.setSelection(1);
					startm.requestFocus();
				}
				if (startm.getText().toString().length() == 2) {
					endh.requestFocus();
				}
				break;
			case 3:
				if (Integer.parseInt(endh.getText().toString()) >= 24){
					endh.setText(endh.getText().toString().subSequence(0, 1));
					endh.setSelection(1);
					endh.requestFocus();
				}
				if (endh.getText().toString().length() == 2) {
					endm.requestFocus();
				}
				break;
			case 4:
				if (Integer.parseInt(endm.getText().toString()) >= 60){
					endm.setText(endm.getText().toString().subSequence(0, 1));
					endm.setSelection(1);
					endm.requestFocus();
				}
				break;
			}
		}

	}

	private class onCheckBoxChange implements CompoundButton.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				submit.setEnabled(true);
			} else {
				submit.setEnabled(false);
			}
		}
	}

	private class onDeliveryClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.delivery_back_iv:
				finish();
				break;
			case R.id.delivery_submit_btn:
				order();
				break;
			case R.id.delivery_address_btn:
				listPopupWindow = new ListPopupWindow(DeliveryActivity.this);
				listPopupWindow.setAdapter(
						new ArrayAdapter<String>(DeliveryActivity.this, android.R.layout.simple_list_item_1, list));
				listPopupWindow.setAnchorView(address);
				listPopupWindow.setModal(true);

				listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
						address.setText(list.get(i));
						listPopupWindow.dismiss();
					}
				});
				listPopupWindow.show();
				break;
			}
		}

	}

	public void order() {
		new Thread(new Runnable() {
			public void run() {

			}
		}).start();
	}
}
