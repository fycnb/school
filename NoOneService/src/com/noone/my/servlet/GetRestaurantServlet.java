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
import com.noone.db.DBOper;
import com.noone.util.JsonUtil;

public class GetRestaurantServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		String type = obj.getString("type");
		int number = obj.getInteger("number");

		if (type.equals("all")) {
			type = "";
		} else {
			type = "where " + type + "='1'";
		}

		response.setContentType("text/html");
		JSONArray js = new JSONArray();
		String content = "";

		DBOper db = new DBOper();
		try {
			db.getConn();

			String sql = "select restaurantid,name,address,send,delivery,image from restaurant "
					+ type
					+ " ORDER BY restaurantid ASC limit "
					+ number
					+ ",5";
			ResultSet rs = db.executeQuery(sql, null);
			while (rs.next()) {

				JSONObject json = new JSONObject();
				String id = rs.getInt(1) + "";
				String name = rs.getString(2);
				String address = rs.getString(3);
				String send = rs.getString(4);
				String delivery = rs.getString(5);
				String imgurl = rs.getString(6);
				if (imgurl == null) {
					imgurl = "";
				}
				String sale = "12";
				json.put("id", id);
				json.put("name", name);
				json.put("address", address);
				json.put("send", send);
				json.put("delivery", delivery);
				json.put("imgurl", imgurl);
				json.put("sale", sale);
				js.add(json);
			}

			content = String.valueOf(js);
			response.getOutputStream().write(content.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
