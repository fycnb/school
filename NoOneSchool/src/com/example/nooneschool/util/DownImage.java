package com.example.nooneschool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class DownImage {
	public static final String PATH = "/noone/img/";
	public String image_path;
 
	public DownImage(String image_path) {
		this.image_path = image_path;
	}
 
	public static String getFileName(String url) {
        int index = url.lastIndexOf("/") + 1;
        return url.substring(index);
    }
	
	public void loadImage(final ImageCallBack callBack) {
 
		final Handler handler = new Handler() {
 
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Drawable drawable = (Drawable) msg.obj;
				callBack.getDrawable(drawable);
			}
 
		};
 
		new Thread(new Runnable() {
 
			@Override
			public void run() {
				try {
			        if(image_path==null||image_path.equals("")){
			        	return;
			        }
			        Drawable drawable;
			        Bitmap loacalBitmap = ImageUtils.getLoacalBitmap(Environment.getExternalStorageDirectory() + ImageUtils.PATH + ImageUtils.getFileName(image_path));
			        if (loacalBitmap != null) {
			        	drawable = new BitmapDrawable(loacalBitmap);
			        } else {
			        	drawable = Drawable.createFromStream(new URL(
								image_path).openStream(), "");
			        	loacalBitmap=((BitmapDrawable)drawable).getBitmap();
			        }
					Message message = Message.obtain();
					message.obj = drawable;
					handler.sendMessage(message);
					saveImage(loacalBitmap, Environment.getExternalStorageDirectory() + PATH, getFileName(image_path));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public static Bitmap getLoacalBitmap(String url) {
        if (url != null) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(url);
                return BitmapFactory.decodeStream(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } finally {
                if(fis != null) {
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
 
	public interface ImageCallBack {
		public void getDrawable(Drawable drawable);
	}

}
