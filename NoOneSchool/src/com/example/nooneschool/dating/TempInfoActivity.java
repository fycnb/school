package com.example.nooneschool.dating;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.R.layout;
import com.example.nooneschool.dating.adapter.AdapterMessage;
import com.example.nooneschool.dating.list.ListMessage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TempInfoActivity extends Activity {

	private List<ListMessage> list;
	private AdapterMessage adapter;
	private ListView message;
	private LinearLayout empty;
	private String id;

	private ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(128));
	private Runnable getMessageDataRunnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp_info);

		message = (ListView) findViewById(R.id.temp_message_lv);
		empty = (LinearLayout) findViewById(R.id.temp_empty_ll);
		getMessgae();

	}

	public void getMessgae() {
		getMessageDataRunnable = new Runnable() {

			@Override
			public void run() {
				final String result = DatingService.HomeServiceByPost(0, "1", "NoOneService/GetMessageServlet?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								list = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String id = j.getString("id");
									String friendid = j.getString("friendid");
									String name = j.getString("name");
									String content = j.getString("info");
									String time = j.getString("time");
									String imgurl = j.getString("imgurl");

									list.add(new ListMessage(id, friendid, "0", "0", content, time, name, imgurl));
								}
								adapter = new AdapterMessage(TempInfoActivity.this, list);
								message.setAdapter(adapter);
								message.setEmptyView(empty);

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
			}
		};

		cachedThreadPool.execute(getMessageDataRunnable);
	}
}
