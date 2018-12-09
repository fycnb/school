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
		// ���"ʱ��"��ť���� ����ʱ��
		time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// �Զ���ؼ�
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				View view = View.inflate(context, R.layout.time_dialog, null);
				final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
				// ��ʼ��ʱ��
				calendar.setTimeInMillis(System.currentTimeMillis());
				timePicker.setIs24HourView(true);
				timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
				timePicker.setCurrentMinute(Calendar.MINUTE);
				// ����time����
				builder.setView(view);
				builder.setTitle("����ʱ����Ϣ");
				builder.setPositiveButton("ȷ  ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mHour = timePicker.getCurrentHour();
						mMinute = timePicker.getCurrentMinute();
						// ʱ��С��10������ ǰ�油0 ��01:12:00
						time.setText(new StringBuilder().append(mHour < 10 ? "0" + mHour : mHour).append(":")
								.append(mMinute < 10 ? "0" + mMinute : mMinute).append(":00"));
						dialog.cancel();
					}
				});
				builder.setNegativeButton("ȡ  ��", new DialogInterface.OnClickListener() {
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
		// ���"����"��ť���� ��������
		date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ͨ���Զ���ؼ�AlertDialogʵ��
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				View view = View.inflate(context, R.layout.date_dialog, null);
				final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
				// �������ڼ�����ʾ ������ϸ��ʾ ����:����\��
				datePicker.setCalendarViewShown(false);
				// ��ʼ����ǰ����
				calendar.setTimeInMillis(System.currentTimeMillis());
				datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH), null);
				// ����date����
				builder.setView(view);
				builder.setTitle("����������Ϣ");
				builder.setPositiveButton("ȷ  ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ���ڸ�ʽ
						StringBuffer sb = new StringBuffer();
						sb.append(String.format("%d-%02d-%02d", datePicker.getYear(), datePicker.getMonth() + 1,
								datePicker.getDayOfMonth()));
						date.setText(sb);
						// ��ֵ��������ʹ��
						mYear = datePicker.getYear();
						mMonth = datePicker.getMonth();
						mDay = datePicker.getDayOfMonth();
						dialog.cancel();
					}
				});
				builder.setNegativeButton("ȡ  ��", new DialogInterface.OnClickListener() {
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
