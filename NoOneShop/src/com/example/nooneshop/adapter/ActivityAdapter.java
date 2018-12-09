package com.example.nooneshop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneshop.R;
import com.example.nooneshop.R.id;
import com.example.nooneshop.R.layout;
import com.example.nooneshop.list.ActivityList;
import com.example.nooneshop.list.OrderList;
import com.example.nooneshop.utils.ListItemClickHelp;
import com.example.nooneshop.utils.Utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityAdapter extends BaseAdapter {

	private List<ActivityList> list = new ArrayList<ActivityList>();
	private LayoutInflater inflater;
	private ListItemClickHelp callback;

	public ActivityAdapter(Context context, ListItemClickHelp callback) {
		this.inflater = LayoutInflater.from(context);
		this.callback = callback;
	}
	public void setDatas(List<ActivityList> list) {
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
			view = inflater.inflate(R.layout.listview_activity, null);
			holder = new ViewHolder();

			holder.type = (TextView) view.findViewById(R.id.activity_type_tv);
			holder.state = (TextView) view.findViewById(R.id.activity_state_tv);
			holder.time = (TextView) view.findViewById(R.id.activity_time_tv);
			holder.context = (TextView) view.findViewById(R.id.activity_context_tv);
			holder.more = (ImageView) view.findViewById(R.id.activity_more_iv);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.type.setText(list.get(position).getType());
		holder.state.setText(list.get(position).getState());
		holder.time.setText(list.get(position).getStartime()+" - "+list.get(position).getEndtime());
		holder.context.setText(list.get(position).getContext());

		final int one = holder.more.getId();
		holder.more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callback.onClick(view, parent, position, one);
			}
		});

		return view;
	}

	public class ViewHolder {
		TextView time;
		TextView type;
		TextView state;
		TextView context;
		ImageView more;
	}

}
