package com.zh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.zh.Dao.TakerDao;
import com.zh.Dao.UserDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.Taker;
import com.zh.entity.User;
import com.zh.utils.JsonUtil;

@WebServlet("/TakerData")
public class TakerDataServlet extends HttpServlet {

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

		TakerDao takerDao = (TakerDao) DaoFactory.getInstance("takerDao");
		Taker taker = takerDao.findOne(Long.parseLong(takerid));

		resp.setContentType("text/html");
		JSONObject json = new JSONObject();
		json.put("name", taker.getName());
		json.put("number", taker.getNumber());
		json.put("iphone", taker.getIphone());
		String content = String.valueOf(json);
		resp.getOutputStream().write(content.getBytes("utf-8"));

	}
}
