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

public class GetAdServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		JSONArray js = new JSONArray();
		String content = "";

		try {

			JSONObject json = new JSONObject();
			json.put("id", 1);
			json.put("imgurl",
					"http://169.254.164.100:8080/NoOneService/img_01.png");
			js.add(json);

			json = new JSONObject();
			json.put("id", 2);
			json.put("imgurl",
					"http://169.254.164.100:8080/NoOneService/img_02.png");
			js.add(json);

			json = new JSONObject();
			json.put("id", 3);
			json.put("imgurl",
					"http://169.254.164.100:8080/NoOneService/img_03.png");
			js.add(json);

			json = new JSONObject();
			json.put("id", 4);
			json.put("imgurl",
					"http://169.254.164.100:8080/NoOneService/img_04.png");
			js.add(json);

			content = String.valueOf(js);
			response.getOutputStream().write(content.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
