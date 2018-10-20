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

public class GetMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		String account = obj.getString("type");

		response.setContentType("text/html");
		JSONArray js = new JSONArray();
		String content = "";

		System.out.println("id:"+account);
		DBOper db = new DBOper();
		try {
			db.getConn();
			ResultSet rs = db
					.executeQuery(
							"select * from msg"
									+ account
									+ ",`user` where friendid = account and id in (select MAX(id) from msg"
									+ account
									+ " group by friendid) and `read`=0", null);
			while (rs.next()) {

				JSONObject json = new JSONObject();
				String id = rs.getString(1);
				String friendid = rs.getString(2);
				String info = rs.getString(5);
				String time = rs.getString(6);
				String name = rs.getString(8);
				String imgurl = rs.getString(9);
				if (imgurl == null) {
					imgurl = "";
				}
				json.put("id", id);
				json.put("friendid", friendid);
				json.put("info", info);
				json.put("time", time);
				json.put("name", name);
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
