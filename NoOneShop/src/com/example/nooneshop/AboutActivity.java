package com.example.nooneshop;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AboutActivity extends Activity {

	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		back = (ImageView) findViewById(R.id.about_back_iv);
		
		back.setOnClickListener(new onAboutClick());
	}

	public class onAboutClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.about_back_iv:
				finish();
				break;
			}
		}

	}
}
