package com.example.nooneschool.home.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {

	private List<String> list = new ArrayList<String>();
	private LayoutInflater inflater;
	private int select = -1;

	public AddressAdapter(Context context, List<String> list) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}

	
	public int getSelect() {
		return select;
	}


	public void setSelect(int select) {
		this.select = select;
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
			view = inflater.inflate(R.layout.listview_address, null);
			holder = new ViewHolder();

			holder.address = (TextView) view.findViewById(R.id.address_address_tv);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.address.setText(list.get(i));

		return view;
	}

	public class ViewHolder {
		TextView address;
	}

}
