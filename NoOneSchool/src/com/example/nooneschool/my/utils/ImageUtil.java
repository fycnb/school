package com.example.nooneschool.my.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Bitmap.Config;
import android.graphics.Shader.TileMode;

public class ImageUtil {

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
	
	
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        Bitmap bm = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Shader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawCircle(100, 100, 100, paint);
        return bm;
    }


}
