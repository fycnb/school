package com.example.nooneschool.my;

import com.example.nooneschool.R;
import com.example.nooneschool.R.id;
import com.example.nooneschool.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;

public class PersonalDataActivity extends Activity implements View.OnClickListener{
	private ImageView iv_return;
	private TextView tv_username;
	private TextView tv_iphone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_data);
		init();
	}

	private void init(){
		iv_return = (ImageView) findViewById(R.id.person_return_imageview);
		tv_username = (TextView) findViewById(R.id.person_username_textview);
		tv_iphone = (TextView) findViewById(R.id.person_iphone_textview);
		
		String username = "cjq";
		
		String iphone="13540176679";
		StringBuilder sbiphone = new StringBuilder(iphone);
		sbiphone.substring(0, 2);
		sbiphone.replace(3, 7, "****");
		sbiphone.substring(7, 11);
		
		tv_iphone.setText(sbiphone.toString());

		
		tv_username.setText(username);
		iv_return.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_return_imageview:
			finish();
			break;
	

		default:
			break;
		}
	}
}
