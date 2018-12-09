package com.example.nooneshop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneshop.adapter.RankAdapter;
import com.example.nooneshop.list.RankList;
import com.example.nooneshop.service.MyService;
import com.example.nooneshop.utils.DownImage;
import com.example.nooneshop.utils.MyToast;
import com.example.nooneshop.utils.UploadThread;
import com.example.nooneshop.utils.DownImage.ImageCallBack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ShopDetailActivity extends Activity {

	private ImageView back;
	private ImageView edit;
	private ImageView cancel;
	private ImageView submit;
	private ImageView img;
	private EditText name;
	private TextView refresh;
	private TextView state;
	private EditText address;
	private TextView number;
	private LinearLayout show;
	private ListView listview;
	private String resetname;
	private String resetadd;
	private Drawable resetimg;
	private String id;

	private List<RankList> list;
	private RankAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_detail);

		id = getIntent().getStringExtra("id");
		back = (ImageView) findViewById(R.id.shop_back_iv);
		edit = (ImageView) findViewById(R.id.shop_edit_iv);
		cancel = (ImageView) findViewById(R.id.shop_cancel_iv);
		submit = (ImageView) findViewById(R.id.shop_submit_iv);
		img = (ImageView) findViewById(R.id.shop_img_iv);
		name = (EditText) findViewById(R.id.shop_name_et);
		refresh = (TextView) findViewById(R.id.shop_refresh_tv);
		state = (TextView) findViewById(R.id.shop_state_tv);
		address = (EditText) findViewById(R.id.shop_address_et);
		number = (TextView) findViewById(R.id.shop_number_tv);
		show = (LinearLayout) findViewById(R.id.shop_show_ll);
		listview = (ListView) findViewById(R.id.shop_list_lv);

		back.setOnClickListener(new onShopClick());
		edit.setOnClickListener(new onShopClick());
		cancel.setOnClickListener(new onShopClick());
		submit.setOnClickListener(new onShopClick());
		img.setOnClickListener(new onShopClick());
		refresh.setOnClickListener(new onShopClick());

		img.setClickable(false);
		address.setFocusable(false);
		address.setFocusableInTouchMode(false);
		name.setFocusable(false);
		name.setFocusableInTouchMode(false);

		show.setVisibility(8);
		refresh.setVisibility(0);

		getShopDetail();
	}

	public class onShopClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.shop_back_iv:
				finish();
				break;
			case R.id.shop_edit_iv:
				Edit();
				break;
			case R.id.shop_cancel_iv:
				cancelEdit();
				break;
			case R.id.shop_submit_iv:
				if (!name.getText().toString().equals(resetname) || !address.getText().toString().equals(resetadd)
						|| img.getDrawable() != resetimg)
					editShopDetail();
				else
					cancelEdit();
				break;
			case R.id.shop_img_iv:
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 2);
				break;
			case R.id.shop_refresh_tv:
				getShopDetail();
				break;
			}
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 第一层switch
		switch (requestCode) {
		case 2:
			// 第二层switch
			switch (resultCode) {
			case RESULT_OK:
				if (data != null) {
					Uri uri = data.getData();
					img.setImageURI(uri);
				}
				break;
			case RESULT_CANCELED:
				break;
			}
			break;

		}
	}

	public void getShopDetail() {
		new Thread(new Runnable() {
			public void run() {
				final String result = MyService.ShopDetailServiceByPost(id, "NoOneShop/noone/shop/detail/get?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								list = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								JSONObject j = (JSONObject) ja.get(0);

								name.setText(j.getString("name"));
								address.setText(j.getString("address"));
								state.setText(j.getInt("state") == 0 ? "闭店中" : "营业中");
								DownImage downImage = new DownImage(j.getString("img"), img.getWidth(),
										img.getHeight());
								downImage.loadImage(new ImageCallBack() {

									@Override
									public void getDrawable(Drawable drawable) {
										img.setImageDrawable(drawable);
									}
								});
								String detail = j.getString("detail");

								JSONArray ja2 = new JSONArray(detail);
								for (int n = 0; n < ja2.length(); n++) {
									JSONObject m = (JSONObject) ja2.get(n);
									String img = m.getString("img");
									String name = m.getString("name");
									int number = m.getInt("number");

									list.add(new RankList(number, name, img));
								}
								number.setText("目前热销商品有" + ja2.length() + "件:");
								adapter = new RankAdapter(ShopDetailActivity.this, list);
								listview.setAdapter(adapter);

								edit.setVisibility(0);
								show.setVisibility(0);
								refresh.setVisibility(8);

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							edit.setVisibility(8);
							show.setVisibility(8);
							refresh.setVisibility(0);
						}
					}
				});
			}
		}).start();
	}

	public void editShopDetail() {
		if (img.getDrawable() != resetimg) {
			Bitmap bm =((BitmapDrawable)img.getDrawable()).getBitmap();  
			DownImage.saveImage(bm, Environment.getExternalStorageDirectory() + DownImage.PATH, "shop" + id + ".png");
			String url = "http://" + MyApplication.ip + ":8080/NoOneShop/Upload";
			File file = Environment.getExternalStorageDirectory();
			File fileAbs = new File(file, DownImage.PATH + "shop" + id + ".png");
			String FileName = fileAbs.getAbsolutePath();
			UploadThread thread = new UploadThread(url, FileName,"shop"+id);
			thread.start();
		}
		if (!name.getText().toString().equals(resetname) || !address.getText().toString().equals(resetadd))
			new Thread(new Runnable() {
				public void run() {
					final String result = MyService.EditShopDetailServiceByPost(id, name.getText().toString(),
							address.getText().toString(), "NoOneShop/noone/shop/detail/edit?");

					runOnUiThread(new Runnable() {
						public void run() {

							if (result == null) {
								MyToast.Toast(ShopDetailActivity.this, "请检查网络!");
							} else if (result.equals("100")) {
								back.setVisibility(0);
								edit.setVisibility(0);
								cancel.setVisibility(8);
								submit.setVisibility(8);
								img.setClickable(false);
								address.setFocusable(false);
								address.setFocusableInTouchMode(false);
								name.setFocusable(false);
								name.setFocusableInTouchMode(false);
								resetname = name.getText().toString();
								resetadd = address.getText().toString();
								resetimg = img.getDrawable();
							} else if (result.equals("200")) {
								MyToast.Toast(ShopDetailActivity.this, "更新失败!");
							}
						}
					});
				}
			}).start();
		else{
			back.setVisibility(0);
			edit.setVisibility(0);
			cancel.setVisibility(8);
			submit.setVisibility(8);
			img.setClickable(false);
			address.setFocusable(false);
			address.setFocusableInTouchMode(false);
			name.setFocusable(false);
			name.setFocusableInTouchMode(false);
			resetname = name.getText().toString();
			resetadd = address.getText().toString();
			resetimg = img.getDrawable();
		}
		setResult(1, getIntent());
			
	}

	public void cancelEdit() {
		back.setVisibility(0);
		edit.setVisibility(0);
		cancel.setVisibility(8);
		submit.setVisibility(8);
		img.setClickable(false);
		address.setFocusable(false);
		address.setFocusableInTouchMode(false);
		name.setFocusable(false);
		name.setFocusableInTouchMode(false);
		name.setText(resetname);
		address.setText(resetadd);
		img.setImageDrawable(resetimg);
	}

	public void Edit() {
		back.setVisibility(8);
		edit.setVisibility(8);
		cancel.setVisibility(0);
		submit.setVisibility(0);
		img.setClickable(true);
		address.setFocusable(true);
		address.setFocusableInTouchMode(true);
		name.setFocusable(true);
		name.setFocusableInTouchMode(true);
		name.requestFocus();
		resetname = name.getText().toString();
		resetadd = address.getText().toString();
		resetimg = img.getDrawable();
	}
}
