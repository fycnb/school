package com.example.nooneschool.home.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.home.list.ListMeal;
import com.example.nooneschool.home.list.ListMenu;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;
import com.example.nooneschool.util.ListItemClickHelp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AdapterRightMenu extends BaseAdapter {

	private List<ListMenu> list = new ArrayList<ListMenu>();
	private LayoutInflater inflater;
	private ListItemClickHelp callback;

	public AdapterRightMenu(Context context, List<ListMenu> list, ListItemClickHelp callback) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		this.callback = callback;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int i) {
		return list.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(final int i, View view, final ViewGroup viewGroup) {
		final ViewHolder holder;

		if (view == null) {
			view = inflater.inflate(R.layout.listview_rightmenu, null);
			holder = new ViewHolder();

			holder.name = (TextView) view.findViewById(R.id.foods_name_tv);
			holder.money = (TextView) view.findViewById(R.id.foods_money_tv);
			holder.img = (ImageView) view.findViewById(R.id.foods_img_iv);
			holder.add = (ImageView) view.findViewById(R.id.foods_add_iv);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(list.get(i).getName());
		holder.img.setImageResource(R.drawable.empty);
		holder.money.setText(list.get(i).getMoney());

		DownImage downImage = new DownImage(list.get(i).getImgurl(), holder.img.getWidth(), holder.img.getHeight());
		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {
				holder.img.setImageDrawable(drawable);
			}
		});

		final int one = holder.add.getId();
		holder.add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callback.onClick(view, viewGroup, i, one);
			}
		});

		return view;
	}

	public class ViewHolder {
		TextView name;
		TextView money;
		ImageView img;
		ImageView add;
	}

}
