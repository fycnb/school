package com.example.nooneschool.my.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.my.adapter.SignDateAdapter;
import com.example.nooneschool.my.adapter.SignWeekAdapter;
import com.example.nooneschool.my.inter.OnSignedSuccess;
import com.example.nooneschool.my.service.SignInService;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignDate extends LinearLayout {

	private TextView tv_year;
	private InnerGridView gv_week;
	private InnerGridView gv_date;
	private SignDateAdapter adapterDate;
	private List<Integer> days;
	private List<Boolean> status;

	public SignDate(Context context) {
		super(context);
		init();
	}

	public SignDate(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SignDate(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	
	private void init() {
		View view = View.inflate(getContext(), R.layout.layout_signdate, this);
		tv_year = (TextView) view.findViewById(R.id.signdate_year_textview);
		gv_week = (InnerGridView) view.findViewById(R.id.signdate_week_gridview);
		gv_date = (InnerGridView) view.findViewById(R.id.signdate_date_gridview);
		tv_year.setText(DateUtil.getCurrentYearAndMonth());
		gv_week.setAdapter(new SignWeekAdapter(getContext()));
		status = new ArrayList<>();
		days = new ArrayList<>();
		
	}
	
	public void thread(final String userid){
		new Thread() {
			public void run() {
				final String result = SignInService.SignInByPost(userid);
				if (result != null) {
					try {
						int i = 1;
						JSONArray ja = new JSONArray(result);
					
						for (int a = 0; a < DateUtil.getFirstDayOfMonth() - 1; a++) {
							days.add(0);
							status.add(false);
						}
						int maxDay = DateUtil.getCurrentMonthLastDay();
						if (ja.length() != 0) {
							for (int b = 0; b < ja.length(); b++) {
								JSONObject j = (JSONObject) ja.get(b);
								int day = j.getInt("day");
								
								while (i <= maxDay) {
									if (i == day) {
										days.add(i);
										status.add(true);
										i++;
										break;
									} else {
										days.add(i);
										status.add(false);
									}
									i++;
								}
								while(i <= maxDay){
									days.add(i);
									status.add(false);
									i++;
								}

							}
						} else {
							for (int j = 0; j < maxDay; j++) {
								days.add(j+1);
								status.add(false);
							}
						}
				
					

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					 for (int i = 0; i < DateUtil.getFirstDayOfMonth() - 1; i++) {
			            days.add(0);
			            status.add(false);
			        }
			        int maxDay = DateUtil.getCurrentMonthLastDay();
			        for (int i = 0; i < maxDay; i++) {
			            days.add(i+1);
			            status.add(false);
			        }
				}
			}
		}.start();
		adapterDate = new SignDateAdapter(getContext(), days, status);
		gv_date.setAdapter(adapterDate);
	
	}

	public void setOnSignedSuccess(OnSignedSuccess onSignedSuccess) {
		adapterDate.setOnSignedSuccess(onSignedSuccess);
	}
}
