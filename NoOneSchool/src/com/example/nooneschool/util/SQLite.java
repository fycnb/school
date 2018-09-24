package com.example.nooneschool.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper{

	public SQLite(Context context) {
		super(context, "noonesql.db", null, 5);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table image ( imgid integer primary key, imgurl varchar(100))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
	}

}
