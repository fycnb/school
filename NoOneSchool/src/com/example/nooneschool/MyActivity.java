package com.example.nooneschool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.example.nooneschool.my.CollectionActivity;
import com.example.nooneschool.my.CustomerServiceActivity;
import com.example.nooneschool.my.MyOrderActivity;
import com.example.nooneschool.my.PersonalDataActivity;
import com.example.nooneschool.my.RecentlyBrowseActivity;
import com.example.nooneschool.my.SignInActivity;
import com.example.nooneschool.my.service.UserDataService;
import com.example.nooneschool.my.utils.ImageUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity implements View.OnClickListener {
	private TextView tv_nickname;
	private TextView tv_account;
	private TextView tv_sobo;
	private ImageView iv_headportrait;
	private Button btn_signin;
	private Button btn_myorder;

	private GridView gv_function;
	private List<Map<String, Object>> functionList;
	private SimpleAdapter adapter;

	private Uri imageUri = Uri.parse("file:///sdcard/temp/img.jpg");

	private static final int CAMERA_CODE = 1;
	private static final int GALLERY_CODE = 2;
	private static final int CROP_CODE = 3;

	private String userid = "1";
	private String account;
	private String nickname;
	private String sobo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		init();

	}

	@Override
	protected void onResume() {
		super.onResume();
		init();
	}

	private void init() {

		tv_nickname = (TextView) findViewById(R.id.my_nickname_textview);
		tv_account = (TextView) findViewById(R.id.my_account_textview);
		tv_sobo = (TextView) findViewById(R.id.my_sobo_textview);
		iv_headportrait = (ImageView) findViewById(R.id.my_headportrait_imageview);
		btn_signin = (Button) findViewById(R.id.my_signin_button);
		btn_myorder = (Button) findViewById(R.id.my_myorder_button);
		gv_function = (GridView) findViewById(R.id.my_function_gridview);

		// 向function_gridview中插入数据
		functiondata();

		// 获取数据
		getuserdata();

		String path = Environment.getExternalStorageDirectory() + "/temp/img.jpg";
		Bitmap bm = ImageUtil.getLoacalBitmap(path);
		if (bm != null) {
			iv_headportrait.setImageBitmap(ImageUtil.toRoundBitmap(bm));
		} else {
			bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			iv_headportrait.setImageBitmap(ImageUtil.toRoundBitmap(bm));
		}

		// 点击事件
		btn_signin.setOnClickListener(this);
		btn_myorder.setOnClickListener(this);
		iv_headportrait.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_signin_button:
			Intent intent = new Intent(MyActivity.this, SignInActivity.class);
			startActivity(intent);
			break;
		case R.id.my_myorder_button:
			Intent intent1 = new Intent(MyActivity.this, MyOrderActivity.class);
			startActivity(intent1);
			break;

		case R.id.my_headportrait_imageview:
			showTypeDialog();
			break;
		default:
			break;
		}

	}

	private void getuserdata() {
		new Thread() {
			public void run() {
				final String result = UserDataService.UserDataByPost(userid);
				if (result != null) {
					try {
						JSONObject js = new JSONObject(result);
						account = js.getString("account");
						nickname = js.getString("nickname");
						sobo = js.getString("sobo");
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					runOnUiThread(new Runnable() {
						public void run() {
							tv_nickname.setText(nickname);
							tv_account.setText(account);
							tv_sobo.setText(sobo);
						}
					});
				} else {

				}
			}
		}.start();
	}

	private void showTypeDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		View view = View.inflate(this, R.layout.dialog_select_photo, null);
		TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
		TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
		tv_select_gallery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				chooseFromGallery();
				dialog.dismiss();
			}
		});
		tv_select_camera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				chooseFromCamera();
				dialog.dismiss();
			}
		});
		dialog.setView(view);
		dialog.show();
	}

	private void chooseFromCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, CAMERA_CODE);
	}

	private void chooseFromGallery() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, GALLERY_CODE);
		} else {
			Toast.makeText(MyActivity.this, "SD不存在", 0).show();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CAMERA_CODE:
			if (data == null) {
				return;
			} else {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap bm = extras.getParcelable("data");
					Uri uri = saveBitmap(bm, "temp");
					imageUri = startImageZoom(uri);

				}
			}
			break;
		case GALLERY_CODE:
			if (data == null) {

				return;
			} else {
				Uri uri;
				uri = data.getData();
				uri = convertUri(uri);
				imageUri = startImageZoom(uri);
			}
			break;
		case CROP_CODE:

			if (imageUri != null) {
				Bitmap bm = decodeUriAsBitmap(imageUri);
				if (bm != null) {
					iv_headportrait.setImageBitmap(bm);
				}

			}
			break;
		default:
			break;

		}
	}


	private Uri convertUri(Uri uri) {
		InputStream is;
		try {
			is = getContentResolver().openInputStream(uri);
			Bitmap bm = BitmapFactory.decodeStream(is);
			is.close();
			return saveBitmap(bm, "temp");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Uri saveBitmap(Bitmap bm, String dirPath) {
		File tmpDir = new File(Environment.getExternalStorageDirectory() + "/" + dirPath);
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		String filename = "img.jpeg";
		String imgpath = tmpDir.getAbsolutePath() + "/" + filename;
		File img = new File(imgpath);

		try {
			FileOutputStream fos = new FileOutputStream(img);
			bm.compress(Bitmap.CompressFormat.JPEG, 85, fos);
			fos.flush();
			fos.close();
			return Uri.fromFile(img);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private Uri startImageZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false);
		startActivityForResult(intent, CROP_CODE);

		return imageUri;
	}
	//

	public Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	private void functiondata() {
		int icno[] = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, };
		String name[] = { "收藏", "最近浏览", "客服", "个人资料" };

		functionList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < icno.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("icon", icno[i]);
			map.put("name", name[i]);
			functionList.add(map);
		}

		String[] str = { "icon", "name" };
		int[] i = { R.id.function_icon_imageview, R.id.function_name_textview };

		adapter = new SimpleAdapter(this, functionList, R.layout.item_my_function_girdview, str, i);
		gv_function.setAdapter(adapter);
		gv_function.setOnItemClickListener(new functiongirdview());
	}

	public class functiongirdview implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String name = functionList.get(position).get("name").toString();
			switch (name) {
			case "收藏":
				Intent intent1 = new Intent(MyActivity.this, CollectionActivity.class);
				startActivity(intent1);
				break;
			case "最近浏览":
				Intent intent2 = new Intent(MyActivity.this, RecentlyBrowseActivity.class);
				startActivity(intent2);
				break;
			case "客服":
				Intent intent3 = new Intent(MyActivity.this, CustomerServiceActivity.class);
				startActivity(intent3);
				break;
			case "个人资料":
				Intent intent4 = new Intent(MyActivity.this, PersonalDataActivity.class);
				startActivity(intent4);
				break;
			default:
				Log.i("cjq", "function gridview error");
				break;
			}

		}

	}

}
