package com.noone.controller.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noone.Dao.MenuDao;
import com.noone.Dao.OrderDao;
import com.noone.Dao.SenderDao;
import com.noone.Dao.RestaurantDao;
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.AllOrder;
import com.noone.entity.Menu;
import com.noone.entity.Sender;
import com.noone.entity.Restaurant;

@WebServlet("/noone/shop/detail/get")
public class GetShopDetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		Long shopid = obj.getLong("id");

		RestaurantDao restaurantDao = (RestaurantDao) DaoFactory.getInstance("shopDao");
		Restaurant restaurant = restaurantDao.findOne(shopid);
		MenuDao menuDao = (MenuDao) DaoFactory.getInstance("goodsDao");
		List<Menu> sales = menuDao.findByShopSale(shopid);

		JSONArray js = new JSONArray();
		JSONArray js2 = new JSONArray();
		for (int j = 0; j < sales.size(); j++) {
			JSONObject json2 = new JSONObject();
			json2.put("img", sales.get(j).getImg());
			json2.put("name", sales.get(j).getName());
			json2.put("number", sales.get(j).getSale());
			js2.add(json2);
		}
		JSONObject json = new JSONObject();

		json.put("name", restaurant.getName());
		json.put("address", restaurant.getAddress());
		json.put("state", restaurant.getState());
		json.put("img", restaurant.getImg());
		json.put("detail", js2.toString());
		js.add(json);
		response.getOutputStream().write(js.toString().getBytes("utf-8"));
	}

}
