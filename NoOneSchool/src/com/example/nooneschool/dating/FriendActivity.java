package com.example.nooneschool.dating;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.R.layout;
import com.example.nooneschool.dating.adapter.AdapteFriend;
import com.example.nooneschool.dating.adapter.AdapterMessage;
import com.example.nooneschool.dating.list.ListFriend;
import com.example.nooneschool.dating.list.ListMessage;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FriendActivity extends Activity {

	private ListView friend;
	private LinearLayout empty;
	
	private List<ListFriend> list;
	private AdapteFriend adapter;
	
	private ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(3, 5,1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128));
	private Runnable getFriendDataRunnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		
		friend = (ListView) findViewById(R.id.friend_friend_lv);
		empty = (LinearLayout) findViewById(R.id.friend_empty_ll);
		
		getFriend();
	}
	
	public void getFriend() {
		getFriendDataRunnable = new Runnable() {

			@Override
			public void run() {
				final String result = DatingService.HomeServiceByPost(0, "1",
						"NoOneService/GetFriendServlet?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								list = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String id = j.getString("id");
									String name = j.getString("name");
									String imgurl = j.getString("imgurl");

									list.add(new ListFriend(id, name, imgurl));
								}
								adapter = new AdapteFriend(FriendActivity.this, list);
								friend.setAdapter(adapter);
								friend.setEmptyView(empty);

							} catch (Exception e) {
								e.printStackTrace();
							}
						} 
					}
				});
			}
		};
		

		cachedThreadPool.execute(getFriendDataRunnable);
	}
}
