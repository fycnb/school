package com.example.nooneschool.home.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.home.list.ListMeal;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;

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

public class AdapterLeftMenu extends BaseAdapter {

	private List<String> list = new ArrayList<>();
	private LayoutInflater inflater;
	private int check ;

	public AdapterLeftMenu(Context context, List<String> list) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		check=0;
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
	
	public void setCheck(int i) {
		check = i;
	}
	
	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		final ViewHolder holder;

		if (view == null) {
			view = inflater.inflate(R.layout.listview_leftmenu, null);
			holder = new ViewHolder();

			holder.tv = (TextView) view.findViewById(R.id.menu_class_tv);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		if(check==i){
			holder.tv.setBackgroundResource(R.color.menucheck);
		}else{
			holder.tv.setBackgroundResource(R.color.menuuncheck);
		}
			
		holder.tv.setText(list.get(i));
		return view;
	}

	public class ViewHolder {
		TextView tv;
	}

}
