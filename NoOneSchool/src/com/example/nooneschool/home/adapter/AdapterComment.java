package com.example.nooneschool.home.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.nooneschool.R;
import com.example.nooneschool.home.list.ListComment;
import com.example.nooneschool.home.list.ListFood;
import com.example.nooneschool.home.list.ListMeal;
import com.example.nooneschool.util.DownImage;
import com.example.nooneschool.util.ListItemClickHelp;
import com.example.nooneschool.util.DownImage.ImageCallBack;

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

public class AdapterComment extends BaseAdapter {

	private List<ListComment> list = new ArrayList<ListComment>();
	private LayoutInflater inflater;
	private ListItemClickHelp callback;
	private Boolean flag = true;

	public AdapterComment(Context context, List<ListComment> list, ListItemClickHelp callback) {
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
	
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	@Override
	public View getView(final int i, View view, final ViewGroup viewGroup) {

		final ViewHolder holder;

		if (view == null) {
			view = inflater.inflate(R.layout.listview_comment, null);
			holder = new ViewHolder();

			holder.img = (ImageView) view.findViewById(R.id.comment_img_imageview);
			holder.name = (TextView) view.findViewById(R.id.comment_name_textview);
			holder.time = (TextView) view.findViewById(R.id.comment_time_textview);
			holder.content = (TextView) view.findViewById(R.id.comment_content_textview);
			holder.more = (TextView) view.findViewById(R.id.comment_more_textview);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.name.setText(list.get(i).getName());
		holder.time.setText(list.get(i).getTime());
		holder.content.setText(list.get(i).getContent());
		holder.more.setVisibility(8);
		holder.img.setImageResource(R.drawable.empty);

		DownImage downImage = new DownImage(list.get(i).getImgurl(), holder.img.getWidth(), holder.img.getHeight());
		downImage.loadImage(new ImageCallBack() {

			@Override
			public void getDrawable(Drawable drawable) {
				holder.img.setImageDrawable(drawable);
			}
		});
		if (i + 1 == list.size()&&list.size()>=10&&flag&&(list.size()%10==0)) {
			holder.more.setVisibility(0);
			final int one = holder.more.getId();
			holder.more.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					callback.onClick(view, viewGroup, i, one);
				}
			});
		}

		return view;
	}

	public class ViewHolder {
		TextView name;
		ImageView img;
		TextView time;
		TextView content;
		TextView more;
	}

}
