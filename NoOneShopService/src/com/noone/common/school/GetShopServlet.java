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
import com.noone.Dao.OrderDao;
import com.noone.Dao.RestaurantDao;
import com.noone.Dao.SenderDao;
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.AllOrder;
import com.noone.entity.Detail;
import com.noone.entity.Restaurant;
import com.noone.entity.Sender;

@WebServlet("/noone/school/shop/get")
public class GetShopServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RestaurantDao restaurantDao = (RestaurantDao) DaoFactory.getInstance("shopDao");
		List<Restaurant> list = restaurantDao.findGoodShop();
		JSONArray js = new JSONArray();
		
		for (int i = 0;i<list.size();i++) {
			JSONObject json = new JSONObject();
			Long id = list.get(i).getId();
			String name = list.get(i).getName();
			String address = list.get(i).getAddress();
			int send = list.get(i).getSend();
			int delivery = list.get(i).getDelivery();
			int sale = list.get(i).getSale();
			String imgurl = list.get(i).getImg();
			
			json.put("id", id);
			json.put("name", name);
			json.put("address", address);
			json.put("send", send);
			json.put("delivery", delivery);
			json.put("sale", sale);
			json.put("imgurl", imgurl);
			js.add(json);
		}
		
		response.getOutputStream().write(js.toString().getBytes("utf-8"));
	}

}
