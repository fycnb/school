package com.zh.controller;

import java.io.IOException;
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
import com.zh.Dao.DetailDao;
import com.zh.Dao.OrderDao;
import com.zh.Dao.RestaurantDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.Detail;
import com.zh.entity.Indent;
import com.zh.entity.Restaurant;
import com.zh.utils.JsonUtil;

@WebServlet("/MyOrder")
public class MyOrderServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		StringBuffer sb = JsonUtil.getjson(req);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		String userid = obj.getString("userid");
		String sta = obj.getString("state");
		
		resp.setContentType("text/html");
		JSONArray js = null;
		String sql = null;
		List<Indent> orderlist = null;
		
		OrderDao orderDao = (OrderDao) DaoFactory.getInstance("orderDao");
		
		if(sta.equals("all")){
			sql = "select * from indent where userid = ? order by state";
			orderlist = orderDao.find(sql,userid);
			js = getdata(orderlist);
		}else{
			sql = "select * from indent where userid = ? and state = ?";
			orderlist = orderDao.find(sql,userid,sta);
			js = getdata(orderlist);
		}
			
		String content = String.valueOf(js);
		resp.getOutputStream().write(content.getBytes("utf-8"));
	}
	
	private JSONArray getdata(List<Indent> orderlist){
		JSONArray js = new JSONArray();
		for(Indent order : orderlist){
			JSONObject json = new JSONObject();
			Long orderid = order.getId();
			int restaurantid = order.getRestaurantid();
			Date t = order.getTime();
			int st = order.getState();
			String memo = order.getMemo();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			String time = sdf.format(t);
			
			String state = "";
			switch (st) {
			case 1:
				state = "待接单";
				break;
			case 2:
				state = "已接单";
				break;
			case 3:
				state = "待评价";
				break;
			case 4:
				state = "已完成";
				break;
			case 5:
				state = "已取消";
				break;
			case 6:
				state = "已拒绝";
				break;
			default:
				break;
			}
			
			DetailDao detailDao = (DetailDao) DaoFactory.getInstance("detailDao");
			String sql1 = "select price,number from detail where orderid = ?";
			List<Detail> detaillist = detailDao.find(sql1,orderid);
			int total = 0;
			for(Detail detail : detaillist){
				int price = Integer.parseInt(detail.getPrice());
				int number = detail.getNumber();
				total += price*number;
			}
			
			RestaurantDao restaurantDao = (RestaurantDao) DaoFactory.getInstance("restaurantDao");
			Restaurant restaurant = restaurantDao.findOne(new Long(restaurantid));
			String name = restaurant.getName();
			String image = restaurant.getImage();
			String iphone = restaurant.getIphone();
			
			json.put("total",total);
			json.put("orderid",orderid);
			json.put("time",time);
			json.put("state",state);
			json.put("image",image);
			json.put("name",name);
			json.put("memo",memo);
			json.put("iphone",iphone);
			js.add(json);
		} 
		return js;
	}

}
