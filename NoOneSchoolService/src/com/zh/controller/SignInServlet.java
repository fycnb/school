package com.zh.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Param;
import com.zh.Dao.SignInDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.SignIn;
import com.zh.utils.JsonUtil;

@WebServlet("/SignIn")
public class SignInServlet extends HttpServlet{
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
		
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH)+1);
		
		SignInDao signInDao = (SignInDao) DaoFactory.getInstance("signInDao");
		String sql = "select day from signin where userid = ? and year = ? and month = ?";
		List<SignIn> list = signInDao.find(sql,userid,year,month);
		
		resp.setContentType("text/html");
		JSONArray js = new JSONArray();
		
		for(SignIn signin : list){
			JSONObject json = new JSONObject();
			int day = signin.getDay();
			json.put("day", day);
			js.add(json);
		}
		String content = String.valueOf(js);
		resp.getOutputStream().write(content.getBytes("utf-8"));
		
	}

}
