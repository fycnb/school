package com.noone.common.school;

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
import com.noone.Dao.RestaurantDao;
import com.noone.Dao.SenderDao;
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.AllOrder;
import com.noone.entity.Menu;
import com.noone.entity.Restaurant;
import com.noone.entity.Sender;

@WebServlet("/noone/school/foods/get")
public class GetFoodsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		int type = obj.getIntValue("type");
		
		MenuDao menuDao = (MenuDao) DaoFactory.getInstance("goodsDao");
		List<Menu> list = menuDao.findBytype(type);
		
		JSONArray js = new JSONArray();
		
		for (int i = 0;i<list.size();i++) {
			JSONObject json = new JSONObject();
			Long id = list.get(i).getId();
			String name = list.get(i).getName();
			int shopid = list.get(i).getRestaurantid();
			String money = list.get(i).getPrice();
			String img = list.get(i).getImg(); 
			int sale = list.get(i).getSale(); 
			
			
			json.put("id", id);
			json.put("name", name);
			json.put("money", money);
			json.put("img", img);
			json.put("shopid", shopid);
			json.put("sale", sale);
			js.add(json);
		}
		
		response.getOutputStream().write(js.toString().getBytes("utf-8"));
	}

}
