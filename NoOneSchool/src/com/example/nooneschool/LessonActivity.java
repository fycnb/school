package com.example.nooneschool;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.lesson.AddCourseActivity;
import com.example.nooneschool.lesson.Course;
import com.example.nooneschool.lesson.CourseDetailActivity;
import com.example.nooneschool.lesson.CourseSQLiteOpenHelper;
import com.example.nooneschool.util.DensityUtil;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LessonActivity extends Activity implements View.OnClickListener {
	private Spinner sp_week;
	private List<String> weekList;
	private ArrayAdapter<String> weekAdapter;
	private RelativeLayout day;// 星期几
	private ImageView iv_add;
	private CourseSQLiteOpenHelper helper;
	private SQLiteDatabase db;

	private int currentCoursesNumber = 0;
	private int maxCoursesNumber = 0;
	private int currweek = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		init();
	}

	private void init() {
		helper = new CourseSQLiteOpenHelper(this);
		iv_add = (ImageView) findViewById(R.id.course_add_imageview);
		sp_week = (Spinner) findViewById(R.id.lesson_week_spinner);
		iv_add.setOnClickListener(this);

		
		loadData();
		weekSipnner();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.course_add_imageview:
			popupFunction();
			break;
		default:
			break;
		}
	}

	private void weekSipnner() {
		weekList = new ArrayList<String>();
		weekList.add("周一");
		weekList.add("周二");
		weekList.add("周三");
		weekList.add("周四");
		weekList.add("周五");
		weekList.add("周六");
		weekList.add("周七");
		weekAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weekList);
		weekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_week.setAdapter(weekAdapter);
		sp_week.setSelection(0, true);
		sp_week.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				currweek = position + 1;
				if(day != null){
					day.removeAllViews();
				}
				
				loadData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
	}

	// 从数据库加载数据
	private void loadData() {
		ArrayList<Course> coursesList = new ArrayList<>();
		db = helper.getWritableDatabase();
		String week = String.valueOf(currweek);
		Cursor cursor = db.rawQuery("select courseid from weekday where week = ?", new String[] { week });
		if (cursor.moveToFirst()) {
			do {
				String courseid = String.valueOf(cursor.getInt(cursor.getColumnIndex("courseid")));
				Cursor cr = db.rawQuery("select * from course where courseid = ?", new String[] { courseid });
				if (cr.moveToNext()) {
					coursesList.add(new Course(cr.getInt(cr.getColumnIndex("courseid")),
							cr.getString(cr.getColumnIndex("course_name")), cr.getString(cr.getColumnIndex("teacher")),
							cr.getString(cr.getColumnIndex("class_room")), cr.getInt(cr.getColumnIndex("day")),
							cr.getInt(cr.getColumnIndex("class_start")), cr.getInt(cr.getColumnIndex("class_end"))));
				}
				cr.close();
			} while (cursor.moveToNext());
		}

		cursor.close();
		for (Course course : coursesList) {
			createLeftView(course);
			createCourseView(course);
		}
	}

	// 创建课程节数视图
	private void createLeftView(Course course) {
		int len = course.getClass_end();
		if (len > maxCoursesNumber) {
			for (int i = 0; i < len - maxCoursesNumber; i++) {
				View view = LayoutInflater.from(this).inflate(R.layout.left_view, null);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.getWindowWidth(this) / 8,
						100);
				view.setLayoutParams(params);

				TextView text = (TextView) view.findViewById(R.id.class_number_text);
				text.setText(String.valueOf(++currentCoursesNumber));

				LinearLayout leftViewLayout = (LinearLayout) findViewById(R.id.left_view_layout);
				leftViewLayout.addView(view);
			}
		}
		maxCoursesNumber = len;
	}

	// 创建课程视图
	private void createCourseView(final Course course) {
		int height = 100;
		int getDay = course.getDay();
		if ((getDay < 1 || getDay > 7) || course.getClass_start() > course.getClass_end())
			Toast.makeText(this, "星期几没写对,或课程结束时间比开始时间还早~~", 0).show();
		else {
			switch (getDay) {
			case 1:
				day = (RelativeLayout) findViewById(R.id.monday);
				break;
			case 2:
				day = (RelativeLayout) findViewById(R.id.tuesday);
				break;
			case 3:
				day = (RelativeLayout) findViewById(R.id.wednesday);
				break;
			case 4:
				day = (RelativeLayout) findViewById(R.id.thursday);
				break;
			case 5:
				day = (RelativeLayout) findViewById(R.id.friday);
				break;
			case 6:
				day = (RelativeLayout) findViewById(R.id.saturday);
				break;
			case 7:
				day = (RelativeLayout) findViewById(R.id.weekday);
				break;
			}
			final View v = LayoutInflater.from(this).inflate(R.layout.course_card, null); // 加载单个课程布局
			v.setY(height * (course.getClass_start() - 1)); // 设置开始高度,即第几节课开始
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					(course.getClass_end() - course.getClass_start() + 1) * height - 8); // 设置布局高度,即跨多少节课
			v.setLayoutParams(params);
			TextView tv_cname = (TextView) v.findViewById(R.id.card_cname_textview);
			TextView tv_room = (TextView) v.findViewById(R.id.card_room_textview);
			tv_cname.setText(course.getCourse_name());
			tv_room.setText(course.getClass_room());
			day.addView(v);

			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(LessonActivity.this, CourseDetailActivity.class);
					intent.putExtra("courseid", course.getCourseid());
					intent.putExtra("cName", course.getCourse_name());
					intent.putExtra("cRoom", course.getClass_room());
					intent.putExtra("teacher", course.getTeacher());
					intent.putExtra("week", currweek);
					intent.putExtra("day", course.getDay());
					intent.putExtra("start", course.getClass_start());
					intent.putExtra("end", course.getClass_end());
					startActivity(intent);
				}
			});

			// 长按删除课程
			v.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					final View vw = v;
					View view = getLayoutInflater().inflate(R.layout.popup_delete, null);

					final PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
					popup.setAnimationStyle(android.R.style.Animation_Translucent);
					popup.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.alert_light_frame));
					popup.getBackground().setAlpha(100);
					popup.setOutsideTouchable(true);
					popup.setFocusable(true);
					popup.setTouchable(true);
					popup.showAtLocation(view, Gravity.CENTER, 0, 0);

					TextView tv_ok = (TextView) view.findViewById(R.id.delete_ok_textview);
					TextView tv_cancel = (TextView) view.findViewById(R.id.delete_cancel_textview);

					tv_ok.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							vw.setVisibility(View.GONE);// 先隐藏
							day.removeView(vw);// 再移除课程视图
							db = helper.getWritableDatabase();
							db.execSQL("delete from course where course_name = ?",
									new String[] { course.getCourse_name() });
							db.close();
							popup.dismiss();
						}
					});

					tv_cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							popup.dismiss();
						}
					});
					return true;
				}
			});
		}
	}
	
	private void popupFunction(){
		View view = getLayoutInflater().inflate(R.layout.popup_lesson_function, null);

		final PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		popup.setAnimationStyle(android.R.style.Animation_Translucent);
		popup.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.alert_light_frame));
		popup.getBackground().setAlpha(100);
		popup.setOutsideTouchable(true);
		popup.setFocusable(true);
		popup.setTouchable(true);
		popup.showAsDropDown(iv_add);
		
		TextView tv_function = (TextView) view.findViewById(R.id.lesson_add_textview);
		TextView tv_clear = (TextView) view.findViewById(R.id.lesson_clear_textview);
		tv_function.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popup.dismiss();
				Intent intent = new Intent(LessonActivity.this, AddCourseActivity.class);
				startActivity(intent);
			}
		});
		
		tv_clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				View view = getLayoutInflater().inflate(R.layout.popup_delete, null);

				final PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				popup.setAnimationStyle(android.R.style.Animation_Translucent);
				popup.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.alert_light_frame));
				popup.getBackground().setAlpha(100);
				popup.setOutsideTouchable(true);
				popup.setFocusable(true);
				popup.setTouchable(true);
				popup.showAtLocation(view, Gravity.CENTER, 0, 0);
				TextView tv_ok = (TextView) view.findViewById(R.id.delete_ok_textview);
				TextView tv_cancel = (TextView) view.findViewById(R.id.delete_cancel_textview);
				tv_ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						day.removeAllViews();
						db = helper.getWritableDatabase();
						db.execSQL("delete from course");
						db.execSQL("delete from weekday");
						db.close();
						popup.dismiss();
					}
				});
			}
		});
	}

}
