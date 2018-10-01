package com.zh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.zh.Dao.UserDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.User;
import com.zh.utils.JsonUtil;

@WebServlet("/UserData")
public class UserDataServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		StringBuffer sb = JsonUtil.getjson(req);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		String userid = obj.getString("userid");
		
		UserDao userDao = (UserDao) DaoFactory.getInstance("userDao");
		User user = userDao.findOne(Long.parseLong(userid));
		
		resp.setContentType("text/html");
		JSONObject json = new JSONObject();
		json.put("account", user.getAccount());
		json.put("nickname", user.getNickname());
		json.put("sobo", user.getSobo());
		json.put("head", user.getHead());
		String content = String.valueOf(json);
		resp.getOutputStream().write(content.getBytes("utf-8"));
		
	}
}
