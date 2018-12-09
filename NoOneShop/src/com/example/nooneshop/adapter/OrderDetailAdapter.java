package com.example.nooneshop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneshop.R;
import com.example.nooneshop.R.id;
import com.example.nooneshop.R.layout;
import com.example.nooneshop.list.DetailList;
import com.example.nooneshop.list.OrderList;
import com.example.nooneshop.utils.DownImage;
import com.example.nooneshop.utils.DownImage.ImageCallBack;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrderDetailAdapter extends BaseAdapter{

	private List<DetailList> list = new ArrayList<DetailList>();
	private LayoutInflater inflater;
	
	public OrderDetailAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
	}

	public void setDatas(List<DetailList> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.listview_detail2, null);
			holder = new ViewHolder();

			holder.name = (TextView) view.findViewById(R.id.detail2_name_tv);
			holder.number = (TextView) view.findViewById(R.id.detail2_number_tv);
			holder.money = (TextView) view.findViewById(R.id.detail2_money_tv);
			holder.img = (ImageView) view.findViewById(R.id.detail2_img_iv);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(list.get(position).getName());
		holder.number.setText(list.get(position).getNumber()+"");
		holder.money.setText(list.get(position).getMoney()+"");

		DownImage downImage = new DownImage(list.get(position).getImg(), holder.img.getWidth(), holder.img.getHeight());
		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {
				holder.img.setImageDrawable(drawable);
			}
		});
		return view;
	}
	public class ViewHolder {
		TextView number;
		TextView money;
		TextView name;
		ImageView img;
	}

}
