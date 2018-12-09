package com.example.nooneshop.utils;

import android.content.Context;
import android.widget.Toast;

public class MyToast {
	public static void Toast(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}
}
