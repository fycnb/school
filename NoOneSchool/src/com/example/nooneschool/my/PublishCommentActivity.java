package com.example.nooneschool.my;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.nooneschool.R;
import com.example.nooneschool.my.service.PublishCommentService;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PublishCommentActivity extends Activity implements View.OnClickListener {
	private ImageView iv_return;
	private ImageView iv_restaurant;
	private TextView tv_name;
	private TextView tv_feel;
	private TextView tv_font;
	private RatingBar rb_taste;
	private EditText et_content;
	private Button btn_confirm;
	private String userid;
	private String orderid;
	private int starsImgHeight;

	private ExecutorService singleThreadExeutor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_comment);
		init();
	}

	private void init() {
		iv_return = (ImageView) findViewById(R.id.pcomment_return_imageview);
		iv_restaurant = (ImageView) findViewById(R.id.pcomment_restaurant_imageview);
		tv_name = (TextView) findViewById(R.id.pcomment_name_textview);
		tv_feel = (TextView) findViewById(R.id.pcomment_feel_textview);
		tv_font = (TextView) findViewById(R.id.pcomment_fontcount_textview);
		rb_taste = (RatingBar) findViewById(R.id.pcomment_taste_ratingbar);
		et_content = (EditText) findViewById(R.id.pcomment_content_edittext);
		btn_confirm = (Button) findViewById(R.id.pcommetn_confirm_button);

		singleThreadExeutor = Executors.newSingleThreadExecutor();

		Intent intent = getIntent();
		userid = intent.getStringExtra("userid");
		orderid = intent.getStringExtra("orderid");
		String image = intent.getStringExtra("image");
		String name = intent.getStringExtra("name");

		DownImage downImage = new DownImage(image, iv_restaurant.getWidth(), iv_restaurant.getHeight());
		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {
				iv_restaurant.setImageDrawable(drawable);
			}
		});

		tv_name.setText(name);
		iv_return.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);

		et_content.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				tv_font.setText(String.valueOf(s.length()) + "/500");
				if (s.length() >= 500) {
					Toast.makeText(PublishCommentActivity.this, "上限了，亲", Toast.LENGTH_SHORT).show();
				}

			}
		});

		// 获取图片的高度
		try {
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_touchstared);
			starsImgHeight = bmp.getHeight();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 将获取的图片高度设置给RatingBar
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rb_taste.getLayoutParams();
		lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
		lp.height = starsImgHeight;
		rb_taste.setLayoutParams(lp);

		rb_taste.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean paramboolean) {
				// rating表示当前评分条中选中星星的个数
				ratingBar.setRating(rating);

				if (rating >= 1) {
					btn_confirm.setBackgroundColor(Color.parseColor("#00BBFF"));
				}

				int i = (int) rating;
				if (i >= 1) {
					tv_feel.setVisibility(View.VISIBLE);
				}
				switch (i) {
				case 1:
					tv_feel.setText("\"很差\"");
					break;
				case 2:
					tv_feel.setText("\"一般\"");
					break;
				case 3:
					tv_feel.setText("\"还行\"");
					break;
				case 4:
					tv_feel.setText("\"满意\"");
					break;

				case 5:
					tv_feel.setText("\"非常满意\"");
					break;

				default:
					break;
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pcomment_return_imageview:
			PublishCommentActivity.this.finish();
			break;

		case R.id.pcommetn_confirm_button:
			String con = et_content.getText().toString();
			final float values = rb_taste.getRating();

			if (con == null || con.length() <= 0) {
				con = "该用户未做出评价";
			}

			final String content = con;

			if (values < 1) {
				Toast.makeText(PublishCommentActivity.this, "请打分^_^", Toast.LENGTH_SHORT).show();
			} else {
				Runnable runnable = new Runnable() {
					public void run() {
						String rating = String.valueOf(values);
						final String result = PublishCommentService.PublishCommentByPost(userid, orderid, rating,
								content);
						if (result != null) {
							try {

								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(PublishCommentActivity.this, result, 0).show();
										PublishCommentActivity.this.finish();
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

			break;

		default:
			break;
		}
	}

}
