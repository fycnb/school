package com.example.nooneschool.my;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.nooneschool.R;
import com.example.nooneschool.my.service.ChangePasswordService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ChangePasswordActivity extends Activity implements View.OnClickListener{
	private EditText et_currentpw;
	private EditText et_newpw;
	private EditText et_confirmpw;
	private ToggleButton tb_currentpw;
	private ToggleButton tb_newpw;
	private ToggleButton tb_confirmpw;
	private Button btn_confirm;
	private ImageView iv_return;
	private String userid;
	
	private ExecutorService singleThreadExeutor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		init();
	}

	private void init() {
		singleThreadExeutor = Executors.newSingleThreadExecutor();
		et_currentpw = (EditText) findViewById(R.id.current_password_edittext);
		et_newpw = (EditText) findViewById(R.id.new_password_edittext);
		et_confirmpw = (EditText) findViewById(R.id.confirm_newpassword_edittext);
		tb_currentpw = (ToggleButton) findViewById(R.id.show_current_password);
		tb_newpw = (ToggleButton) findViewById(R.id.show_new_password);
		tb_confirmpw = (ToggleButton) findViewById(R.id.show_confirm_newpassword);
		btn_confirm = (Button) findViewById(R.id.changepw_confirm_button);
		iv_return = (ImageView) findViewById(R.id.changepw_return_imageview);

		Intent intent = getIntent();
		userid = intent.getStringExtra("userid");
		
		btn_confirm.setOnClickListener(this);
		iv_return.setOnClickListener(this);
		
		
		showPW(tb_currentpw,et_currentpw);
		showPW(tb_newpw,et_newpw);
		showPW(tb_confirmpw,et_confirmpw);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.changepw_return_imageview:
			ChangePasswordActivity.this.finish();
			break;

		case R.id.changepw_confirm_button:
			final String currentpw = et_currentpw.getText().toString().trim();
			final String newpw = et_newpw.getText().toString().trim();
			String confirmpw = et_confirmpw.getText().toString().trim();
			if(currentpw.equals("")||currentpw==null){
				Toast.makeText(ChangePasswordActivity.this, "请输入当前密码", Toast.LENGTH_SHORT).show();
			}else if(newpw.equals("")||newpw==null){
				Toast.makeText(ChangePasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
			}else if(newpw.equals(currentpw)){
				Toast.makeText(ChangePasswordActivity.this, "新密码和之前的一样，请重新输入", Toast.LENGTH_SHORT).show();
			}else if(confirmpw.equals("")||confirmpw==null){
				Toast.makeText(ChangePasswordActivity.this, "请确认新密码", Toast.LENGTH_SHORT).show();
			}else if(!confirmpw.equals(newpw)){
				Toast.makeText(ChangePasswordActivity.this, "两次输入的密码不一样请重新输入", Toast.LENGTH_SHORT).show();
			}else if(et_currentpw.length()<8 || et_newpw.length()<8 || et_confirmpw.length()<8){
				Toast.makeText(ChangePasswordActivity.this, "密码长度不够", Toast.LENGTH_SHORT).show();
			}else{
				Runnable runnable = new Runnable() {
					public void run() {
						final String result = ChangePasswordService.ChangePasswordByPost(userid,currentpw,newpw);
						if (result != null) {
							try {
								if(result.equals("当前的密码输入不正确")){
									runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(ChangePasswordActivity.this, result, Toast.LENGTH_SHORT).show();
										}
									});
								}else{
									runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(ChangePasswordActivity.this, result, Toast.LENGTH_SHORT).show();
											ChangePasswordActivity.this.finish();
										}
									});
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {

						}
					}
				};
				singleThreadExeutor.execute(runnable);
			}
		
			break;

		default:
			break;
		}
	}
	
	private void showPW(ToggleButton tbpw,final EditText etpw){
		tbpw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                	etpw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                	etpw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
	}

}
