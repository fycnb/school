package com.example.nooneschool.dating.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.dating.list.ListFriend;
import com.example.nooneschool.dating.list.ListMessage;
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

public class AdapteFriend extends BaseAdapter {

	private List<ListFriend> list = new ArrayList<ListFriend>();
	private LayoutInflater inflater;

	public AdapteFriend(Context context, List<ListFriend> list) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
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
			view = inflater.inflate(R.layout.listview_friend, null);
			holder = new ViewHolder();

			holder.name = (TextView) view.findViewById(R.id.friend_name_tv);
			holder.img = (ImageView) view.findViewById(R.id.friend_img_iv);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(list.get(i).getName());
		holder.img.setImageResource(R.drawable.empty);

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
		TextView name;
		ImageView img;
	}

}
