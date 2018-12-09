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

@WebServlet("/noone/shop/register")
public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		String password = obj.getString("password");
		String name = obj.getString("name");
		String address = obj.getString("address");
		String phone = obj.getString("phone");
		
		RestaurantDao restaurantDao = (RestaurantDao) DaoFactory.getInstance("shopDao");
		Restaurant restaurant = new Restaurant();
		restaurant.setAddress(address);
		restaurant.setName(name);
		restaurant.setPassword(password);
		restaurant.setPhone(phone);
		
		restaurant = restaurantDao.save(restaurant);
		String result = "200";
		if (restaurant.getId()!=null) {
			result = "100";
			restaurant.setImg("http://192.168.0.102:8080/NoOneShop/Upload/shop" + restaurant.getId() + ".png");
			restaurantDao.update(restaurant);
		}
		response.getOutputStream().write(result.toString().getBytes("utf-8"));
	}

}
