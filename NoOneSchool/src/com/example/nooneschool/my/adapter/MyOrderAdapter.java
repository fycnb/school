package com.example.nooneschool.my.adapter;

import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.my.MyOrder;
import com.example.nooneschool.util.ImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyOrderAdapter extends BaseAdapter {

	private List<MyOrder> myorderlist;
	private LayoutInflater mInflater;

	public MyOrderAdapter(Context context, List<MyOrder> MyOrders) {
		myorderlist = MyOrders;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_myorder, null);
		}

		ImageView iv_img = (ImageView) convertView.findViewById(R.id.myorder_img_imageview);
		TextView tv_state = (TextView) convertView.findViewById(R.id.myorder_state_textview);
		TextView tv_name = (TextView) convertView.findViewById(R.id.myorder_name_textview);
		TextView tv_time = (TextView) convertView.findViewById(R.id.myorder_time_textview);
		TextView tv_total = (TextView) convertView.findViewById(R.id.myorder_total_textview);

		MyOrder myorder = myorderlist.get(position);

		tv_state.setText(myorder.getState());
		tv_name.setText(myorder.getName());
		tv_time.setText(myorder.getTime());
		tv_total.setText(myorder.getTotal());
		ImageUtils.setImageBitmap(myorder.getImage(), iv_img);
		return convertView;

	}


}
