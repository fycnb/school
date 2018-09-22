package com.example.nooneschool.home;

import com.example.nooneschool.R;
import com.example.nooneschool.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AdActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad);
		
		Intent intent = getIntent();
		String id = intent.getStringExtra("id");
		Toast.makeText(this, id, 0).show();
		
		
	}
}
