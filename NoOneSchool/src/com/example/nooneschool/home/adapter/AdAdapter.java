package com.example.nooneschool.home.adapter;

import java.util.List;

import com.example.nooneschool.home.AdActivity;
import com.example.nooneschool.home.list.AdList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
 
public class AdAdapter extends PagerAdapter {

	private List<ImageView> ivList;
	private int count;
	private Context content;
	private List<AdList> list;

	public AdAdapter(List<ImageView> ivList, List<AdList> list, Context content) {
		super();
		this.ivList = ivList;
		this.list = list;
		this.content = content;
		if (ivList != null) {
			count = ivList.size();
		}
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	}

	@Override
	public Object instantiateItem(final ViewGroup container, final int position) {
		final int newPosition = position % count;

		final ImageView iv = ivList.get(newPosition);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(content, AdActivity.class);
				intent.putExtra("url", list.get(newPosition).getUrl());
				content.startActivity(intent);
			}
		});

		container.removeView(iv);
		container.addView(iv);
		return iv;
	}
}
