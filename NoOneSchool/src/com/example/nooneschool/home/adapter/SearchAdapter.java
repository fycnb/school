package com.example.nooneschool.home.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.home.list.ShopList;
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

public class SearchAdapter extends BaseAdapter{

	private List<ShopList> list = new ArrayList<ShopList>();
	private LayoutInflater inflater;
	
	public SearchAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
	}
	
	public void setData(List<ShopList> list) {
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
	public View getView(int i, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.listview_shop, null);
			holder = new ViewHolder();

			holder.img = (ImageView) view.findViewById(R.id.shop_img_imageview);
			holder.name = (TextView) view.findViewById(R.id.shop_name_textview);
			holder.address = (TextView) view.findViewById(R.id.shop_address_textview);
			holder.send = (TextView) view.findViewById(R.id.shop_send_textview);
			holder.delivery = (TextView) view.findViewById(R.id.shop_delivery_textview);
			holder.sale = (TextView) view.findViewById(R.id.shop_sale_textview);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(list.get(i).getName());
		holder.address.setText(list.get(i).getAddress());
		holder.send.setText(list.get(i).getSend());
		holder.delivery.setText(list.get(i).getDelivery());
		holder.sale.setText(list.get(i).getSale());

		DownImage downImage = new DownImage(list.get(i).getImgurl(), holder.img.getWidth(), holder.img.getHeight());
		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {
				holder.img.setImageDrawable(drawable);
			}
		});
		return view;
	}
	public class ViewHolder {
		ImageView img;
		TextView name;
		TextView address;
		TextView sale;
		TextView send;
		TextView delivery;
	}

}
