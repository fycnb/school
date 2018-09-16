package com.example.nooneschool.my.adapter;



import com.example.nooneschool.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class SignWeekAdapter extends BaseAdapter {

    private String[] week = {"日","一","二","三","四","五","六"};
    private Context context;

    public SignWeekAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return week.length;
    }

    @Override
    public Object getItem(int i) {
        return week[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_date_gridview,null);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv = (TextView) view.findViewById(R.id.item_week_textview);
        viewHolder.tv.setText(week[i]);
        return view;
    }

    class ViewHolder{
        TextView tv;
    }
}
