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
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.Activity;
import com.noone.entity.AllOrder;
import com.noone.entity.Menu;
import com.noone.entity.Sender;

@WebServlet("/noone/shop/activity/add")
public class AddActivityServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		int shopid = obj.getIntValue("id");
		int type = obj.getIntValue("type");
		String start = obj.getString("start");
		String end = obj.getString("end");
		String content = obj.getString("content");
		
		ActivityDao activityDao = (ActivityDao) DaoFactory.getInstance("activityDao");
		Activity activity = new Activity();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		activity.setShopid(shopid);
		activity.setType(type);
		activity.setContent(content);
		try {
			activity.setStarttime(sdf.parse(start));
			activity.setEndtime(sdf.parse(end));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		activity = activityDao.save(activity);
		
		String result = "200";
		if(activity!=null){
			result = "100";
		}
		System.out.println(result);
		response.getOutputStream().write(result.toString().getBytes("utf-8"));
	}

}
