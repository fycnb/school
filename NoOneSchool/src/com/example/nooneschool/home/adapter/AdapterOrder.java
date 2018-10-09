package com.example.nooneschool.home.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.home.list.ListFood;
import com.example.nooneschool.home.list.ListMeal;
import com.example.nooneschool.home.list.ListOrder;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;
import com.example.nooneschool.util.ListItemClickHelp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AdapterOrder extends BaseAdapter{

	private List<ListOrder> list = new ArrayList<ListOrder>();
    private LayoutInflater inflater;
    private ListItemClickHelp callback;
    
    public AdapterOrder(Context context,List<ListOrder> list,ListItemClickHelp callback){
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
		
		if(view == null){
            view = inflater.inflate(R.layout.dropdownlist_item,null);
            holder = new ViewHolder();

            holder.ll = (LinearLayout) view.findViewById(R.id.ddlist_show_ll);
            holder.jia = (ImageView) view.findViewById(R.id.ddlist_jia_iv);
            holder.jian = (ImageView) view.findViewById(R.id.ddlist_jian_iv);
            holder.name = (TextView) view.findViewById(R.id.ddlist_name_tv);
            holder.number = (TextView) view.findViewById(R.id.ddlist_number_tv);
            
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
		holder.ll.setVisibility(0);
		if(list.get(i).getNumber()==0){
			holder.ll.setVisibility(View.GONE);
			return view;
		}
		final View v = view;
        holder.name.setText(list.get(i).getName());
        holder.number.setText(list.get(i).getNumber()+"");
        final int one = holder.jia.getId();
		holder.jia.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callback.onClick(v, viewGroup, i, one);
			}
		});
		final int two = holder.jian.getId();
		holder.jian.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callback.onClick(v, viewGroup, i, two);
			}
		});
		
        
        
		return view;
	}
	public class ViewHolder{
		LinearLayout ll;
        ImageView jia;
        ImageView jian;
		TextView name;
		TextView number;
    }

}
