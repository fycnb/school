package com.example.nooneschool.home;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterFood extends BaseAdapter{

	private List<ListFood> list = new ArrayList<ListFood>();
    private LayoutInflater inflater;
    
    public AdapterFood(Context context,List<ListFood> list){
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
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder holder = null;

        if(view == null){
            view = inflater.inflate(R.layout.gridview_food,null);
            holder = new ViewHolder();
            holder.id = (TextView) view.findViewById(R.id.food_type_textview);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.id.setText(list.get(i).getName());
        
		return view;
	}
	public class ViewHolder{
        TextView id;
    }

}
