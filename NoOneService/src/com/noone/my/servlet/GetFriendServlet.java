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

public class GetFriendServlet extends HttpServlet {

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
			ResultSet rs = db.executeQuery("select * from message,`user` where ((toid = account and toid<>?) or (fromid = account and fromid<>?)) and id in (select max(id) from message,`user` where account in(select account from user) and account=toid and fromid=? or account=fromid and toid=? group by account)",
					new String[] { account,account,account,account });
			while (rs.next()) {

				JSONObject json = new JSONObject();
				String id = rs.getString(1);
				String info = rs.getString(2);
				String time = rs.getString(3);
				String fromid = rs.getString(4);
				String toid = rs.getString(5);
				String state = rs.getString(6);
				String name = rs.getString(8);
				String imgurl = rs.getString(9);
				if (imgurl == null) {
					imgurl = "";
				}
				json.put("id", id);
				json.put("info", info);
				json.put("time", time);
				json.put("fromid", fromid);
				json.put("toid", toid);
				json.put("state", state);
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
