package com.example.nooneshop.utils;

import java.util.Calendar;

import com.example.nooneshop.R;
import com.example.nooneshop.R.id;
import com.example.nooneshop.R.layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class Date_TimeUtil {
	private Calendar calendar = Calendar.getInstance();
	private int mYear, mMonth, mDay;
	private int mHour;
	private Integer mMinute;
	private Context context;

	public Date_TimeUtil(Context context) {
		this.context = context;
	}

	public void setTime(final TextView time) {
		// 点击"时间"按钮布局 设置时间
		time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 自定义控件
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				View view = View.inflate(context, R.layout.time_dialog, null);
				final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
				// 初始化时间
				calendar.setTimeInMillis(System.currentTimeMillis());
				timePicker.setIs24HourView(true);
				timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
				timePicker.setCurrentMinute(Calendar.MINUTE);
				// 设置time布局
				builder.setView(view);
				builder.setTitle("设置时间信息");
				builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mHour = timePicker.getCurrentHour();
						mMinute = timePicker.getCurrentMinute();
						// 时间小于10的数字 前面补0 如01:12:00
						time.setText(new StringBuilder().append(mHour < 10 ? "0" + mHour : mHour).append(":")
								.append(mMinute < 10 ? "0" + mMinute : mMinute).append(":00"));
						dialog.cancel();
					}
				});
				builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.create().show();
			}
		});
	}

	public void setDate(final TextView date) {
		// 点击"日期"按钮布局 设置日期
		date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 通过自定义控件AlertDialog实现
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				View view = View.inflate(context, R.layout.date_dialog, null);
				final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
				// 设置日期简略显示 否则详细显示 包括:星期\周
				datePicker.setCalendarViewShown(false);
				// 初始化当前日期
				calendar.setTimeInMillis(System.currentTimeMillis());
				datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH), null);
				// 设置date布局
				builder.setView(view);
				builder.setTitle("设置日期信息");
				builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 日期格式
						StringBuffer sb = new StringBuffer();
						sb.append(String.format("%d-%02d-%02d", datePicker.getYear(), datePicker.getMonth() + 1,
								datePicker.getDayOfMonth()));
						date.setText(sb);
						// 赋值后面闹钟使用
						mYear = datePicker.getYear();
						mMonth = datePicker.getMonth();
						mDay = datePicker.getDayOfMonth();
						dialog.cancel();
					}
				});
				builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.create().show();
			}
		});
	}
}
