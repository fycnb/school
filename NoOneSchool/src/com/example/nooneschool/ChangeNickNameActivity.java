package com.example.nooneschool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.nooneschool.my.PublishCommentActivity;
import com.example.nooneschool.my.service.ChangeNicknameService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangeNickNameActivity extends Activity implements View.OnClickListener {
	private EditText et_nickname;
	private Button btn_confirm;
	private ImageView iv_return;
	private String nickname;
	private String userid;
	
	private ExecutorService singleThreadExeutor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_nick_name);
		init();
	}

	private void init() {
		singleThreadExeutor = Executors.newSingleThreadExecutor();
		et_nickname = (EditText) findViewById(R.id.change_nickname_edittext);
		btn_confirm = (Button) findViewById(R.id.changenick_confirm_button);
		iv_return = (ImageView) findViewById(R.id.changenick_return_imageview);

		Intent intent = getIntent();
		nickname = intent.getStringExtra("nickname");
		userid = intent.getStringExtra("userid");

		et_nickname.setText(nickname);

		btn_confirm.setOnClickListener(this);
		iv_return.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.changenick_return_imageview:
			ChangeNickNameActivity.this.finish();
			break;

		case R.id.changenick_confirm_button:
			Runnable runnable = new Runnable() {
				public void run() {
					String newnickname = et_nickname.getText().toString();
					final String result = ChangeNicknameService.ChangeNicknameByPost(userid,newnickname);
					if (result != null) {
						try {

							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(ChangeNickNameActivity.this, result, 0).show();
									ChangeNickNameActivity.this.finish();
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
			break;

		default:
			break;
		}
	}

}
