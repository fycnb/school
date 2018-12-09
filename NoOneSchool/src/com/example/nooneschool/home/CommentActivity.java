package com.example.nooneschool.home;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.home.adapter.CommentAdapter;
import com.example.nooneschool.home.list.CommentList;
import com.example.nooneschool.util.ListItemClickHelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class CommentActivity extends Activity implements ListItemClickHelp {

	private TextView empty;
	private ListView comment;

	private CommentAdapter adapter;
	private List<CommentList> list;

	private ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(128));
	private Runnable getCommentDataRunnable;
	private ListItemClickHelp callback = this;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);

		empty = (TextView) findViewById(R.id.comment_empty_textview);
		comment = (ListView) findViewById(R.id.comment_list_litview);

		list = new ArrayList<>();
		adapter = new CommentAdapter(CommentActivity.this, list, callback);
		comment.setAdapter(adapter);
		comment.setEmptyView(empty);

		Intent intent = getIntent();
		id = intent.getStringExtra("shopid");
		
		getMealData(null);
	}

	public void getMealData(final View item) {

		getCommentDataRunnable = new Runnable() {

			@Override
			public void run() {
				final String result = HomeService.CommentServiceByPost(id);

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String id = j.getString("id");
									String name = j.getString("name");
									String content = j.getString("content");
									String time = j.getString("time");
									String imgurl = j.getString("imgurl");

									list.add(new CommentList(id, name, content, time, imgurl));
								}
								adapter.notifyDataSetInvalidated();

							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(result != null && item!=null){
							adapter.setFlag(false);
							item.findViewById(R.id.comment_more_textview).setVisibility(8);
						}
							
					}
				});
			}
		};

		cachedThreadPool.execute(getCommentDataRunnable);
	}

	@Override
	public void onClick(View item, View widget, int position, int which) {
		switch (which) {
		case R.id.comment_more_textview:
			getMealData(item);
			break;
		}
	}
}
