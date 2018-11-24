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

@WebServlet("/TakerLogin")
public class TakerLoginServlet extends HttpServlet{
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
		String account = obj.getString("account");
		String password = obj.getString("password");

		TakerDao takerDao = (TakerDao) DaoFactory.getInstance("takerDao");
		Taker taker = takerDao.Login(account,password);
		
		resp.setContentType("text/html");
		if (taker != null) { 
			JSONObject json = new JSONObject();
			json.put("takerid", taker.getId());
			resp.getOutputStream().write(String.valueOf(json).getBytes("utf-8"));
		} else {
			resp.getOutputStream().write("’À∫≈ªÚ√‹¬Î ‰»Î¥ÌŒÛ,«Î÷ÿ–¬ ‰»Î".getBytes("utf-8"));
		}
	}
}
