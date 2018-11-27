package com.zh.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zh.Dao.OrderDao;
import com.zh.Dao.RestaurantDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.Indent;
import com.zh.entity.Restaurant;
import com.zh.utils.JsonUtil;

@WebServlet("/TakerMyOrder")
public class TakerMyOrderServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuffer sb = JsonUtil.getjson(req);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		String takerid = obj.getString("takerid");
		
		resp.setContentType("text/html");

		OrderDao orderDao = (OrderDao) DaoFactory.getInstance("orderDao");
		List<Indent> orderlist = orderDao.findByTaker(takerid);
		
		JSONArray js = getdata(orderlist);
		String content = String.valueOf(js);
		resp.getOutputStream().write(content.getBytes("utf-8"));
		
		
	}

	private JSONArray getdata(List<Indent> orderlist) {
		JSONArray js = new JSONArray();
		for (Indent order : orderlist) {
			JSONObject json = new JSONObject();
			Long orderid = order.getId();
			int restaurantid = order.getRestaurantid();
			Date t = order.getTime();
			String memo = order.getMemo();
			int state = order.getState();
			String oAddress = order.getAddress();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(t);

			RestaurantDao restaurantDao = (RestaurantDao) DaoFactory
					.getInstance("restaurantDao");
			Restaurant restaurant = restaurantDao
					.findOne(new Long(restaurantid));
			String name = restaurant.getName();
			String rAddress = restaurant.getAddress();
			String iphone = restaurant.getIphone();

			json.put("orderid", orderid);
			json.put("time", time);
			json.put("name", name);
			json.put("memo", memo);
			json.put("rAddress", rAddress);
			json.put("oAddress", oAddress);
			json.put("iphone", iphone);
			json.put("state", state);
			js.add(json);
		}
		return js;
	}

}
