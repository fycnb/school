package com.example.nooneschool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.example.nooneschool.my.CollectionActivity;
import com.example.nooneschool.my.CustomerServiceActivity;
import com.example.nooneschool.my.MyOrderActivity;
import com.example.nooneschool.my.PersonalDataActivity;
import com.example.nooneschool.my.RecentlyBrowseActivity;
import com.example.nooneschool.my.SignInActivity;
import com.example.nooneschool.my.service.UserDataService;
import com.example.nooneschool.my.utils.ImageUtil;
import com.example.nooneschool.my.utils.UploadThread;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity implements View.OnClickListener {
	private TextView tv_nickname;
	private TextView tv_account;
	private TextView tv_sobo;

	private Button btn_signin;
	private Button btn_myorder;
	private Button btn_waitorder;
	private Button btn_acceptorder;
	private Button btn_waitcomment;
	
	private ImageView iv_headportrait;
	private RelativeLayout rl_person;

	private ThreadPoolExecutor poolExecutor;

	private GridView gv_function;
	private List<Map<String, Object>> functionList;
	private SimpleAdapter adapter;

	private String path = Environment.getExternalStorageDirectory() + "/temp";
	private Uri imageUri = Uri.parse(path + "/head.png");

	private static final int CAMERA_CODE = 1;
	private static final int GALLERY_CODE = 2;
	private static final int CROP_CODE = 3;

	private String userid = "1";
	private String account;
	private String nickname;
	private String sobo;
	private String head;

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
		btn_waitorder = (Button) findViewById(R.id.my_waitingorder_button);
		btn_acceptorder = (Button) findViewById(R.id.my_acceptorder_button);
		btn_waitcomment = (Button) findViewById(R.id.my_waitingcomment_button);

		gv_function = (GridView) findViewById(R.id.my_function_gridview);
		rl_person = (RelativeLayout) findViewById(R.id.my_person_relativelayout);

		poolExecutor = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128));

		// 向function_gridview中插入数据
		functionData();

		// 头像
		String imagepath = path + "/head.png";
		Bitmap bm = ImageUtil.getLoacalBitmap(imagepath);
		if (bm != null) {
			iv_headportrait.setImageBitmap(ImageUtil.toRoundBitmap(bm));
		} else {
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			iv_headportrait.setImageBitmap(ImageUtil.toRoundBitmap(bmp));
		}

		// 获取数据
		getUserData();

		// 点击事件
		btn_signin.setOnClickListener(this);
		btn_myorder.setOnClickListener(this);
		btn_waitorder.setOnClickListener(this);
		btn_acceptorder.setOnClickListener(this);
		btn_waitcomment.setOnClickListener(this);
		iv_headportrait.setOnClickListener(this);
		rl_person.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.my_signin_button:
			intent = new Intent(MyActivity.this, SignInActivity.class);
			intent.putExtra("userid", userid);
			startActivity(intent);
			break;
		case R.id.my_myorder_button:
			intent = new Intent(MyActivity.this, MyOrderActivity.class);
			intent.putExtra("state", "all");
			startActivity(intent);
			break;
		case R.id.my_waitingorder_button:
			intent = new Intent(MyActivity.this, MyOrderActivity.class);
			intent.putExtra("state", "0");
			startActivity(intent);
			break;
		case R.id.my_acceptorder_button:
			intent = new Intent(MyActivity.this, MyOrderActivity.class);
			intent.putExtra("state", "1");
			startActivity(intent);
			break;
		case R.id.my_waitingcomment_button:
			intent = new Intent(MyActivity.this, MyOrderActivity.class);
			intent.putExtra("state", "4");
			startActivity(intent);
			break;
			
		case R.id.my_person_relativelayout:
			intent = new Intent(MyActivity.this, PersonalDataActivity.class);
			intent.putExtra("userid", userid);
			startActivity(intent);
			break;

		case R.id.my_headportrait_imageview:
			showTypeDialog();
			break;
			
		default:
			break;
		}

	}

	private void getUserData() {
		Runnable runnable = new Runnable() {
			public void run() {
				final String result = UserDataService.UserDataByPost(userid);
				if (result != null) {
					try {
						JSONObject js = new JSONObject(result);
						account = js.getString("account");
						nickname = js.getString("nickname");
						sobo = js.getString("sobo");
						head = js.getString("head");
					} catch (Exception e) {
						e.printStackTrace();
					}
					runOnUiThread(new Runnable() {
						public void run() {
							DownImage downImage = new DownImage(head, iv_headportrait.getWidth(),
									iv_headportrait.getHeight());
							downImage.loadImage(new ImageCallBack() {
								@Override
								public void getDrawable(Drawable drawable) {
									BitmapDrawable bd = (BitmapDrawable) drawable;
									Bitmap bm = bd.getBitmap();
									DownImage.saveImage(bm, path, "head.png");
									iv_headportrait.setImageBitmap(ImageUtil.toRoundBitmap(bm));
								}
							});

							tv_nickname.setText(nickname);
							tv_account.setText(account);
							tv_sobo.setText(sobo);
						}
					});
				} else {
					// 没有返回数据

				}
			}
		};
		poolExecutor.execute(runnable);
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
					File file = DownImage.saveImage(bm, path, "head.png");
					upload();
					Uri uri = Uri.fromFile(file);
					startImageZoom(uri);

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
				upload();
				startImageZoom(uri);
			}
			break;
		case CROP_CODE:
			Bitmap bm = decodeUriAsBitmap(imageUri);
			if (bm != null) {
				iv_headportrait.setImageBitmap(bm);
			}

			break;
		default:
			break;

		}
	}

	private void upload() {
		String url = "http://169.254.96.11:8080/NoOneSchoolService/Upload";
		File file = Environment.getExternalStorageDirectory();
		File fileAbs = new File(file, "/temp/head.png");
		String FileName = fileAbs.getAbsolutePath();
		UploadThread thread = new UploadThread(url, FileName);
		thread.start();
	}

	private Uri convertUri(Uri uri) {
		InputStream is;
		try {
			is = getContentResolver().openInputStream(uri);
			Bitmap bm = BitmapFactory.decodeStream(is);
			File file = DownImage.saveImage(bm, path, "head.png");
			Uri url = Uri.fromFile(file);
			is.close();
			return url;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void startImageZoom(Uri uri) {
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
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
		intent.putExtra("noFaceDetection", false);
		startActivityForResult(intent, CROP_CODE);

		// return imageUri;
	}

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

	private void functionData() {
		int icno[] = { R.drawable.person, R.drawable.customer };
		String name[] = {  "个人资料", "联系客服" };

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
			Intent intent;
			String name = functionList.get(position).get("name").toString();
			switch (name) {
//			case "收藏":
//				intent = new Intent(MyActivity.this, CollectionActivity.class);
//				startActivity(intent);
//				break;
//			case "最近浏览":
//				intent = new Intent(MyActivity.this, RecentlyBrowseActivity.class);
//				startActivity(intent);
//				break;
			case "联系客服":
				String iphone = "13920147107";
				intent = new Intent(Intent.ACTION_CALL);
				Uri data = Uri.parse("tel:" + iphone);
				intent.setData(data);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
//				intent = new Intent(MyActivity.this, CustomerServiceActivity.class);
//				startActivity(intent);
				break;
			case "个人资料":
				intent = new Intent(MyActivity.this, PersonalDataActivity.class);
				intent.putExtra("userid", userid);
				startActivity(intent);
				break;
			default:
				Log.i("cjq", "function gridview error");
				break;
			}

		}

	}

}
