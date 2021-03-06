package com.example.nooneschool.my;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.nooneschool.R;
import com.example.nooneschool.my.inter.OnSignedSuccess;
import com.example.nooneschool.my.service.SignInSuccessService;
import com.example.nooneschool.my.utils.SignDate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SignInActivity extends Activity implements View.OnClickListener {
	private SignDate signdate;
	private ImageView iv_return;
	private ExecutorService singleThreadExeutor;
	private String userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		init();
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signin_return_imageview:
			SignInActivity.this.finish();
			break;

		default:
			break;
		}
	}

	private void init() {
		singleThreadExeutor = Executors.newSingleThreadExecutor();
		signdate = (SignDate) findViewById(R.id.signdate);
		iv_return = (ImageView) findViewById(R.id.signin_return_imageview);
		
		Intent intent = getIntent();
		userid = intent.getStringExtra("userid");
		signdate.getsignindata(userid);
		signedsuccess();
		iv_return.setOnClickListener(this);
	}

	private void signedsuccess() {
		signdate.setOnSignedSuccess(new OnSignedSuccess() {

			@Override
			public void OnSignedSuccess() {
				Log.i("cjq", "sign in success");
				signin();
			}

		});
	}

	private void signin() {
		Runnable runnable = new Runnable() {
			public void run() {
				final String result = SignInSuccessService.SignInSuccessByPost(userid);
				if (result != null) {
					try {
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(SignInActivity.this, result, 0).show();
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

}
