package com.example.nooneschool.lesson;

import java.util.HashMap;

import com.example.nooneschool.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class WeekGridViewAdpter extends BaseAdapter {
	private static HashMap<Integer, Boolean> isSelected;
	private LayoutInflater mInflater;
	private String[] items;
	private boolean[] states;

	public WeekGridViewAdpter(Context context, String[] items, boolean[] states) {
		this.items = items;
		isSelected = new HashMap<Integer, Boolean>();
		this.states = states;
		mInflater = LayoutInflater.from(context);
		initDate();
	}

	private void initDate(){
		for(int i = 0;i<states.length;i++){
			if (states[i]==true) {
				getIsSelected().put(i, true);
			}else{
				getIsSelected().put(i, false);
			}
		}
	}

	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_week_gridview, null);
			holder.cb_week = (CheckBox) convertView.findViewById(R.id.item_week_checkbox);
			holder.tv_week = (TextView) convertView.findViewById(R.id.item_week_textview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_week.setText(items[position]);
		holder.cb_week.setChecked(getIsSelected().get(position));
		if(getIsSelected().get(position)){
			holder.tv_week.setTextColor(Color.parseColor("#000000"));
		}else{
			holder.tv_week.setTextColor(Color.parseColor("#888888"));
		}
		return convertView;
	}
	public class ViewHolder {
		CheckBox cb_week;
		TextView tv_week;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

}
