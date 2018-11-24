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
import com.zh.Dao.DetailDao;
import com.zh.Dao.OrderDao;
import com.zh.Dao.RestaurantDao;
import com.zh.Dao.TakerDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.Detail;
import com.zh.entity.Indent;
import com.zh.entity.Restaurant;
import com.zh.entity.Taker;
import com.zh.utils.JsonUtil;

@WebServlet("/MyOrderTaker")
public class MyOrderTakerServlet extends HttpServlet {

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
		String orderid = obj.getString("orderid");
		System.out.println("orderid"+orderid);
		OrderDao orderDao = (OrderDao) DaoFactory.getInstance("orderDao");
		Indent indent = orderDao.findOne(Long.parseLong(orderid));
		Long takerid = (long) indent.getTakerid();
		
		TakerDao takerDao = (TakerDao) DaoFactory.getInstance("takerDao");
		Taker taker = takerDao.findOne(takerid);
		String name = taker.getName();
		String iphone = taker.getIphone();
		System.out.println("iphone"+iphone);
		resp.setContentType("text/html");
		
		JSONObject json = new JSONObject();
		json.put("iphone", iphone);
		resp.getOutputStream().write(String.valueOf(json).getBytes("utf-8"));
	}
}
