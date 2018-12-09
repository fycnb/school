package com.example.nooneshop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneshop.R;
import com.example.nooneshop.R.id;
import com.example.nooneshop.R.layout;
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

public class OrderAdapter extends BaseAdapter {

	private Context context;
	private List<OrderList> list = new ArrayList<OrderList>();
	private LayoutInflater inflater;
	private ListItemClickHelp callback;

	public OrderAdapter(Context context, ListItemClickHelp callback) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.callback = callback;
	}

	public void setDatas(List<OrderList> list) {
		this.list = list;
		notifyDataSetChanged();
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
	public View getView(final int position, View view, final ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.listview_order, null);
			holder = new ViewHolder();

			holder.orderid = (TextView) view.findViewById(R.id.order_orderid_tv);
			holder.number = (TextView) view.findViewById(R.id.order_number_tv);
			holder.sender = (TextView) view.findViewById(R.id.order_sender_tv);
			holder.time = (TextView) view.findViewById(R.id.order_time_tv);
			holder.money = (TextView) view.findViewById(R.id.order_money_tv);
			holder.memo = (TextView) view.findViewById(R.id.order_memo_tv);
			holder.submit = (Button) view.findViewById(R.id.order_submit_btn);
			holder.finish = (Button) view.findViewById(R.id.order_finish_btn);
			holder.detail = (ListView) view.findViewById(R.id.order_detail_lv);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.orderid.setText(list.get(position).getOrderid());
		holder.sender.setText("骑手:"+list.get(position).getSender());
		holder.time.setText(list.get(position).getTime());
		holder.number.setText(list.get(position).getNumber() + "");
		holder.money.setText(list.get(position).getMoney() + "");
		holder.memo.setText(list.get(position).getMemo());
		DetailAdapter adapter = new DetailAdapter(context, list.get(position).getList());
		holder.detail.setAdapter(adapter);
		Utility.setListViewHeight(holder.detail);
		
		holder.finish.setVisibility(0);
		holder.submit.setText("弃单");
		holder.submit.setEnabled(true);
		if(list.get(position).getState()==1){
			holder.finish.setVisibility(8);
			holder.submit.setText("提交");
			if(list.get(position).getSender().equals("暂无")){
				holder.submit.setEnabled(false);
			}
		}
		
		
		final int one = holder.submit.getId();
		holder.submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callback.onClick(view, parent, position, one);
			}
		});
		final int two = holder.finish.getId();
		holder.finish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callback.onClick(view, parent, position, two);
			}
		});

		return view;
	}

	public class ViewHolder {
		TextView sender;
		TextView number;
		TextView money;
		TextView time;
		TextView memo;
		TextView orderid;
		Button submit;
		Button finish;
		ListView detail;
	}

}
