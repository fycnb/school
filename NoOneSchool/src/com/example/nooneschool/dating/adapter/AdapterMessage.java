package com.example.nooneschool.dating.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.nooneschool.R;
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

public class AdapterMessage extends BaseAdapter {

	private List<ListMessage> list = new ArrayList<ListMessage>();
	private LayoutInflater inflater;

	public AdapterMessage(Context context, List<ListMessage> list) {
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

		SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd");
		Date today = new Date();

		if (view == null) {
			view = inflater.inflate(R.layout.listview_message, null);
			holder = new ViewHolder();

			holder.name = (TextView) view.findViewById(R.id.message_name_tv);
			holder.content = (TextView) view.findViewById(R.id.message_message_tv);
			holder.img = (ImageView) view.findViewById(R.id.message_img_iv);
			holder.time = (TextView) view.findViewById(R.id.message_time_tv);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(list.get(i).getName());
		holder.content.setText(list.get(i).getContent());
		try {
			if (sdf1.format(today).equals(sdf1.format(sdf0.parse(list.get(i).getTime()))))
				holder.time.setText(sdf2.format(sdf0.parse(list.get(i).getTime())));
			else
				holder.time.setText(sdf3.format(sdf0.parse(list.get(i).getTime())));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("fyc", e.toString());
		}
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
		TextView content;
		TextView time;
		ImageView img;
	}

}
