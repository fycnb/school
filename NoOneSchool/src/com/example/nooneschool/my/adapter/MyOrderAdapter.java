//package com.example.nooneschool.my.adapter;
//
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.nooneschool.my.MyOrder;
//
//import android.content.Context;
//import android.net.NetworkInfo.State;
//import android.os.CountDownTimer;
//import android.util.SparseArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//public class MyOrderAdapter extends BaseAdapter {
//
//	private List<MyOrder> myorderlist;
//	private LayoutInflater mInflater;
//
//	   public MyOrderAdapter(Context context,List<MyOrder> myorders) {
//		   	myorderlist = myorders;
//	        mInflater = LayoutInflater.from(context);
//	    }
//
//
//	@Override
//	public int getCount() {
//		return myorderlist.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return myorderlist.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//	    ViewHolder viewHolder;
//	    //如果view未被实例化过，缓存池中没有对应的缓存
//	    if (convertView == null) {
//	        viewHolder = new ViewHolder();
//	     
//	        convertView = mInflater.inflate(R.layout.item, null);
//
//	    
//	        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_image);
//	        viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
//	        viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content);
//
//	        convertView.setTag(viewHolder);
//	    }else{
//	        viewHolder = (ViewHolder) convertView.getTag();
//	    }
//	
//	    ItemBean bean = mList.get(position);
//
//	  
//	    viewHolder.imageView.setImageResource(bean.itemImageResId);
//	    viewHolder.title.setText(bean.itemTitle);
//	    viewHolder.content.setText(bean.itemContent);
//
//	    return convertView;
//	}
//	
//	class ViewHolder{
//	    public ImageView imageView;
//	    public TextView title;
//	    public TextView content;
//	}
//
//}
