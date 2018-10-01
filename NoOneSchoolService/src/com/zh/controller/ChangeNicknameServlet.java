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

@WebServlet("/ChangeNickname")
public class ChangeNicknameServlet extends HttpServlet{
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
		String nickname = obj.getString("nickname");
	
		UserDao userDao = (UserDao) DaoFactory.getInstance("userDao");
		String sql = "update user set nickname = ?  where id = ?";
		int rs = userDao.update(sql,nickname,userid);
		
		resp.setContentType("text/html");
		if(rs > 0){
			resp.getOutputStream().write("修改昵称成功".getBytes("utf-8"));
		}else{
			resp.getOutputStream().write("修改昵称失败".getBytes("utf-8"));
		}
	}

}
