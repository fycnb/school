package com.example.nooneschool.home.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.home.list.OrderList;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.DownImage.ImageCallBack;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderAdapter2 extends BaseAdapter{

	private List<OrderList> list = new ArrayList<OrderList>();
    private LayoutInflater inflater;
    
    public OrderAdapter2(Context context,List<OrderList> list){
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
		
		if(view == null){
            view = inflater.inflate(R.layout.listview_order,null);
            holder = new ViewHolder();

            holder.img = (ImageView) view.findViewById(R.id.order_img_iv);
            holder.name = (TextView) view.findViewById(R.id.order_name_tv);
            holder.number = (TextView) view.findViewById(R.id.order_number_tv);
            
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(list.get(i).getName());
        holder.number.setText(list.get(i).getNumber()+"");
        DownImage downImage = new DownImage(list.get(i).getImg(), holder.img.getWidth(), holder.img.getHeight());
		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {
				holder.img.setImageDrawable(drawable);
			}
		});
		
        
        
		return view;
	}
	public class ViewHolder{
        ImageView img;
		TextView name;
		TextView number;
    }

}
