package com.noone.controller.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noone.Dao.MenuDao;
import com.noone.Dao.OrderDao;
import com.noone.Dao.SenderDao;
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.AllOrder;
import com.noone.entity.Menu;
import com.noone.entity.Sender;

@WebServlet("/noone/shop/goods/edit")
public class EditGoodsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		Long goodsid = obj.getLong("id");
		String name = obj.getString("name");
		String money = obj.getString("money");
		int classify = obj.getIntValue("classify");
		
		MenuDao menuDao = (MenuDao) DaoFactory.getInstance("goodsDao");
		Menu menu = menuDao.findOne(goodsid);
		menu.setClassify(classify);
		menu.setName(name);
		menu.setPrice(money);
		String result = menuDao.update(menu);
		
		response.getOutputStream().write(result.toString().getBytes("utf-8"));
	}

}
