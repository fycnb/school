package com.example.nooneschool.util;

import java.util.ArrayList;
import java.util.List;
import com.example.nooneschool.home.ListAd;
import com.example.nooneschool.home.ListFood;
import com.example.nooneschool.home.ListMeal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataUtil {

	private static SQLite helper;
	
	public static List<ListAd> getBannerData(Context context) {
		helper = new SQLite(context);
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select imgid, imgblob,imgname,imgurl from image",null);

		List<ListAd> list = new ArrayList<ListAd>();
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);
			byte[] bit = cursor.getBlob(1);
			String name = cursor.getString(2);
			String url = cursor.getString(3);
			list.add(new ListAd(id, name, bit,url));	
		}
		cursor.close();
		db.close();
		
		while(list.size()<4){
			list.add(new ListAd(null, "", null,""));
		}
		return list;
	}

	public static List<ListFood> getFoodData() {
		List<ListFood> list = new ArrayList<ListFood>();
		list.add(new ListFood("推荐","0"));
		list.add(new ListFood("盖饭","1"));
		list.add(new ListFood("面食","2"));
		list.add(new ListFood("米粥","3"));
		list.add(new ListFood("套餐","4"));
		list.add(new ListFood("小吃","5"));
		list.add(new ListFood("烧烤","6"));
		list.add(new ListFood("其他","7"));
		return list;
	}

	 public static List<ListMeal> getNoDataMeal(int height) {
	        List<ListMeal> list = new ArrayList<ListMeal>();
	        ListMeal meal = new ListMeal();
	        meal.setIsNoData(true);
	        meal.setmHeight(height);
	        list.add(meal);
	        return list;
	    }
}
