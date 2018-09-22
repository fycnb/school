package com.example.nooneschool.home;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
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

public class AdapterMeal extends BaseAdapter{

	private List<ListMeal> list = new ArrayList<ListMeal>();
    private LayoutInflater inflater;
    private Boolean isNoData = true;
    private int mHeight;
    public static final int ONE_SCREEN_COUNT = 4;
    
    public AdapterMeal(Context context,List<ListMeal> list){
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

	public void setData(List<ListMeal> list) {
        this.list.clear();
        this.list.addAll(list);

        isNoData = false;
        if (list.size() == 1 && list.get(0).getIsNoData()) {
            // 暂无数据布局
            isNoData = list.get(0).getIsNoData();
            mHeight = list.get(0).getmHeight();
        } else {
            // 添加空数据
            if (list.size() < ONE_SCREEN_COUNT) {
            	this.list.addAll(createEmptyList(ONE_SCREEN_COUNT - list.size()));
            }
        }
        notifyDataSetChanged();
    }
	
    public List<ListMeal> createEmptyList(int size) {
        List<ListMeal> emptyList = new ArrayList<ListMeal>();
        if (size <= 0) return emptyList;
        for (int i=0; i<size; i++) {
            emptyList.add(new ListMeal());
        }
        return emptyList;
    }
    
	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		isNoData = list.get(list.size()-1).getIsNoData();
		mHeight = list.get(list.size()-1).getmHeight();
		if (isNoData) {
			view = inflater.inflate(R.layout.empty_meal_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
            RelativeLayout rootView = (RelativeLayout) view.findViewById(R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return view;
        }
		
		ViewHolder holder = null;

        if(view == null){
            view = inflater.inflate(R.layout.listview_meal,null);
            holder = new ViewHolder();

            holder.ll = (LinearLayout) view.findViewById(R.id.meal_layout_ll);
            holder.img = (ImageView) view.findViewById(R.id.meal_img_imageview);
            holder.name = (TextView) view.findViewById(R.id.meal_name_textview);
            holder.address = (TextView) view.findViewById(R.id.meal_address_textview);
            holder.money = (TextView) view.findViewById(R.id.meal_money_textview);
            holder.sale = (TextView) view.findViewById(R.id.meal_degree_textview);
            
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        
        holder.ll.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(list.get(i).getId())) {
            holder.ll.setVisibility(View.INVISIBLE);
            return view;
        }
        
        
        
		return view;
	}
	public class ViewHolder{
		LinearLayout ll;
        ImageView img;
		TextView name;
		TextView address;
		TextView sale;
		TextView money;
    }

}
