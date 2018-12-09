package com.example.nooneshop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneshop.R;
import com.example.nooneshop.R.id;
import com.example.nooneshop.R.layout;
import com.example.nooneshop.list.DetailList;
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

public class DetailAdapter extends BaseAdapter{

	private List<DetailList> list = new ArrayList<DetailList>();
	private LayoutInflater inflater;
	
	public DetailAdapter(Context context, List<DetailList> list) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.listview_detail, null);
			holder = new ViewHolder();

			holder.name = (TextView) view.findViewById(R.id.detail_name_tv);
			holder.number = (TextView) view.findViewById(R.id.detail_number_tv);
			holder.money = (TextView) view.findViewById(R.id.detail_money_tv);
			holder.img = (ImageView) view.findViewById(R.id.detail_img_iv);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(list.get(position).getName());
		holder.number.setText("×"+list.get(position).getNumber());
		holder.money.setText(list.get(position).getMoney()+"元/个");

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
