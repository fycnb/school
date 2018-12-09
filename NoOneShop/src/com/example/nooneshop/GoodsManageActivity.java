package com.example.nooneshop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneshop.adapter.GoodsAdapter;
import com.example.nooneshop.list.GoodsList;
import com.example.nooneshop.service.MyService;
import com.example.nooneshop.utils.ListItemClickHelp;
import com.example.nooneshop.utils.MyToast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GoodsManageActivity extends Activity implements ListItemClickHelp {

	private ImageView back;
	private ImageView add;
	private EditText edit;
	private TextView search;
	private ImageView clean;
	private ListView listview;
	private TextView empty;
	private SwipeRefreshLayout swipeRefreshLayout;
	private SwipeRefreshLayout emptyRefreshLayout;

	private String id;
	private GoodsAdapter adapter;
	private List<GoodsList> list;
	private List<GoodsList> templist;
	private ListItemClickHelp callback = this;
	private ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(128));
	private Runnable getGoodsDataRunnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_manage);

		id = getIntent().getStringExtra("id");
		back = (ImageView) findViewById(R.id.goods_back_iv);
		add = (ImageView) findViewById(R.id.goods_add_iv);
		edit = (EditText) findViewById(R.id.goods_search_et);
		search = (TextView) findViewById(R.id.goods_search_btn);
		clean = (ImageView) findViewById(R.id.goods_clean_iv);
		listview = (ListView) findViewById(R.id.goods_list_lv);
		empty = (TextView) findViewById(R.id.goods_empty_tv);
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.goods_refresh_layout);
		emptyRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.goods_empty_layout);

		swipeRefreshLayout.setOnRefreshListener(new onRefresh());
		emptyRefreshLayout.setOnRefreshListener(new onRefresh());

		back.setOnClickListener(new onClick());
		add.setOnClickListener(new onClick());
		search.setOnClickListener(new onClick());
		clean.setOnClickListener(new onClick());

		edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (edit.getText().toString().equals("")) {
					clean.setVisibility(4);
				} else
					clean.setVisibility(0);
			}
		});

		adapter = new GoodsAdapter(GoodsManageActivity.this, callback);
		listview.setAdapter(adapter);
		listview.setEmptyView(emptyRefreshLayout);
		getGoodsData();
	}

	public class onRefresh implements OnRefreshListener {

		@Override
		public void onRefresh() {
			new Handler().postDelayed(new Runnable() {// 模拟耗时操作
				@Override
				public void run() {
					swipeRefreshLayout.setRefreshing(false);// 取消刷新
					swipeRefreshLayout.setEnabled(true);
					emptyRefreshLayout.setRefreshing(false);// 取消刷新
					emptyRefreshLayout.setEnabled(true);
				}
			}, 4000);
			swipeRefreshLayout.setEnabled(false);
			emptyRefreshLayout.setEnabled(false);
			getGoodsData();
		}

	}

	public void getGoodsData() {
		getGoodsDataRunnable = new Runnable() {

			@Override
			public void run() {
				final String result = MyService.GoodsServiceByPost(id, "NoOneShop/noone/shop/goods/get?");

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
									String img = j.getString("img");
									int classify = j.getInt("classify");
									String money = j.getString("money");

									list.add(new GoodsList(id, name, img, classify, money));
								}
								adapter.setDatas(list);
								;

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (result != null) {
							list = new ArrayList<>();
							adapter.setDatas(list);
							;
							empty.setText("通过右上角按钮添加商品");
						} else {
							empty.setText("请检查网络");
						}
					}
				});
			}
		};

		cachedThreadPool.execute(getGoodsDataRunnable);
	}

	public void deleteGoods(final String i) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String result = MyService.GoodsServiceByPost(i, "NoOneShop/noone/shop/goods/delete?");

				runOnUiThread(new Runnable() {
					public void run() {
						if (result == null) {
							MyToast.Toast(GoodsManageActivity.this, "请检查网络!");
						} else if (result.equals("100")) {
							getGoodsData();
						} else if (result.equals("200")) {
							MyToast.Toast(GoodsManageActivity.this, "删除失败!");
						}
					}
				});
			}
		}).start();
	}

	public class onClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.goods_back_iv:
				finish();
				break;
			case R.id.goods_add_iv:
				startActivityForResult(new Intent(GoodsManageActivity.this, AddGoodsActivity.class).putExtra("id", id),
						1);
				break;
			case R.id.goods_search_btn:
				getTempList(edit.getText().toString());
				break;
			case R.id.goods_clean_iv:
				edit.setText("");
				break;
			}
		}

	}

	public void getTempList(String type) {
		templist = new ArrayList<>();
		for (GoodsList goods : list) {
			if (goods.getName().equals(type) || goods.getClassify().equals(type) || goods.getMoney().equals(type)) {
				templist.add(goods);
			}
		}

		if (type.trim().equals("") || type == null) {
			templist = list;
		}
		adapter.setDatas(templist);
		edit.setText("");
	}

	@Override
	public void onClick(View item, View widget, int position, int which) {
		switch (which) {
		case R.id.goods_edit_tv:
			startActivityForResult(new Intent(GoodsManageActivity.this, AddGoodsActivity.class).putExtra("id", id)
					.putExtra("classify", list.get(position).getClassifyNumber())
					.putExtra("goodsid", list.get(position).getId()).putExtra("money", list.get(position).getMoney())
					.putExtra("name", list.get(position).getName()).putExtra("img", list.get(position).getImg()), 1);
			break;
		case R.id.goods_delete_tv:
			deleteGoods(list.get(position).getId());
			break;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 0:

			break;
		case 1:
			getGoodsData();
			break;
		}
	}
}
