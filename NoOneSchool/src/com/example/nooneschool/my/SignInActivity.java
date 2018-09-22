package com.example.nooneschool.my;

import com.example.nooneschool.R;
import com.example.nooneschool.my.service.SignInSuccessService;
import com.example.nooneschool.my.utils.SignDate;
import com.example.nooneschool.my.inter.OnSignedSuccess;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SignInActivity extends Activity implements View.OnClickListener {
	private SignDate signdate;
	private ImageView iv_return;
	private String userid = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		init();
		signedsuccess();
		iv_return.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signin_return_imageview:
			finish();
			break;

		default:
			break;
		}
	}

	private void init() {
		signdate = (SignDate) findViewById(R.id.signdate);
		iv_return = (ImageView) findViewById(R.id.signin_return_imageview);
		signdate.thread(userid);
	}

	private void signedsuccess() {
		signdate.setOnSignedSuccess(new OnSignedSuccess() {

			@Override
			public void OnSignedSuccess() {
				Log.i("cjq", "sign in success");
				thread();

			}

		});
	}

	private void thread() {
		new Thread() {
			public void run() {
				final String result = SignInSuccessService.SignInSuccessByPost(userid);
				if (result != null) {
					try {
						if (result.equals("签到成功")) {
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(SignInActivity.this, "Sign in success!", 0).show();
								}
							});

						} else if (result.equals("签到失败")) {
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(SignInActivity.this, "Sign in fail!", 0).show();
								}
							});
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {

				}
			}
		}.start();
	}

}
