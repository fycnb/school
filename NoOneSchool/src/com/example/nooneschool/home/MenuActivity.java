package com.example.nooneschool.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.nooneschool.R;
import com.example.nooneschool.home.adapter.AdapterLeftMenu;
import com.example.nooneschool.home.adapter.AdapterOrder;
import com.example.nooneschool.home.adapter.AdapterRightMenu;
import com.example.nooneschool.home.list.ListMenu;
import com.example.nooneschool.home.list.ListOrder;
import com.example.nooneschool.util.DataUtil;
import com.example.nooneschool.util.DensityUtil;
import com.example.nooneschool.util.ListItemClickHelp;
import com.example.nooneschool.util.ViewSizeChangeAnimation;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity implements ListItemClickHelp {

	private RelativeLayout shop;
	private RelativeLayout car;
	private LinearLayout detailbtn;
	private TextView number;
	private TextView pay;
	private TextView detail;
	private TextView empty;
	private TextView emptychoice;
	private ImageView listimg;
	private ListView menuLeftList;
	private ListView menuRightList;
	private ListView orderlistview;
	private AdapterLeftMenu leftAdapter;
	private AdapterRightMenu rightAdapter;
	private AdapterOrder orderAdapter;
	private List<ListMenu> listmenu;
	private List<ListOrder> listorder = new ArrayList<>();
	private HashMap hm = new HashMap();
	private PopupWindow popupWindow;

	private int shopFromWidth = 0;
	private int shopToWidth;
	private Boolean flag = false;
	private static final int FROM = 1;
	private static final int TO = 3;
	private static final int ING = 2;
	private int shopTop;
	private int shopLeft;
	private int shopButton;
	private int state = FROM;
	private int orderNumber = 0;
	private String id;
	private ListItemClickHelp callback = this;

	private ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
			new LinkedBlockingDeque<Runnable>(128));
	private Runnable getFoodsDataRunnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		shop = (RelativeLayout) findViewById(R.id.menu_shopping_rl);
		car = (RelativeLayout) findViewById(R.id.menu_car_rl);
		number = (TextView) findViewById(R.id.menu_number_tv);
		pay = (TextView) findViewById(R.id.menu_pay_tv);
		detail = (TextView) findViewById(R.id.menu_detail_tv);
		detailbtn = (LinearLayout) findViewById(R.id.menu_button_ll);
		listimg = (ImageView) findViewById(R.id.menu_arrow_iv);
		empty = (TextView) findViewById(R.id.menu_empty_tv);
		menuLeftList = (ListView) findViewById(R.id.menu_left_listview);
		menuRightList = (ListView) findViewById(R.id.menu_right_listview);

		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		leftAdapter = new AdapterLeftMenu(this, DataUtil.getLeftMenuData());
		menuLeftList.setAdapter(leftAdapter);
		menuLeftList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				leftAdapter.setCheck(position);
				getFoodsData(position);
				leftAdapter.notifyDataSetInvalidated();
			}
		});
		getFoodsData(0);

		car.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (shopFromWidth == 0) {
					shopFromWidth = shop.getWidth();
					shopTop = shop.getTop();
					shopLeft = shop.getLeft();
					shopButton = shop.getBottom();
					shopToWidth = DensityUtil.getWindowWidth(MenuActivity.this) - shopLeft * 2;
				}
				switch (state) {
				case FROM:
					state = ING;
					doAnimation(true);
					break;
				case TO:
					state = ING;
					doAnimation(false);
					break;
				}
			}
		});

		pay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!flag){
					if (shopFromWidth == 0) {
						shopFromWidth = shop.getWidth();
						shopTop = shop.getTop();
						shopLeft = shop.getLeft();
						shopButton = shop.getBottom();
						shopToWidth = DensityUtil.getWindowWidth(MenuActivity.this) - shopLeft * 2;
					}
					switch (state) {
					case FROM:
						state = ING;
						doAnimation(true);
						break;
					case TO:
						state = ING;
						doAnimation(false);
						break;
					}
				}else{
					Toast.makeText(MenuActivity.this, "..", 0).show();
				}
				
			}
		});

		detailbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View contentView = LayoutInflater.from(MenuActivity.this).inflate(R.layout.dropdownlist_popupwindow,
						null);
				if (listorder.size() != 0) {
					popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
							true);

					popupWindow.setTouchable(true);
					popupWindow.setBackgroundDrawable(new ColorDrawable());

					orderlistview = (ListView) contentView.findViewById(R.id.ddlist_show_lv);
					emptychoice = (TextView) contentView.findViewById(R.id.ddlist_empty_tv);
					orderAdapter = new AdapterOrder(MenuActivity.this, listorder, callback);
					orderlistview.setAdapter(orderAdapter);
					orderlistview.setEmptyView(emptychoice);

					popupWindow.showAtLocation(shop, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
							DensityUtil.dip2px(MenuActivity.this, 90));

				}
			}
		});
	}

	public void orderMeal(String id, int position) {
		if (hm.get(id) != null) {
			listorder.get((int) hm.get(id)).addNumber();
		} else {
			ListMenu temp = listmenu.get(position);
			hm.put(id, orderNumber++);
			listorder.add(new ListOrder(id, 1, temp.getMoney(), temp.getName()));
		}
		number.setText((Integer.parseInt(number.getText().toString()) + 1) + "");
		detail.setText("共" + allMoney() + "元,点击查看详情");
	}

	public void cancelMeal(String id) {
		if (hm.get(id) != null) {
			listorder.get((int) hm.get(id)).minusNumber();
			if (listorder.get((int) hm.get(id)).getNumber() == 0) {
				hm.remove(id);
				if (hm.size() == 0) {
					orderNumber = 0;
					listorder.clear();
					popupWindow.dismiss();
				}
				orderAdapter.notifyDataSetChanged();
			}
			number.setText((Integer.parseInt(number.getText().toString()) - 1) + "");
		} else {
			orderAdapter.notifyDataSetChanged();
		}
		detail.setText("共" + allMoney() + "元,点击查看详情");
	}

	public float allMoney() {
		float money = 0;
		for (int i = 0; i < listorder.size(); i++) {
			money += listorder.get(i).getNumber() * listorder.get(i).getMomey();
		}
		return money;
	}

	private void doAnimation(final Boolean what) {
		Animation animator = new ViewSizeChangeAnimation(shop, shop.getHeight(), what ? shopToWidth : shopFromWidth);
		animator.setDuration(1000);
		shop.startAnimation(animator);
		pay.setVisibility(0);
		AlphaAnimation alphaAnimation = new AlphaAnimation(what ? 0 : 1, what ? 1 : 0);
		alphaAnimation.setDuration(1000);
		alphaAnimation.setFillAfter(true);
		pay.startAnimation(alphaAnimation);
		animator.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				flag = false;
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				state = what ? TO : FROM;
				flag = what;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

		});
		// animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		// {
		// @Override
		// public void onAnimationUpdate(ValueAnimator animation) {
		// int curValue = (int) animation.getAnimatedValue();
		// shop.getLayoutParams().height = shopFromWidth +
		// (shopToWidth-shopFromWidth)*(curValue/400);
		// shop.requestLayout();
		// if(shop.getRight()==shopToWidth){
		// flag=true;
		// }else
		// flag=false;
		//
		// if(curValue==0||curValue==400)
		// state = curValue==0?FROM:TO;
		// }
		// });
		// animator.start();
	}
	//
	// private static Bitmap createViewBitmap(View v) {
	// Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
	// Bitmap.Config.ARGB_8888);
	// Canvas canvas = new Canvas(bitmap);
	// v.draw(canvas);
	// return bitmap;
	// }

	public void getFoodsData(final int what) {
		getFoodsDataRunnable = new Runnable() {

			@Override
			public void run() {
				final String result = HomeService.HomeServiceByPost(what, id, "NoOneService/GetMenuServlet?");

				runOnUiThread(new Runnable() {
					public void run() {

						if (result != null && !result.equals("[]")) {
							try {
								listmenu = new ArrayList<>();
								JSONArray ja = new JSONArray(result);
								for (int i = 0; i < ja.length(); i++) {
									JSONObject j = (JSONObject) ja.get(i);

									String id = j.getString("id");
									String name = j.getString("name");
									String imgurl = j.getString("imgurl");
									float money = (float) j.getDouble("money");
									listmenu.add(new ListMenu(id, name, imgurl, money));
								}
								rightAdapter = new AdapterRightMenu(MenuActivity.this, listmenu, callback);
								menuRightList.setAdapter(rightAdapter);
								menuRightList.setEmptyView(empty);

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else if (result != null) {
							listmenu = new ArrayList<>();
							rightAdapter = new AdapterRightMenu(MenuActivity.this, listmenu, callback);
							menuRightList.setAdapter(rightAdapter);
							menuRightList.setEmptyView(empty);
							empty.setText("暂无商品");
						} else {
							listmenu = new ArrayList<>();
							rightAdapter = new AdapterRightMenu(MenuActivity.this, listmenu, callback);
							menuRightList.setAdapter(rightAdapter);
							menuRightList.setEmptyView(empty);
							empty.setText("请检查网络");
						}
					}
				});
			}
		};

		cachedThreadPool.execute(getFoodsDataRunnable);
	}

	@Override
	public void onClick(View item, View widget, int position, int which) {
		switch (which) {
		case R.id.foods_add_iv:
			orderMeal(listmenu.get(position).getId(), position);
			break;
		case R.id.ddlist_jia_iv:
			TextView number1 = (TextView) item.findViewById(R.id.ddlist_number_tv);
			number1.setText((Integer.parseInt(number1.getText().toString()) + 1) + "");
			orderMeal(listorder.get(position).getId(), 0);
			break;
		case R.id.ddlist_jian_iv:
			TextView number2 = (TextView) item.findViewById(R.id.ddlist_number_tv);
			number2.setText((Integer.parseInt(number2.getText().toString()) - 1) + "");
			cancelMeal(listorder.get(position).getId());
			break;
		}
	}
}
