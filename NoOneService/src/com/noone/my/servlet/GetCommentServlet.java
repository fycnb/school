package com.noone.my.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noone.db.DBOper;
import com.noone.util.JsonUtil;

public class GetCommentServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		String id = obj.getString("type");
		String number = obj.getString("number");

		response.setContentType("text/html");
		JSONArray js = new JSONArray();
		String content = "";

		DBOper db = new DBOper();
		try {
			db.getConn();

			String sql = "select name,time,content,imgurl,id from comment where restaurantid = ? ORDER BY restaurantid ASC limit "+number+",10 ";
					
			ResultSet rs = db.executeQuery(sql, new String[]{id});
			while (rs.next()) {

				JSONObject json = new JSONObject();
				String id1 = rs.getString(5);
				String name = rs.getString(1);
				String time = rs.getString(2);
				String content1 = rs.getString(3);
				String imgurl = rs.getString(4);
				if (imgurl == null) {
					imgurl = "";
				}
				json.put("id", id1);
				json.put("name", name);
				json.put("time", time);
				json.put("content", content1);
				json.put("imgurl", imgurl);
				js.add(json);
			}

			content = String.valueOf(js);
			System.out.println(content);
			response.getOutputStream().write(content.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
