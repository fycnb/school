package com.example.nooneshop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.nooneshop.service.MyService;
import com.example.nooneshop.utils.DownImage;
import com.example.nooneshop.utils.DownImage.ImageCallBack;
import com.example.nooneshop.utils.MyToast;
import com.example.nooneshop.utils.UploadThread;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class AddGoodsActivity extends Activity {

	private TextView title;
	private ImageView back;
	private ImageView img;
	private EditText name;
	private EditText money;
	private Spinner classify;
	private Button add;
	private adapter ad;

	private List<String> list;
	private String id;
	private int classifynum = 0;
	private int FLAG = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_goods);

		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		title = (TextView) findViewById(R.id.addgoods_title_tv);
		back = (ImageView) findViewById(R.id.addgoods_back_iv);
		img = (ImageView) findViewById(R.id.addgoods_img_iv);
		name = (EditText) findViewById(R.id.addgoods_name_et);
		money = (EditText) findViewById(R.id.addgoods_money_et);
		classify = (Spinner) findViewById(R.id.addgoods_classify_spn);
		add = (Button) findViewById(R.id.addgoods_add_btn);

		back.setOnClickListener(new onAddGoodsClick());
		img.setOnClickListener(new onAddGoodsClick());
		add.setOnClickListener(new onAddGoodsClick());
		classify.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				classifynum = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		list = new ArrayList<>();
		list.add("请选择类别");
		list.add("盖饭");
		list.add("面食");
		list.add("小吃");
		list.add("其他");
		ad = new adapter();
		classify.setAdapter(ad);
		ad.setDatas(list);

		if (intent.getStringExtra("goodsid") != null) {
			classifynum = intent.getIntExtra("classify", 0);
			id = intent.getStringExtra("goodsid");
			DownImage downImage = new DownImage(intent.getStringExtra("img"), img.getWidth(), img.getHeight());
			downImage.loadImage(new ImageCallBack() {

				@Override
				public void getDrawable(Drawable drawable) {
					img.setImageDrawable(drawable);
				}
			});
			name.setText(intent.getStringExtra("name"));
			classify.setSelection(classifynum);
			money.setText(intent.getStringExtra("money"));
			add.setText("修改");
			title.setText("修改商品");
			FLAG = 1;
		}
	}

	public class onAddGoodsClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.addgoods_back_iv:
				setResult(0, getIntent());
				finish();
				break;
			case R.id.addgoods_img_iv:
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 2);
				break;
			case R.id.addgoods_add_btn:
				if (!name.getText().toString().equals("") && classifynum != 0 && !money.getText().toString().equals(""))
					if (FLAG == 0)
						addGoods();
					else
						editGoods();
				else
					MyToast.Toast(AddGoodsActivity.this, "请填完整!");
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
	public void addGoods() {
		new Thread(new Runnable() {
			public void run() {

				final String result = MyService.AddGoodsServiceByPost(id, name.getText().toString(), classifynum,
						money.getText().toString(), "NoOneShop/noone/shop/goods/add?");
				runOnUiThread(new Runnable() {
					public void run() {
						if (result == null) {
							MyToast.Toast(AddGoodsActivity.this, "请检查网络!");
						} else if (result.equals("no")) {
							MyToast.Toast(AddGoodsActivity.this, "添加失败,请重新添加!");
						} else {
							Bitmap bm =((BitmapDrawable)img.getDrawable()).getBitmap();  
							DownImage.saveImage(bm, Environment.getExternalStorageDirectory() + DownImage.PATH, "goods" + result + ".png");
							String url = "http://" + MyApplication.ip + ":8080/NoOneShop/Upload";
							File file = Environment.getExternalStorageDirectory();
							File fileAbs = new File(file, DownImage.PATH + "goods" + result + ".png");
							String FileName = fileAbs.getAbsolutePath();
							UploadThread thread = new UploadThread(url, FileName,"goods"+result);
							thread.start();
							setResult(1, getIntent());
							finish();
						}
					}
				});

			}
		}).start();
	}

	public void editGoods() {
		
		Bitmap bm =((BitmapDrawable)img.getDrawable()).getBitmap();  
		DownImage.saveImage(bm, Environment.getExternalStorageDirectory() + DownImage.PATH, "goods" + id + ".png");
		String url = "http://" + MyApplication.ip + ":8080/NoOneShop/Upload";
		File file = Environment.getExternalStorageDirectory();
		File fileAbs = new File(file, DownImage.PATH + "goods" + id + ".png");
		String FileName = fileAbs.getAbsolutePath();
		UploadThread thread = new UploadThread(url, FileName,"goods"+id);
		thread.start();
		new Thread(new Runnable() {
			public void run() {

				final String result = MyService.AddGoodsServiceByPost(id, name.getText().toString(), classifynum,
						money.getText().toString(), "NoOneShop/noone/shop/goods/edit?");
				runOnUiThread(new Runnable() {
					public void run() {
						if (result == null) {
							MyToast.Toast(AddGoodsActivity.this, "请检查网络!");
						} else if (result.equals("100")) {
							setResult(1, getIntent());
							finish();
						} else if (result.equals("200")) {
							MyToast.Toast(AddGoodsActivity.this, "修改失败,请重新修改!");
						}
					}
				});

			}
		}).start();
	}

	public class adapter extends BaseAdapter {

		List<String> datas = new ArrayList<>();

		public void setDatas(List<String> datas) {
			this.datas = datas;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHodler hodler = null;
			if (convertView == null) {
				hodler = new ViewHodler();
				convertView = LayoutInflater.from(AddGoodsActivity.this).inflate(R.layout.spinner_classify, null);
				hodler.mTextView = (TextView) convertView.findViewById(R.id.spinner_classify);
				convertView.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
			}

			hodler.mTextView.setText(datas.get(position));

			return convertView;
		}

		private class ViewHodler {
			TextView mTextView;
		}

	}
}
