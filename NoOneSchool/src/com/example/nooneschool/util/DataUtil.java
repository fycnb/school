package com.example.nooneschool.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataUtil {

	public static List<String> getLeftMenuData() {
		List<String> list = new ArrayList<>();

		list.add("热门");
		list.add("盖饭");
		list.add("面食");
		list.add("小吃");
		list.add("其他");
		return list;
	}
}
