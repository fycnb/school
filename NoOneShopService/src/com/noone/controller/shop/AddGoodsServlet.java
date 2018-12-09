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

@WebServlet("/noone/shop/goods/add")
public class AddGoodsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		int shopid = obj.getIntValue("id");
		String name = obj.getString("name");
		String money = obj.getString("money");
		int classify = obj.getIntValue("classify");
		
		MenuDao menuDao = (MenuDao) DaoFactory.getInstance("goodsDao");
		Menu menu = new Menu();
		menu.setClassify(classify);
		menu.setName(name);
		menu.setRestaurantid(shopid);
		menu.setPrice(money);
		menu = menuDao.save(menu);
		
		String result = "no";
		if(menu!=null){
			result = menu.getId()+"";
			menu.setImg("http://192.168.0.102:8080/NoOneShop/Upload/goods" + menu.getId() + ".png");
			menuDao.update(menu);
		}
		
		response.getOutputStream().write(result.toString().getBytes("utf-8"));
	}

}
