package com.noone.controller.shop;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/noone/shop/activity/get")
public class GetAllActivityServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		int shopid = obj.getIntValue("id");
		
		ActivityDao activityDao = (ActivityDao) DaoFactory.getInstance("activityDao");
		List<Activity> list = activityDao.findByShopId(shopid);
		
		JSONArray js = new JSONArray();
		
		for (int i = 0;i<list.size();i++) {
			JSONObject json = new JSONObject();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			json.put("activityid", list.get(i).getId());
			json.put("type", list.get(i).getType());
			json.put("state", list.get(i).getState());
			json.put("startime", sdf.format(list.get(i).getStarttime()));
			json.put("context", list.get(i).getContent());
			json.put("endtime", sdf.format(list.get(i).getEndtime()));
			js.add(json);
		}
		
		response.getOutputStream().write(js.toString().getBytes("utf-8"));
	}

}
