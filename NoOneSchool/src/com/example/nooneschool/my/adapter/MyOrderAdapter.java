package com.example.nooneschool.my.adapter;

import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.my.MyOrder;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;
import com.example.nooneschool.util.ListItemClickHelp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyOrderAdapter extends BaseAdapter {

	private List<MyOrder> myorderlist;
	private LayoutInflater mInflater;
	private ListItemClickHelp callback;

	public MyOrderAdapter(Context context, List<MyOrder> MyOrders, ListItemClickHelp callback) {
		myorderlist = MyOrders;
		this.callback = callback;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return myorderlist.size();
	}

	@Override
	public Object getItem(int position) {
		return myorderlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_myorder, null);

			holder.iv_img = (ImageView) convertView.findViewById(R.id.myorder_img_imageview);
			holder.tv_state = (TextView) convertView.findViewById(R.id.myorder_state_textview);
			holder.tv_name = (TextView) convertView.findViewById(R.id.myorder_name_textview);
			holder.tv_time = (TextView) convertView.findViewById(R.id.myorder_time_textview);
			holder.tv_total = (TextView) convertView.findViewById(R.id.myorder_total_textview);
			holder.btn_cancel = (Button) convertView.findViewById(R.id.myorder_cancel_button);
			holder.btn_comment = (Button) convertView.findViewById(R.id.myorder_comment_button);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final View view = convertView;
		final int p = position;
		final int cancel = holder.btn_cancel.getId();
		final int comment = holder.btn_comment.getId();

		holder.btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 回调接口中listview点击事件
				callback.onClick(view, parent, p, cancel);
			}
		});

		holder.btn_comment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 回调接口中listview点击事件
				callback.onClick(view, parent, p, comment);
			}
		});

		MyOrder myorder = myorderlist.get(position);
		String state = myorder.getState();

		switch (state) {
		case "待接单":
			holder.btn_cancel.setVisibility(View.VISIBLE);
			setStateColor(holder.tv_state, "#EEEE00");
			break;

		case "已接单":
			setStateColor(holder.tv_state, "#0000FF");
			break;

		case "待评价":
			holder.btn_comment.setVisibility(View.VISIBLE);
			setStateColor(holder.tv_state, "#EEEE00");
			break;

		case "已完成":
			setStateColor(holder.tv_state, "#00FF00");
			break;

		case "已取消":
			setStateColor(holder.tv_state, "#FF0000");
			break;

		case "已拒绝":
			setStateColor(holder.tv_state, "#FF0000");
			break;

		default:
			break;
		}

		holder.tv_state.setText(state);
		holder.tv_name.setText(myorder.getName());
		holder.tv_time.setText(myorder.getTime());
		holder.tv_total.setText(myorder.getTotal());
		DownImage downImage = new DownImage(myorder.getImage(), holder.iv_img.getWidth(), holder.iv_img.getHeight());
		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {
				holder.iv_img.setImageDrawable(drawable);
			}
		});
		return convertView;

	}

	private void setStateColor(TextView tv, String color) {
		tv.setTextColor(Color.parseColor(color));
	}

	public class ViewHolder {
		ImageView iv_img;
		TextView tv_state;
		TextView tv_name;
		TextView tv_time;
		TextView tv_total;
		Button btn_cancel;
		Button btn_comment;
	}

}
