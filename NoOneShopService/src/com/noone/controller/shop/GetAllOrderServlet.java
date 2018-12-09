package com.noone.controller.shop;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.noone.Dao.OrderDao;
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.AllOrder;

@WebServlet("/noone/shop/order/getall")
public class GetAllOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		int shopid = obj.getIntValue("id");
		int orderstate = obj.getIntValue("state");
		int month = obj.getIntValue("month");
		int sequence = obj.getIntValue("sequence");
		
		OrderDao orderDao = (OrderDao) DaoFactory.getInstance("orderDao");
		List<AllOrder> list = orderDao.findAllByThree(shopid,month,orderstate,sequence);
		JSONArray js = new JSONArray();
		
		for (int i = 0;i<list.size();i++) {
			JSONObject json = new JSONObject();
			Long orderid = list.get(i).getId();
			int state = list.get(i).getState();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
			String time = sdf.format(list.get(i).getTime());
			
			
			json.put("orderid", orderid);
			json.put("state", state);
			json.put("time", time);
			js.add(json);
		}
		
		response.getOutputStream().write(js.toString().getBytes("utf-8"));
	}

}
