package com.example.nooneshop;

import com.example.nooneshop.service.LoginService;
import com.example.nooneshop.utils.MyToast;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActivity extends Activity {

	private ImageView back;
	private EditText phone;
	private EditText password1;
	private EditText password2;
	private EditText address;
	private EditText name;
	private Button register;

	private String pe;
	private String p1;
	private String p2;
	private String as;
	private String ne;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		back = (ImageView) findViewById(R.id.register_back_iv);
		phone = (EditText) findViewById(R.id.register_phone_et);
		password1 = (EditText) findViewById(R.id.register_password1_et);
		password2 = (EditText) findViewById(R.id.register_password2_et);
		address = (EditText) findViewById(R.id.register_address_et);
		name = (EditText) findViewById(R.id.register_name_et);
		register = (Button) findViewById(R.id.register_register_btn);

		back.setOnClickListener(new onRegister());
		register.setOnClickListener(new onRegister());
	}

	public class onRegister implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.register_back_iv:
				finish();
				break;
			case R.id.register_register_btn:
				getContent();
				if (isEmpty(p1) || isEmpty(as) || isEmpty(ne) || isEmpty(p2) || isEmpty(pe)) {
					MyToast.Toast(RegisterActivity.this, "请填完整");
				} else if (!p1.equals(p2)) {
					MyToast.Toast(RegisterActivity.this, "密码不一致");
				} else {
					register();
				}
				break;
			}
		}

	}

	public void register() {
		new Thread(new Runnable() {
			public void run() {
				final String result = LoginService.RegisterServiceByPost(pe, p1, ne, as, "NoOneShop/noone/shop/register?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result == null || result.equals("")) {
							MyToast.Toast(RegisterActivity.this, "检查网络!");
						} else if (result.equals("200")) {
							MyToast.Toast(RegisterActivity.this, "注册失败!");
						} else {
							setResult(1,getIntent());
							getIntent().putExtra("account", pe);
							getIntent().putExtra("password", p1);
							finish();
						}
					}
				});
			}
		}).start();
	}

	public void getContent() {
		pe = phone.getText().toString();
		p1 = password1.getText().toString();
		p2 = password2.getText().toString();
		as = address.getText().toString();
		ne = name.getText().toString();

	}

	public boolean isEmpty(String st) {
		if (st == null)
			return true;
		if (st.equals(""))
			return true;
		return false;
	}
}
