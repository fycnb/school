package com.zh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.zh.Dao.OrderDao;
import com.zh.Dao.TakerDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.Taker;
import com.zh.utils.JsonUtil;

@WebServlet("/TakerFinishOrder")
public class TakerFinishOrderServlet extends HttpServlet{
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
		int rs = orderDao.update4ofstate(orderid,takerid);
		
		if (rs > 0) {
			TakerDao takerDao = (TakerDao) DaoFactory.getInstance("takerDao");
			Taker taker = takerDao.findOne(Long.parseLong(takerid));
			int n = taker.getNumber();
			taker.setNumber(++n);
			takerDao.update(taker);
			
			resp.getOutputStream().write("完成配送".getBytes("utf-8"));
		} else {
			resp.getOutputStream().write("请稍后再操作".getBytes("utf-8"));
		}
	}

}
