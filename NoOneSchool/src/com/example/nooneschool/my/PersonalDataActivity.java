package com.example.nooneschool.my;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.R.id;
import com.example.nooneschool.R.layout;
import com.example.nooneschool.my.service.UserDataService;
import com.example.nooneschool.my.utils.ImageUtil;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;

public class PersonalDataActivity extends Activity implements View.OnClickListener {
	private ImageView iv_return;
	private TextView tv_nickname;
	private TextView tv_iphone;
	private LinearLayout ll_nickname;
	private LinearLayout ll_password;
	private String nickname;
	private String userid;
	private String account;
	
	private ExecutorService singleThreadExeutor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_data);
		init();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		init();
	}

	private void init() {
		singleThreadExeutor = Executors.newSingleThreadExecutor();
		iv_return = (ImageView) findViewById(R.id.person_return_imageview);
		tv_nickname = (TextView) findViewById(R.id.person_nickname_textview);
		tv_iphone = (TextView) findViewById(R.id.person_iphone_textview);
		ll_nickname = (LinearLayout) findViewById(R.id.person_nickname_linearlayout);
		ll_password = (LinearLayout) findViewById(R.id.person_password_linearlayout);
		
		Intent intent = getIntent();
		userid = intent.getStringExtra("userid");

		iv_return.setOnClickListener(this);
		ll_nickname.setOnClickListener(this);
		ll_password.setOnClickListener(this);
		
		getuserdata();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.person_return_imageview:
			PersonalDataActivity.this.finish();
			break;
			
		case R.id.person_nickname_linearlayout:
			intent = new Intent(PersonalDataActivity.this, ChangeNickNameActivity.class);
			intent.putExtra("nickname", nickname);
			intent.putExtra("userid", userid);
			startActivity(intent);
			break;
			
		case R.id.person_password_linearlayout:
			intent = new Intent(PersonalDataActivity.this, ChangePasswordActivity.class);
			intent.putExtra("userid", userid);
			startActivity(intent);
			break;
			
		default:
			break;
		}
	}
	
	private void getuserdata() {
		Runnable runnable = new Runnable() {
			public void run() {
				final String result = UserDataService.UserDataByPost(userid);
				if (result != null) {
					try {
						JSONObject js = new JSONObject(result);
						account = js.getString("account");
						nickname = js.getString("nickname");
					} catch (Exception e) {
						e.printStackTrace();
					}
					runOnUiThread(new Runnable() {
						public void run() {
							StringBuilder sb = new StringBuilder(account);
							sb.substring(0, 2);
							sb.replace(3, 7, "****");
							sb.substring(7, 11);

							tv_iphone.setText(sb.toString());
							tv_nickname.setText(nickname);
						}
					});
				} else {
					// 没有返回数据

				}
			}
		};
		singleThreadExeutor.execute(runnable);
	}
}
