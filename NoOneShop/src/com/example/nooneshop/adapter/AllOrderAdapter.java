package com.example.nooneshop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneshop.R;
import com.example.nooneshop.R.id;
import com.example.nooneshop.R.layout;
import com.example.nooneshop.list.AllOrderList;
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

public class AllOrderAdapter extends BaseAdapter {

	private List<AllOrderList> list = new ArrayList<AllOrderList>();
	private LayoutInflater inflater;

	public AllOrderAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
	}
	
	public void setDatas(List<AllOrderList> list) {
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
			view = inflater.inflate(R.layout.listview_allorder, null);
			holder = new ViewHolder();

			holder.orderid = (TextView) view.findViewById(R.id.allorder_orderid_tv);
			holder.state = (TextView) view.findViewById(R.id.allorder_state_tv);
			holder.time = (TextView) view.findViewById(R.id.allorder_time_tv);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.orderid.setText(list.get(position).getOrderid());
		holder.state.setText(list.get(position).getState());
		holder.time.setText(list.get(position).getTime());

		return view;
	}

	public class ViewHolder {
		TextView orderid;
		TextView state;
		TextView time;
	}

}
