package com.example.nooneshop;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneshop.service.MyService;
import com.example.nooneshop.utils.Date_TimeUtil;
import com.example.nooneshop.utils.MyToast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class AddActivityActivity extends Activity {

	private ImageView back;
	private TextView title;
	private TextView start;
	private TextView end;
	private Spinner type;
	private EditText content;
	private TextView number;
	private Button reset;
	private Button submit;
	private Date_TimeUtil dtUtil;
	private adapter ad;

	private List<String> list;
	private String id;
	private int tp = 0;
	private String st = "选择时间";
	private String ed = "选择时间";
	private String ct;
	private int FLAG = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_activity);

		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		back = (ImageView) findViewById(R.id.addactivity_back_iv);
		title = (TextView) findViewById(R.id.addactivity_title_tv);
		start = (TextView) findViewById(R.id.addactivity_start_tv);
		end = (TextView) findViewById(R.id.addactivity_end_tv);
		type = (Spinner) findViewById(R.id.addactivity_type_spn);
		content = (EditText) findViewById(R.id.addactivity_context_et);
		number = (TextView) findViewById(R.id.addactivity_number_tv);
		reset = (Button) findViewById(R.id.addactivity_reset_btn);
		submit = (Button) findViewById(R.id.addactivity_submit_btn);
		dtUtil = new Date_TimeUtil(this);

		back.setOnClickListener(new onActivityClick());
		start.setOnClickListener(new onActivityClick());
		end.setOnClickListener(new onActivityClick());
		reset.setOnClickListener(new onActivityClick());
		submit.setOnClickListener(new onActivityClick());

		list = new ArrayList<>();
		list.add("请选择类别");
		list.add("代币");
		list.add("降价");
		list.add("赠送");
		ad = new adapter();
		type.setAdapter(ad);
		ad.setDatas(list);

		if (intent.getStringExtra("activityid") != null) {
			id = intent.getStringExtra("activityid");
			tp = intent.getIntExtra("type", 0);
			st = intent.getStringExtra("start");
			ed = intent.getStringExtra("end");
			ct = intent.getStringExtra("context");
			title.setText("修改活动");
			setData();
			FLAG = 1;
		}
		content.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				number.setText(content.getText().length() + "");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	public class onActivityClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.addactivity_back_iv:
				finish();
				break;
			case R.id.addactivity_start_tv:
				dtUtil.setDate(start);
				break;
			case R.id.addactivity_end_tv:
				dtUtil.setDate(end);
				break;
			case R.id.addactivity_reset_btn:
				setData();
				break;
			case R.id.addactivity_submit_btn:
				if (type.getSelectedItemId() != 0 && !start.getText().toString().equals("")
						&& !end.getText().toString().equals("") && !content.getText().toString().equals(""))
					if (FLAG == 0)
						addActivity();
					else
						editActivity();
				else
					MyToast.Toast(AddActivityActivity.this, "请填完整!");
				break;
			}
		}

	}

	public void setData() {
		type.setSelection(tp);
		start.setText(st);
		end.setText(ed);
		content.setText(ct);
		content.setSelection(ct.length());
	}

	public void addActivity() {
		new Thread(new Runnable() {
			public void run() {
				final String result = MyService.AddActivityServiceByPost(id, type.getSelectedItemPosition(),
						start.getText().toString(), end.getText().toString(), content.getText().toString(),
						"NoOneShop/noone/shop/activity/add?");
				runOnUiThread(new Runnable() {
					public void run() {
						if (result == null) {
							MyToast.Toast(AddActivityActivity.this, "请检查网络!");
						} else if (result.equals("100")) {
							setResult(1, getIntent());
							finish();
						} else if (result.equals("200")) {
							MyToast.Toast(AddActivityActivity.this, "添加失败,请重新添加!");
						}
					}
				});
			}
		}).start();
	}

	public void editActivity() {
		new Thread(new Runnable() {
			public void run() {
				final String result = MyService.AddActivityServiceByPost(id, type.getSelectedItemPosition(),
						start.getText().toString(), end.getText().toString(), content.getText().toString(),
						"NoOneShop/noone/shop/activity/edit?");
				runOnUiThread(new Runnable() {
					public void run() {
						if (result == null) {
							MyToast.Toast(AddActivityActivity.this, "请检查网络!");
						} else if (result.equals("100")) {
							setResult(1, getIntent());
							finish();
						} else if (result.equals("200")) {
							MyToast.Toast(AddActivityActivity.this, "修改失败,请重新修改!");
						}
					}
				});
			}
		}).start();
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
				convertView = LayoutInflater.from(AddActivityActivity.this).inflate(R.layout.spinner_type, null);
				hodler.mTextView = (TextView) convertView.findViewById(R.id.spinner_type);
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
}
