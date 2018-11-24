package com.zh.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zh.Dao.DetailDao;
import com.zh.Dao.MenuDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.Detail;
import com.zh.entity.Menu;
import com.zh.utils.JsonUtil;

@WebServlet("/TakerOrderDetail")
public class TakerOrderDetailServlet extends HttpServlet {

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
		String orderid = obj.getString("orderid");

		DetailDao detailDao = (DetailDao) DaoFactory.getInstance("detailDao");
		List<Detail> detaillist = detailDao.findById(Long.parseLong(orderid));
		
		resp.setContentType("text/html");
		JSONArray js = new JSONArray();
		for (Detail detail : detaillist) {
			JSONObject json = new JSONObject();
			int menuid = detail.getMenuid();
			int number = detail.getNumber();

			MenuDao menuDao = (MenuDao) DaoFactory.getInstance("menuDao");
			Menu menu = menuDao.findOne(new Long(menuid));
			String name = menu.getFoodname();

			json.put("number", number);
			json.put("name", name);
			js.add(json);
		}
		String content = String.valueOf(js);
		resp.getOutputStream().write(content.getBytes("utf-8"));
	}
}
