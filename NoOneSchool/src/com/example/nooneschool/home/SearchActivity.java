package com.example.nooneschool.home;

import com.example.nooneschool.R;
import com.example.nooneschool.R.layout;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class SearchActivity extends Activity {

	private ImageView back;
	private ImageView clean;
	private ImageView search;
	private EditText input;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		back = (ImageView) findViewById(R.id.search_back_imageview);
		clean = (ImageView) findViewById(R.id.search_clean_imageview);
		search = (ImageView) findViewById(R.id.search_search_imageview);
		input = (EditText) findViewById(R.id.search_edit_edittext);
		
		input.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (input.getText().toString().equals("")) {
					clean.setVisibility(4);
				} else
					clean.setVisibility(0);
			}
		});
		
		clean.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				input.setText("");
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchInfo();
			}
		});
	}
	
	private void searchInfo(){
		input.getText().clear();
	}
	
}
