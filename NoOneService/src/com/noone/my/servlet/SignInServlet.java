package com.noone.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noone.util.JsonUtil;
import com.noone.db.DBOper;

public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		String userid = obj.getString("userid");

		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH)+1;
		String year = String.valueOf(y);
		String month = String.valueOf(m);
		System.out.println("userid:"+userid+"year:"+year+"month:"+month);
		response.setContentType("text/html");
		JSONArray js = new JSONArray();
		String content = "нч";

		DBOper db = new DBOper();
		try {
			db.getConn();
			String sql = "select day from signin where userid = ? and year = ? and month = ?";
			ResultSet rs = db.executeQuery(sql, new String[] {userid,year,month});
			while (rs.next()) {
				System.out.println("1");
				JSONObject json = new JSONObject();
				int day = rs.getInt("day");
				System.out.println("day:" + day);
				json.put("day", day);
				js.add(json);
			}
			content = String.valueOf(js);
			response.getOutputStream().write(content.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
