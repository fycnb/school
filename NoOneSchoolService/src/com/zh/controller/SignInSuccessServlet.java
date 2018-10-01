package com.zh.controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.zh.Dao.SignInDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.SignIn;
import com.zh.utils.JsonUtil;

@WebServlet("/SignInSuccess")
public class SignInSuccessServlet extends HttpServlet{
	
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
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		
		SignIn signin = new SignIn();
		signin.setUserid(Integer.parseInt(userid));
		signin.setYear(year);
		signin.setMonth(month);
		signin.setDay(day);
		
		SignInDao signInDao = (SignInDao) DaoFactory.getInstance("signInDao");
		SignIn sign = signInDao.save(signin);
		Long id = sign.getId();
	
		resp.setContentType("text/html");
		if(signInDao.exists(id)){
			resp.getOutputStream().write("签到成功".getBytes("utf-8"));
		}else{
			resp.getOutputStream().write("签到失败".getBytes("utf-8"));
		}
		
		
	}

}
