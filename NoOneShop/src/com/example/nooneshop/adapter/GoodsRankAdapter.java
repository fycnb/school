package com.example.nooneshop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneshop.R;
import com.example.nooneshop.R.id;
import com.example.nooneshop.R.layout;
import com.example.nooneshop.list.GoodsRankList;
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

public class GoodsRankAdapter extends BaseAdapter{

	private List<GoodsRankList> list = new ArrayList<GoodsRankList>();
	private LayoutInflater inflater;
	
	public GoodsRankAdapter(Context context, List<GoodsRankList> list) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
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
			view = inflater.inflate(R.layout.listview_rank, null);
			holder = new ViewHolder();

			holder.name = (TextView) view.findViewById(R.id.rank_name);
			holder.img = (ImageView) view.findViewById(R.id.rank_img);
			holder.number = (TextView) view.findViewById(R.id.rank_number);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(list.get(position).getName());
		holder.number.setText(list.get(position).getNumber()+"");

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
		TextView name;
		TextView number;
		ImageView img;
	}

}
