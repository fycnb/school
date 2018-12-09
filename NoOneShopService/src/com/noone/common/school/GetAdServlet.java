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
import com.noone.Dao.AdDao;
import com.noone.Dao.DetailDao;
import com.noone.Dao.OrderDao;
import com.noone.Dao.RestaurantDao;
import com.noone.Dao.SenderDao;
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.Ad;
import com.noone.entity.AllOrder;
import com.noone.entity.Detail;
import com.noone.entity.Restaurant;
import com.noone.entity.Sender;

@WebServlet("/noone/school/ad/get")
public class GetAdServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		AdDao adDao = (AdDao) DaoFactory.getInstance("adDao");
		List<Ad> list = adDao.findAll();
		JSONArray js = new JSONArray();
		
		for (int i = 0;i<list.size();i++) {
			JSONObject json = new JSONObject();
			Long id = list.get(i).getId();
			String imgurl = list.get(i).getImg();
			String url = list.get(i).getUrl();
			
			json.put("id", id);
			json.put("imgurl", imgurl);
			json.put("url", url);
			js.add(json);
		}
		
		response.getOutputStream().write(js.toString().getBytes("utf-8"));
	}

}