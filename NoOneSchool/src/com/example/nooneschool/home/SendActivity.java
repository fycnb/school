package com.example.nooneschool.home;

import com.example.nooneschool.R;
import com.example.nooneschool.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SendActivity extends Activity {

	private ImageView back;
	private Button agree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);

		back = (ImageView) findViewById(R.id.send_back_iv);
		agree = (Button) findViewById(R.id.send_agree_btn);

		back.setOnClickListener(new SendOnClick());
		agree.setOnClickListener(new SendOnClick());
	}

	private class SendOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.send_agree_btn:
				Intent intent = new Intent(SendActivity.this, SendDetailActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.send_back_iv:
				finish();
				break;
			}
		}
	}
}
