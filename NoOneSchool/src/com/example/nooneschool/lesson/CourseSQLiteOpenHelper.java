package com.example.nooneschool.lesson;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseSQLiteOpenHelper extends SQLiteOpenHelper {

	public CourseSQLiteOpenHelper(Context context) {
		super(context, "course.db", null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table course (courseid integer primary key autoincrement," + "course_name varchar(32),"
				+ "teacher varchar(16)," + "class_room varchar(16)," + "day integer," + "class_start integer," + "class_end integer)");
		
		db.execSQL("create table weekday (weekid integer primary key autoincrement," + "courseid integer,"
				 + "week integer)");
	}

	@Override 
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	
}
