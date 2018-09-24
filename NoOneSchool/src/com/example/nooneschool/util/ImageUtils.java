package com.example.nooneschool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class ImageUtils {

	public static final String PATH = "/noone/img/";
	private static ImageView showImageView;
	private static String mUrl;

	private static ExecutorService SINGLE_TASK_EXECUTOR;
	private static ExecutorService LIMITED_TASK_EXECUTOR;
	private static ExecutorService FULL_TASK_EXECUTOR;

	static {
		SINGLE_TASK_EXECUTOR = (ExecutorService) Executors.newSingleThreadExecutor();
		LIMITED_TASK_EXECUTOR = (ExecutorService) Executors.newFixedThreadPool(7);
		FULL_TASK_EXECUTOR = (ExecutorService) Executors.newCachedThreadPool();
	};

	public static String getFileName(String url) {
		int index = url.lastIndexOf("/") + 1;
		return url.substring(index);
	}

	public static void setImageBitmap(String url, ImageView iv) {
		showImageView = iv;
		mUrl = url;
		if (url == null) {
			return;
		}
		Bitmap loacalBitmap = ImageUtils.getLoacalBitmap(
				Environment.getExternalStorageDirectory() + ImageUtils.PATH + ImageUtils.getFileName(url));
		if (loacalBitmap != null) {
			showImageView.setImageBitmap(loacalBitmap);
		} else {
			new DownImgAsyncTask().executeOnExecutor(FULL_TASK_EXECUTOR, url);
		}
	}

	/**
	 * * 通过URL地址获取Bitmap对象 * @Title: getBitMapByUrl * @param @param url
	 * * @param @return * @param @throws Exception * @return Bitmap * @throws
	 */
	public static Bitmap getBitmapByUrl(final String url) {
		URL fileUrl = null;
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			fileUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			is = null;
		}
		return bitmap;
	}

	// 保存图片到本地路径
	public static File saveImage(Bitmap bmp, String path, String fileName) {
		File appDir = new File(path);
		if (!appDir.exists()) {
			appDir.mkdirs();
		}
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * * @Title: getLoacalBitmap * @Description: 加载本地图片 * @param @param url 本地路径
	 * * @param @return * @return Bitmap * @throws
	 */
	public static Bitmap getLoacalBitmap(String url) {
		if (url != null) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(url);
				return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					fis = null;
				}
			}
		} else {
			return null;
		}
	}
	


	
	static class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap b = getBitmapByUrl(params[0]);
			return b;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result != null) {
				File file = saveImage(result, Environment.getExternalStorageDirectory() + PATH, getFileName(mUrl));

				showImageView.setImageBitmap(result);
			}
		}
	}

}
