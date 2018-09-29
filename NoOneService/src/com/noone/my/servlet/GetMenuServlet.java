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

public class GetMenuServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		String restaurantid = obj.getString("type");
		String type = obj.getString("number");

		response.setContentType("text/html");
		JSONArray js = new JSONArray();
		String content = "";

		DBOper db = new DBOper();
		try {
			db.getConn();
			System.out.println(type+restaurantid);

			String sql = "select id,name,money,imgurl from menu where restaurantid=? and type=? ";
			ResultSet rs = db.executeQuery(sql, new String[]{restaurantid,type});
			while (rs.next()) {

				JSONObject json = new JSONObject();
				String id = rs.getInt(1) + "";
				String name = rs.getString(2);
				String money = rs.getString(3);
				String imgurl = rs.getString(4);
				if (imgurl == null) {
					imgurl = "";
				}
				json.put("id", id);
				json.put("name", name);
				json.put("money", money);
				json.put("imgurl", imgurl);
				js.add(json);
			}
			System.out.println(js.toString());
			content = String.valueOf(js);
			response.getOutputStream().write(content.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
