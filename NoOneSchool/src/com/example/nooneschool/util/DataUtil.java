package com.example.nooneschool.util;

import java.util.ArrayList;
import java.util.List;
import com.example.nooneschool.home.ListAd;
import com.example.nooneschool.home.ListFood;
import com.example.nooneschool.home.ListMeal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataUtil {

	private static SQLite helper;
	
	public static List<ListAd> getBannerData(Context context) {
		helper = new SQLite(context);
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select imgid,imgurl from image",null);

		List<ListAd> list = new ArrayList<ListAd>();
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);
			String url = cursor.getString(1);
			list.add(new ListAd(id,url));	
		}
		cursor.close();
		db.close();

		while(list.size()<4){
			list.add(new ListAd(null,""));
		}
		return list;
	}

	public static List<ListFood> getFoodData() {
		List<ListFood> list = new ArrayList<ListFood>();
		
		list.add(new ListFood("盖饭","rice"));
		list.add(new ListFood("面食","noodle"));
		list.add(new ListFood("小吃","snack"));
		list.add(new ListFood("跑腿","send"));
		return list;
	}

	 public static List<ListMeal> getNoDataMeal(int height) {
	        List<ListMeal> list = new ArrayList<ListMeal>();
	        ListMeal meal = new ListMeal(null, null, null, null, null, null, null);
	        meal.setIsNoData(true);
	        meal.setmHeight(height);
	        list.add(meal);
	        return list;
	    }
}
