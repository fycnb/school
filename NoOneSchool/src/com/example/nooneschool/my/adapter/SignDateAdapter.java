package com.example.nooneschool.my.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.my.inter.OnSignedSuccess;

public class SignDateAdapter extends BaseAdapter {

	private Context context;
	private List<Integer> days;
	private List<Boolean> status;
	private OnSignedSuccess onSignedSuccess;

	public SignDateAdapter(Context context, List<Integer> days, List<Boolean> status) {
		this.context = context;
		this.days = days;
		this.status = status;
	}

	@Override
	public int getCount() {
		return days.size();
	}

	@Override
	public Object getItem(int i) {
		return days.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(final int i, View view, final ViewGroup parent) {
		ViewHolder viewHolder;

		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.item_date_gridview, null);
			viewHolder = new ViewHolder();
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.rl_item = (RelativeLayout) view.findViewById(R.id.item_date_layout);
		viewHolder.tv_week = (TextView) view.findViewById(R.id.item_week_textview);
		viewHolder.iv_Status = (ImageView) view.findViewById(R.id.item_Status_imageview);
		viewHolder.tv_week.setText(days.get(i) + "");
		if (days.get(i) == 0) {
			viewHolder.rl_item.setVisibility(View.GONE);
		}
		if (status.get(i)) {
			viewHolder.tv_week.setTextColor(Color.parseColor("#FD0000"));
			viewHolder.iv_Status.setVisibility(View.VISIBLE);
		} else {
			viewHolder.tv_week.setTextColor(Color.parseColor("#666666"));
			viewHolder.iv_Status.setVisibility(View.GONE);
		}

		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Calendar calendar = Calendar.getInstance();
				int day = calendar.get(Calendar.DATE);
				if (day == days.get(i)) {
					if (status.get(i)) {
						Toast.makeText(context, "已经签到啦!", 0).show();
					} else {
						status.set(i, true);
						notifyDataSetChanged();
						if (onSignedSuccess != null) {
							onSignedSuccess.OnSignedSuccess();
						}
					}
				} else {
					Toast.makeText(context, "不能签到!", 0).show();
				}
			}
		});
		return view;
	}

	class ViewHolder {
		RelativeLayout rl_item;
		TextView tv_week;
		ImageView iv_Status;
	}

	public void setOnSignedSuccess(OnSignedSuccess onSignedSuccess) {
		this.onSignedSuccess = onSignedSuccess;
	}

}
