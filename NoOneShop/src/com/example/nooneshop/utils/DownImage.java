package com.example.nooneshop.utils;

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
	public static final String PATH = "/noshop/img/";
	public String image_path;
	public int piexlW;
	public  int piexlH;
 
	public DownImage(String image_path,int width,int height) {
		this.image_path = image_path;
		this.piexlW = width;
		this.piexlH = height;
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
			        Bitmap loacalBitmap = getLoacalBitmap(Environment.getExternalStorageDirectory() + PATH + getFileName(image_path),piexlW, piexlH);

			        if (loacalBitmap != null) {
			        	drawable = new BitmapDrawable(loacalBitmap);
			        } else {
			        	drawable = Drawable.createFromStream(new URL(
								image_path).openStream(), "");
			        	loacalBitmap=((BitmapDrawable)drawable).getBitmap();
			        	saveImage(loacalBitmap, Environment.getExternalStorageDirectory() + PATH, getFileName(image_path));
			        }
					Message message = Message.obtain();
					message.obj = drawable;
					handler.sendMessage(message);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public static Bitmap getLoacalBitmap(String url,int piexlW, int piexlH) {
        if (url != null) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(url);
                BitmapFactory.Options newOption = new BitmapFactory.Options();
        		newOption.inJustDecodeBounds = true;
        		newOption.inPreferredConfig = Bitmap.Config.RGB_565;

                BitmapFactory.decodeFileDescriptor(fis.getFD(), null, newOption);
        		int originalW = newOption.outWidth;
        		int originalH = newOption.outHeight;

        		newOption.inSampleSize = getSimpleSize(originalW, originalH, piexlW, piexlH);
        		newOption.inJustDecodeBounds = false;
                return BitmapFactory.decodeFileDescriptor(fis.getFD(), null, newOption);
            } catch (Exception e) {
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
	
	private static int getSimpleSize(int originalW, int originalH, int piexlW, int piexlH) {
		int simpleSize = 1;
		if (originalW > originalH && originalW > piexlW) {
			simpleSize = originalW / piexlW;
		} else if (originalH > originalW && originalH > piexlH) {
			simpleSize = originalH / piexlH;
		}

		if (simpleSize <= 0) {
			simpleSize = 1;
		}
		return 1;

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
