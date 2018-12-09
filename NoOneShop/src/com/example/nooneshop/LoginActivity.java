package com.example.nooneshop;

import com.example.nooneshop.service.LoginService;
import com.example.nooneshop.utils.MyToast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private EditText account;
	private EditText password;
	private TextView login;
	private TextView register;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		account = (EditText) findViewById(R.id.login_account_et);
		password = (EditText) findViewById(R.id.login_password_et);
		login = (TextView) findViewById(R.id.login_login_tv);
		register = (TextView) findViewById(R.id.login_register_et);

		login.setOnClickListener(new onLoginClick());
		register.setOnClickListener(new onLoginClick());

		if (!MyApplication.sp.getString("id", "null").equals("null")) {
			id = MyApplication.sp.getString("id", "null");
			startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("id", id));
			finish();
		}
	}

	public class onLoginClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_login_tv:
				if (account.getText().toString().equals("") || password.getText().toString().equals("")) {
					MyToast.Toast(LoginActivity.this, "请输入账号密码!");
				} else
					login();
				break;
			case R.id.login_register_et:
				startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 1);
				break;
			}
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 1:
			account.setText(data.getStringExtra("account"));
			password.setText(data.getStringExtra("password"));
			break;
		}
	}

	public void login() {
		new Thread(new Runnable() {
			public void run() {
				final String result = LoginService.LoginServiceByPost(account.getText().toString(),
						password.getText().toString(), "NoOneShop/noone/shop/login?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result == null || result.equals("")) {
							MyToast.Toast(LoginActivity.this, "检查网络!");
						} else if (result.equals("200")) {
							MyToast.Toast(LoginActivity.this, "请检查账号密码!");
						} else {
							id = result;
							MyApplication.editor.putString("id", result);
							MyApplication.editor.commit();
							
							startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("id", id));
							finish();
						}
					}
				});
			}
		}).start();
	}
}
