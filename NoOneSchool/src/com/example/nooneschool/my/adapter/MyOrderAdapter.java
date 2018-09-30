package com.example.nooneschool.my.adapter;

import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.my.MyOrder;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
		
		final ViewHolder holder;
		    if (convertView == null) {
		    	holder = new ViewHolder();
		        convertView = mInflater.inflate(R.layout.item_myorder, null);

		        holder.iv_img = (ImageView) convertView.findViewById(R.id.myorder_img_imageview);
				holder.tv_state = (TextView) convertView.findViewById(R.id.myorder_state_textview);
				holder.tv_name = (TextView) convertView.findViewById(R.id.myorder_name_textview);
				holder.tv_time = (TextView) convertView.findViewById(R.id.myorder_time_textview);
				holder.tv_total = (TextView) convertView.findViewById(R.id.myorder_total_textview);
				
		        convertView.setTag(holder);
		    }else{
		    	holder = (ViewHolder) convertView.getTag();
		    }

		MyOrder myorder = myorderlist.get(position);

		holder.tv_state.setText(myorder.getState());
		holder.tv_name.setText(myorder.getName());
		holder.tv_time.setText(myorder.getTime());
		holder.tv_total.setText(myorder.getTotal());
		 DownImage downImage = new DownImage(myorder.getImage(),holder.iv_img.getWidth(),holder.iv_img.getHeight());
			downImage.loadImage(new ImageCallBack() {
				
				@Override
				public void getDrawable(Drawable drawable) {
					holder.iv_img.setImageDrawable(drawable);
				}
			});
		return convertView;

	}
	
	
	public class ViewHolder{
        ImageView iv_img;
		TextView tv_state;
		TextView tv_name;
		TextView tv_time;
		TextView tv_total;
    }


}
