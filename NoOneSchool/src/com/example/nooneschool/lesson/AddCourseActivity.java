package com.example.nooneschool.lesson;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.lesson.WeekGridViewAdpter.ViewHolder;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class AddCourseActivity extends Activity implements View.OnClickListener {
	private ImageView iv_return;
	private EditText et_course_name;
	private EditText et_class_room;
	private EditText et_teacher;
	private TextView tv_week;
	private TextView tv_day;
	private TextView tv_finish;

	private ArrayAdapter<String> dayAdapter;
	private ArrayAdapter<String> startAdapter;
	private ArrayAdapter<String> endAdapter;
	private WeekGridViewAdpter weekGridViewAdpter;
	List<String> listItemID = new ArrayList<String>();// 周数内容
	private ArrayList<Integer> list;// 周数下标
	private int currday = 0;
	private int currstart = 0;
	private int currend = 0;
	private CheckBox cb_all;

	private CourseSQLiteOpenHelper helper;
	private SQLiteDatabase db;

	private String[] weekArray = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20", "21", "22", "23", "24", "25" };
	private boolean[] weekState = { false, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false, false, false, false, false };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_course);
		init();
	}

	private void init() {
		helper = new CourseSQLiteOpenHelper(this);
		iv_return = (ImageView) findViewById(R.id.course_add_return_imageview);
		et_course_name = (EditText) findViewById(R.id.course_name_edittext);
		et_class_room = (EditText) findViewById(R.id.class_room_edittext);
		et_teacher = (EditText) findViewById(R.id.teacher_edittext);
		tv_week = (TextView) findViewById(R.id.week_textview);
		tv_day = (TextView) findViewById(R.id.day_textview);
		tv_finish = (TextView) findViewById(R.id.course_add_finish_textview);

		iv_return.setOnClickListener(this);
		tv_week.setOnClickListener(this);
		tv_day.setOnClickListener(this);
		tv_finish.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.course_add_return_imageview:
			AddCourseActivity.this.finish();
			break;

		case R.id.week_textview:
			showWeekPopupWindow();
			break;

		case R.id.day_textview:
			showDayPopupWindow();
			break;

		case R.id.course_add_finish_textview:
			finishAddCourse();
			break;
		default:
			break;
		}
	}

	private void showWeekPopupWindow() {
		View view = getLayoutInflater().inflate(R.layout.popup_week, null);

		final PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		popup.setAnimationStyle(android.R.style.Animation_Translucent);
		popup.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.alert_dark_frame));
		popup.getBackground().setAlpha(100);
		popup.setOutsideTouchable(true);
		popup.setFocusable(true);
		popup.setTouchable(true);
		popup.showAtLocation(view, Gravity.CENTER, 0, 0);

		GridView gv_week = (GridView) view.findViewById(R.id.week_gridview);
		TextView tv_ok = (TextView) view.findViewById(R.id.week_ok_textview);
		TextView tv_cancel = (TextView) view.findViewById(R.id.week_cancel_textview);
		cb_all = (CheckBox) view.findViewById(R.id.week_all_checkbox);

		weekGridViewAdpter = new WeekGridViewAdpter(this, weekArray, weekState);
		gv_week.setAdapter(weekGridViewAdpter);
		gv_week.setOnItemClickListener(new weekGirdView());

		String week = tv_week.getText().toString();
		String[] chrstr = week.split(",");
		if (chrstr.length == weekArray.length) {
			cb_all.setChecked(true);
		} else {
			cb_all.setChecked(false);
		}

		cb_all.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cb_all.isChecked()) {
					for (int i = 0; i < weekArray.length; i++) {
						weekGridViewAdpter.getIsSelected().put(i, true);
					}
					weekGridViewAdpter.notifyDataSetChanged();
				} else {
					for (int i = 0; i < weekArray.length; i++) {
						if (weekGridViewAdpter.getIsSelected().get(i)) {
							weekGridViewAdpter.getIsSelected().put(i, false);
						}
					}
					weekGridViewAdpter.notifyDataSetChanged();
				}
			}
		});

		tv_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listItemID.clear();
				list = new ArrayList<Integer>();
				for (int i = 0; i < WeekGridViewAdpter.getIsSelected().size(); i++) {
					if (WeekGridViewAdpter.getIsSelected().get(i)) {
						listItemID.add(weekArray[i]);
						list.add(i);
					}
				}
				weekState = new boolean[weekArray.length];
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						weekState[list.get(i)] = true;
					}
				} else {
					weekState[0] = true;
				}

				if (listItemID.size() == 0) {
					tv_week.setText("未选择周数");
				} else {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < listItemID.size(); i++) {
						if (i == listItemID.size() - 1) {
							sb.append(listItemID.get(i) + "周");
						} else {
							sb.append(listItemID.get(i) + ",");
						}
					}
					tv_week.setText(sb.toString());
				}
				popup.dismiss();
			}
		});

		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				popup.dismiss();
			}
		});
	}

	private void showDayPopupWindow() {
		View view = getLayoutInflater().inflate(R.layout.popup_day, null);

		final PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		popup.setAnimationStyle(android.R.style.Animation_Translucent);
		popup.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.alert_dark_frame));
		popup.getBackground().setAlpha(100);
		popup.setOutsideTouchable(true);
		popup.setFocusable(true);
		popup.setTouchable(true);
		popup.showAtLocation(view, Gravity.CENTER, 0, 0);

		TextView tv_ok = (TextView) view.findViewById(R.id.day_ok_textview);
		TextView tv_cancel = (TextView) view.findViewById(R.id.day_cancel_textview);

		final ListView lv_day = (ListView) view.findViewById(R.id.day_listview);
		final String[] days = { "周一", "周二", "周三", "周四", "周五", "周六", "周七" };
		dayAdapter = new ArrayAdapter<String>(this, R.layout.item_arrayadpter, days);
		lv_day.setAdapter(dayAdapter);
		lv_day.setSelected(true);
		lv_day.setSelection(currday - 1);
		lv_day.setSelector(R.color.blue);
		lv_day.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				currday = position + 1;

			}
		});

		ListView lv_start = (ListView) view.findViewById(R.id.lesson_start_listview);
		final String[] starts = { "第1节", "第2节", "第3节", "第4节", "第5节", "第6节", "第7节", "第8节", "第9节", "第10节", "第11节",
				"第12节" };
		startAdapter = new ArrayAdapter<String>(this, R.layout.item_arrayadpter, starts);
		lv_start.setAdapter(startAdapter);
		lv_start.setSelected(true);
		lv_start.setSelection(currstart - 1);
		lv_start.setSelector(R.color.blue);
		lv_start.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				currstart = position + 1;
			}
		});

		ListView lv_end = (ListView) view.findViewById(R.id.lesson_end_listview);
		final String[] ends = { "到1节", "到2节", "到3节", "到4节", "到5节", "到6节", "到7节", "到8节", "到9节", "到10节", "到11节", "到12节" };
		endAdapter = new ArrayAdapter<String>(this, R.layout.item_arrayadpter, ends);
		lv_end.setAdapter(endAdapter);
		lv_end.setSelected(true);
		lv_end.setSelection(currend - 1);
		lv_end.setSelector(R.color.blue);
		lv_end.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				currend = position + 1;
			}
		});

		tv_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StringBuilder sb = new StringBuilder();
				if (currday == 0 || currstart == 0 || currend == 0) {
					tv_day.setText("未选择节数");
				} else if (currstart > currend) {
					Toast.makeText(AddCourseActivity.this, "选择结束的节数小于开始节数", Toast.LENGTH_SHORT).show();
				} else if (currstart == currend) {
					sb.append(currday + " ");
					sb.append("第" + currstart + "节");
				} else {
					sb.append(days[currday - 1] + " ");
					sb.append(currstart + "-");
					sb.append(currend + "节");
				}
				tv_day.setText(sb.toString());

				popup.dismiss();
			}
		});

		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popup.dismiss();
			}
		});

	}

	private void finishAddCourse() {
		String cName = et_course_name.getText().toString();
		String cRoom = et_class_room.getText().toString();
		String teacher = et_teacher.getText().toString();
		String week = tv_week.getText().toString();
		String day = tv_day.getText().toString();
		if ("".equals(cName)) {
			Toast.makeText(AddCourseActivity.this, "课程名称未填写", Toast.LENGTH_SHORT).show();
		} else if ("".equals(cRoom)) {
			Toast.makeText(AddCourseActivity.this, "教室未填写", Toast.LENGTH_SHORT).show();
		} else if ("".equals(week) || list == null || week.equals("未选择周数")) {
			Toast.makeText(AddCourseActivity.this, "周数未选择", Toast.LENGTH_SHORT).show();
		} else if ("".equals(day)) {
			Toast.makeText(AddCourseActivity.this, "节数未选择", Toast.LENGTH_SHORT).show();
		} else if ("".equals(teacher)) {
			Toast.makeText(AddCourseActivity.this, "老师未填写", Toast.LENGTH_SHORT).show();
		}

		Course course = new Course();
		course.setCourse_name(cName);
		course.setClass_room(cRoom);
		course.setTeacher(teacher);
		course.setDay(currday);
		course.setClass_start(currstart);
		course.setClass_end(currend);

		Long id = saveCourse(course);

		Integer courseid = id.intValue();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Week w = new Week();
				w.setWeek(list.get(i) + 1);
				w.setCourseid(courseid);
				saveWeek(w);
			}
			Toast.makeText(AddCourseActivity.this, "添加成功", 0).show();
			AddCourseActivity.this.finish();
		}

	}

	// 保存数据到数据库
	private Long saveCourse(Course course) {
		db = helper.getWritableDatabase();

		ContentValues Values = new ContentValues();
		Values.put("course_name", course.getCourse_name());
		Values.put("teacher", course.getTeacher());
		Values.put("class_room", course.getClass_room());
		Values.put("day", course.getDay());
		Values.put("class_start", course.getClass_start());
		Values.put("class_end", course.getClass_end());
		Long id = db.insert("course", null, Values);

		db.close();
		return id;
	}

	private void saveWeek(Week week) {
		db = helper.getWritableDatabase();
		ContentValues Values = new ContentValues();
		Values.put("courseid", week.getCourseid());
		Values.put("week", week.getWeek());
		db.insert("weekday", null, Values);
		db.close();
	}

	public class weekGirdView implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ViewHolder holder = (ViewHolder) view.getTag();
			holder.cb_week.toggle();
			WeekGridViewAdpter.getIsSelected().put(position, holder.cb_week.isChecked());
			if (holder.cb_week.isChecked() == true) {
				holder.tv_week.setTextColor(Color.parseColor("#000000"));
				list = new ArrayList<Integer>();
				for (int i = 0; i < WeekGridViewAdpter.getIsSelected().size(); i++) {
					if (WeekGridViewAdpter.getIsSelected().get(i)) {
						list.add(i);
					}
					if (list.size() == weekArray.length) {
						cb_all.setChecked(true);
					} else {
						cb_all.setChecked(false);
					}
				}
			} else {
				holder.tv_week.setTextColor(Color.parseColor("#888888"));
				list = new ArrayList<Integer>();
				for (int i = 0; i < WeekGridViewAdpter.getIsSelected().size(); i++) {
					if (WeekGridViewAdpter.getIsSelected().get(i)) {
						list.add(i);
					}
					if (list.size() == weekArray.length) {
						cb_all.setChecked(true);
					} else {
						cb_all.setChecked(false);
					}
				}
			}

		}

	}

}
