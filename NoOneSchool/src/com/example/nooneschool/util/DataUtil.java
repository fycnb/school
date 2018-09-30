package com.example.nooneschool.util;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.home.list.ListAd;
import com.example.nooneschool.home.list.ListClass;
import com.example.nooneschool.home.list.ListFood;
import com.example.nooneschool.home.list.ListMeal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataUtil {

	private static SQLite helper;

	public static List<ListAd> getBannerData(Context context) {
		helper = new SQLite(context);

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select imgid,imgurl from image", null);

		List<ListAd> list = new ArrayList<ListAd>();
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);
			String url = cursor.getString(1);
			list.add(new ListAd(id, url));
		}
		cursor.close();
		db.close();

		while (list.size() < 4) {
			list.add(new ListAd(null, ""));
		}
		return list;
	}

	public static List<ListClass> getFoodData() {
		List<ListClass> list = new ArrayList<ListClass>();

		list.add(new ListClass("盖饭", "rice"));
		list.add(new ListClass("面食", "noodle"));
		list.add(new ListClass("小吃", "snack"));
		list.add(new ListClass("跑腿", "send"));
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
	
	
	public static List<ListFood> getNoDataFood(int height) {
		List<ListFood> list = new ArrayList<ListFood>();
		ListFood meal = new ListFood(null, null, null, null, null, null, null);
		meal.setIsNoData(true);
		meal.setmHeight(height);
		list.add(meal);
		return list;
	}
	
	public static List<String> getLeftMenuData() {
		List<String> list = new ArrayList<>();

		list.add("折扣");
		list.add("主食");
		list.add("套餐");
		list.add("小食");
		list.add("饮品");
		return list;
	}
}
