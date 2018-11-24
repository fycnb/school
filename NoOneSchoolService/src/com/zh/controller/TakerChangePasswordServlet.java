package com.zh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.zh.Dao.TakerDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.Taker;
import com.zh.utils.JsonUtil;

@WebServlet("/TakerChangePassword")
public class TakerChangePasswordServlet extends HttpServlet {
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
		String currentpw = obj.getString("currentpw");
		String newpw = obj.getString("newpw");

		TakerDao takerDao = (TakerDao) DaoFactory.getInstance("takerDao");
		Taker taker = takerDao.findOne(Long.parseLong(takerid));
		String password = taker.getPassword();

		resp.setContentType("text/html");
		if (currentpw.equals(password)) {
			int rs = takerDao.changePwd(newpw, takerid);
			
			if (rs > 0) {
				resp.getOutputStream().write("修改密码成功".getBytes("utf-8"));
			} else {
				resp.getOutputStream().write("修改密码失败，请稍后再试".getBytes("utf-8"));
			}
		} else {
			resp.getOutputStream().write("当前的密码输入不正确".getBytes("utf-8"));
		}

	}
}
