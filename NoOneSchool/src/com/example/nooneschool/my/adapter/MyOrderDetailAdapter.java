package com.example.nooneschool.my.adapter;

import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.my.MyOrderDetail;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyOrderDetailAdapter extends BaseAdapter {

	private List<MyOrderDetail> myOrderDetailList;
	private LayoutInflater mInflater;

	public MyOrderDetailAdapter(Context context, List<MyOrderDetail> MyOrderDetails) {
		myOrderDetailList = MyOrderDetails;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return myOrderDetailList.size();
	}

	@Override
	public Object getItem(int position) {
		return myOrderDetailList.get(position);
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
		        convertView = mInflater.inflate(R.layout.item_myorder_detail, null);

		        holder.iv_image = (ImageView) convertView.findViewById(R.id.detail_image_imageview);
		    	holder.tv_name = (TextView) convertView.findViewById(R.id.detail_name_textview);
				holder.tv_number = (TextView) convertView.findViewById(R.id.detail_number_textview);
				holder.tv_total = (TextView) convertView.findViewById(R.id.detail_total_textview);
				
		        convertView.setTag(holder);
		    }else{
		    	holder = (ViewHolder) convertView.getTag();
		    }

		  MyOrderDetail detail = myOrderDetailList.get(position);

		holder.tv_name.setText(detail.getName());
		holder.tv_number.setText(detail.getNumber());
		holder.tv_total.setText(detail.getTotal());
		 DownImage downImage = new DownImage(detail.getImage());
			downImage.loadImage(new ImageCallBack() {
				
				@Override
				public void getDrawable(Drawable drawable) {
					holder.iv_image.setImageDrawable(drawable);
				}
			});
		return convertView;

	}
	
	
	public class ViewHolder{
        ImageView iv_image;
		TextView tv_name;
		TextView tv_number;
		TextView tv_total;
    }


}

