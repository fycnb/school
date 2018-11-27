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

@WebServlet("/CancelOrder")
public class CancelOrderServlet extends HttpServlet {
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
		String userid = obj.getString("userid");

		OrderDao orderDao = (OrderDao) DaoFactory.getInstance("orderDao");
		int rs = orderDao.cancelOrder(userid, orderid);

		resp.setContentType("text/html");
		if (rs > 0) {
			resp.getOutputStream().write("取消订单成功".getBytes("utf-8"));
		} else {
			resp.getOutputStream().write("取消订单失败".getBytes("utf-8"));
		}
	}
}
