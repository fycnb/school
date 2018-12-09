package com.noone.controller.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noone.Dao.ActivityDao;
import com.noone.Dao.MenuDao;
import com.noone.Dao.OrderDao;
import com.noone.Dao.SenderDao;
import com.noone.Dao.RestaurantDao;
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.Activity;
import com.noone.entity.AllOrder;
import com.noone.entity.Menu;
import com.noone.entity.Sender;
import com.noone.entity.Restaurant;

@WebServlet("/noone/shop/info")
public class ShopInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		Long id = obj.getLong("id");

		RestaurantDao restaurantDao = (RestaurantDao) DaoFactory.getInstance("shopDao");
		Restaurant restaurant = restaurantDao.findOne(id);
		
		String result = "200";
		if (restaurant != null) {
			JSONArray js = new JSONArray();

			JSONObject json = new JSONObject();
			String name = restaurant.getName();
			int state = restaurant.getState();
			String img = restaurant.getImg();

			json.put("name", name);
			json.put("state", state);
			json.put("img", img);
			js.add(json);
			result = js.toString();
		}
		response.getOutputStream().write(result.toString().getBytes("utf-8"));
	}

}
