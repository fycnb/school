package com.zh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.zh.Dao.OrderDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.utils.JsonUtil;

@WebServlet("/TakerAcceptOrder")
public class TakerAcceptOrderServlet extends HttpServlet{
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
		String orderid = obj.getString("orderid");
		
		OrderDao orderDao = (OrderDao) DaoFactory.getInstance("orderDao");
		int rs = orderDao.updateByTakerid(orderid,takerid);
		
		if (rs > 0) {
			resp.getOutputStream().write("成功接单,请尽快到达商家取餐".getBytes("utf-8"));
		} else {
			resp.getOutputStream().write("请稍后再操作".getBytes("utf-8"));
		}
	}

}
