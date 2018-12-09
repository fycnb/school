package com.noone.common.school;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noone.Dao.DetailDao;
import com.noone.Dao.MenuDao;
import com.noone.Dao.OrderDao;
import com.noone.Dao.RestaurantDao;
import com.noone.Dao.SenderDao;
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.AllOrder;
import com.noone.entity.Detail;
import com.noone.entity.Menu;
import com.noone.entity.Restaurant;
import com.noone.entity.Sender;

@WebServlet("/noone/school/search")
public class GetSearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		String string = obj.getString("string");
		
		RestaurantDao restaurantDao = (RestaurantDao) DaoFactory.getInstance("shopDao");
		List<Restaurant> shoplist = restaurantDao.findBySearch(string);
		MenuDao menuDao = (MenuDao) DaoFactory.getInstance("goodsDao");
		List<Menu> foodslist = menuDao.findBySearch(string);
		
		JSONArray js = new JSONArray();
		
		for (int i = 0;i<shoplist.size();i++) {
			JSONObject json = new JSONObject();
			Long id = shoplist.get(i).getId();
			String name = shoplist.get(i).getName();
			String address = shoplist.get(i).getAddress();
			int send = shoplist.get(i).getSend();
			int delivery = shoplist.get(i).getDelivery();
			int sale = shoplist.get(i).getSale();
			String imgurl = shoplist.get(i).getImg();
			
			json.put("type", "shop");
			json.put("id", id);
			json.put("name", name);
			json.put("address", address);
			json.put("send", send);
			json.put("delivery", delivery);
			json.put("sale", sale);
			json.put("imgurl", imgurl);
			js.add(json);
		}
		
		for (int i = 0;i<foodslist.size();i++) {
			JSONObject json = new JSONObject();
			Long id = foodslist.get(i).getId();
			String name = foodslist.get(i).getName();
			int shopid = foodslist.get(i).getRestaurantid();
			String money = foodslist.get(i).getPrice();
			String img = foodslist.get(i).getImg(); 
			int sale = foodslist.get(i).getSale(); 
			
			json.put("type", "foods");
			json.put("id", id);
			json.put("name", name);
			json.put("money", money);
			json.put("img", img);
			json.put("shopid", shopid);
			json.put("sale", sale);
			js.add(json);
		}
		System.out.println(js.toString());
		response.getOutputStream().write(js.toString().getBytes("utf-8"));
	}

}
