package com.example.nooneschool.my;

import java.security.PrivilegedActionException;

import com.example.nooneschool.R;
import com.example.nooneschool.my.inter.OnSignedSuccess;
import com.example.nooneschool.my.utils.SignDate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SignInActivity extends Activity {
	private SignDate signdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		init();
		signdate.setOnSignedSuccess(new OnSignedSuccess() {

			@Override
			public void OnSignedSuccess() {
				Log.i("cjq", "sign in success");
			}

		});
	}
	
	private void init(){
		signdate = (SignDate) findViewById(R.id.signdate);
	}
	
	

}
