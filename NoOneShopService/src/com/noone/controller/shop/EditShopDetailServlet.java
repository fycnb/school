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
import com.noone.Dao.OrderDao;
import com.noone.Dao.SenderDao;
import com.noone.Dao.RestaurantDao;
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.AllOrder;
import com.noone.entity.Sender;
import com.noone.entity.Restaurant;

@WebServlet("/noone/shop/detail/edit")
public class EditShopDetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		Long shopid = obj.getLong("id");
		String name = obj.getString("name");
		String address = obj.getString("address");

		RestaurantDao restaurantDao = (RestaurantDao) DaoFactory.getInstance("shopDao");
		Restaurant restaurant = restaurantDao.findOne(shopid);
		restaurant.setName(name);
		restaurant.setAddress(address);

		String result = restaurantDao.update(restaurant);

		response.getOutputStream().write(result.toString().getBytes("utf-8"));
	}

}
