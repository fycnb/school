package com.example.nooneshop;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyApplication extends Application {
	
	public static String ip = "192.168.0.107";
	public static SharedPreferences sp;
	public static SharedPreferences.Editor editor;
	public void onCreate() {
        super.onCreate();
        sp = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
    	editor = sp.edit();
    }
	
}
