package com.example.nooneschool.home;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.home.adapter.FoodsAdapter;
import com.example.nooneschool.home.adapter.ShopAdapter;
import com.example.nooneschool.home.list.FoodsList;
import com.example.nooneschool.home.list.ShopList;
import com.example.nooneschool.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity {

	private ImageView back;
	private ImageView search;
	private EditText content;
	private ListView shop;
	private TextView shopempty;
	private ListView foods;
	private TextView foodsempty;

	private List<ShopList> shoplist;
	private List<FoodsList> foodslist;
	private ShopAdapter shopadapter;
	private FoodsAdapter foodsadapter;
	private String id;
	private ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(128));
	private Runnable getSearchDataRunnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		back = (ImageView) findViewById(R.id.search_back_iv);
		search = (ImageView) findViewById(R.id.search_search_iv);
		content = (EditText) findViewById(R.id.search_search_et);
		shop = (ListView) findViewById(R.id.search_shoplist_lv);
		shopempty = (TextView) findViewById(R.id.search_shopempty_tv);
		foods = (ListView) findViewById(R.id.search_foodslist_lv);
		foodsempty = (TextView) findViewById(R.id.search_foodsempty_tv);

		id = getIntent().getStringExtra("id");
		back.setOnClickListener(new onSearchClick());
		search.setOnClickListener(new onSearchClick());

		shopadapter = new ShopAdapter(SearchActivity.this);
		shop.setAdapter(shopadapter);
		shop.setEmptyView(shopempty);

		foodsadapter = new FoodsAdapter(SearchActivity.this);
		foods.setAdapter(foodsadapter);
		foods.setEmptyView(foodsempty);

		foods.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String id1 = ((FoodsList) parent.getAdapter().getItem(position)).getAddress();
				Intent intent = new Intent(SearchActivity.this, ShopActivity.class);
				intent.putExtra("shopid", id1).putExtra("id", id);
				startActivity(intent);
			}
		});

		shop.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long lid) {
				String mealid = ((ShopList) parent.getAdapter().getItem(position)).getId();
				Intent intent = new Intent(SearchActivity.this, ShopActivity.class);
				intent.putExtra("shopid", mealid).putExtra("id", id);
				startActivity(intent);

			}
		});

	}

	public class onSearchClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.search_back_iv:
				finish();
				break;
			case R.id.search_search_iv:
				if (content.getText().toString().trim() == null || content.getText().toString().trim().equals(""))
					Toast.makeText(SearchActivity.this, "请输入关键字", 0).show();
				else
					getSearch(content.getText().toString());
				break;
			}
		}

	}

	public void getSearch(final String content) {
		getSearchDataRunnable = new Runnable() {
			@Override
			public void run() {
				final String result = HomeService.SearchServiceByPost(content);

				runOnUiThread(new Runnable() {
					public void run() {
						if (result != null && !result.equals("[]")) {
							try {
								shoplist = new ArrayList<>();
								foodslist = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									if (j.getString("type").equals("shop")) {
										String id = j.getString("id");
										String name = j.getString("name");
										String address = j.getString("address");
										String send = j.getString("send");
										String delivery = j.getString("delivery");
										String sale = j.getString("sale");
										String imgurl = j.getString("imgurl");

										shoplist.add(new ShopList(id, name, address, send, delivery, sale, imgurl));
									} else {
										String id = j.getString("id");
										String name = j.getString("name");
										String address = j.getString("shopid");
										String money = j.getString("money");
										String sale = j.getString("sale");
										String imgurl = j.getString("img");

										foodslist.add(new FoodsList(id, name, address, imgurl, money, sale));
									}
									foodsadapter.setData(foodslist);
									Utility.setListViewHeight(foods);
									shopadapter.setData(shoplist);
									Utility.setListViewHeight(shop);

								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							shoplist = new ArrayList<>();
							foodslist = new ArrayList<>();
							foodsadapter.setData(foodslist);
							Utility.setListViewHeight(foods);
							shopadapter.setData(shoplist);
							Utility.setListViewHeight(shop);
						}
					}
				});
			}
		};
		cachedThreadPool.execute(getSearchDataRunnable);
	}
}
