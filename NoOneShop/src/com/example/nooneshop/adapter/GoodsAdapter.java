package com.example.nooneshop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneshop.R;
import com.example.nooneshop.R.id;
import com.example.nooneshop.R.layout;
import com.example.nooneshop.list.GoodsList;
import com.example.nooneshop.utils.DownImage;
import com.example.nooneshop.utils.ListItemClickHelp;
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

public class GoodsAdapter extends BaseAdapter{

	private List<GoodsList> list = new ArrayList<GoodsList>();
	private LayoutInflater inflater;
	private ListItemClickHelp callback;
	
	public GoodsAdapter(Context context, ListItemClickHelp callback) {
		this.inflater = LayoutInflater.from(context);
		this.callback = callback;
	}

	public void setDatas(List<GoodsList> list) {
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
	public View getView(final int position, View view, final ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.listview_goods, null);
			holder = new ViewHolder();

			holder.name = (TextView) view.findViewById(R.id.goods_name_tv);
			holder.classify = (TextView) view.findViewById(R.id.goods_classify_tv);
			holder.money = (TextView) view.findViewById(R.id.goods_money_tv);
			holder.img = (ImageView) view.findViewById(R.id.goods_img_iv);
			holder.edit = (TextView) view.findViewById(R.id.goods_edit_tv);
			holder.delete = (TextView) view.findViewById(R.id.goods_delete_tv);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(list.get(position).getName());
		holder.classify.setText(list.get(position).getClassify());
		holder.money.setText(list.get(position).getMoney());

		DownImage downImage = new DownImage(list.get(position).getImg(), holder.img.getWidth(), holder.img.getHeight());
		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {
				holder.img.setImageDrawable(drawable);
			}
		});
		
		final int one = holder.edit.getId();
		holder.edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callback.onClick(view, parent, position, one);
			}
		});
		
		final int two = holder.delete.getId();
		holder.delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callback.onClick(view, parent, position, two);
			}
		});
		return view;
	}
	public class ViewHolder {
		TextView name;
		TextView money;
		TextView classify;
		ImageView img;
		TextView delete;
		TextView edit;
	}

}
