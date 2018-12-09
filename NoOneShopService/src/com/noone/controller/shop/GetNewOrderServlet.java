package com.noone.controller.shop;

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
import com.noone.Dao.SenderDao;
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.AllOrder;
import com.noone.entity.Detail;
import com.noone.entity.Sender;

@WebServlet("/noone/shop/order/get")
public class GetNewOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		int shopid = obj.getIntValue("id");
		OrderDao orderDao = (OrderDao) DaoFactory.getInstance("orderDao");
		List<AllOrder> list = orderDao.findByShopId(shopid);
		SenderDao senderDao = (SenderDao) DaoFactory.getInstance("senderDao");
		List<Sender> sender = senderDao.findAll();
		DetailDao detailDao = (DetailDao) DaoFactory.getInstance("detailDao");
		
		JSONArray js = new JSONArray();
		
		for (int i = 0;i<list.size();i++) {
			JSONObject json = new JSONObject();
			Long id = list.get(i).getId();
			int state = list.get(i).getState();
			Date time = list.get(i).getTime();
			String sendername = "暂无";
			String memo = list.get(i).getMemo();
			SimpleDateFormat sdFormat = new SimpleDateFormat("MM-dd hh:mm");
			
			if(list.get(i).getTakerid()!=0){
				for (int j = 0; j < sender.size(); j++) {
					if (list.get(i).getTakerid()==sender.get(j).getId()) {
						sendername = sender.get(j).getName();
						break;
					}
				}
			}
			List<Detail> detail = detailDao.findByOrderId(id);
			JSONArray js2 = new JSONArray();
			for (int j = 0;j<detail.size();j++) {
				JSONObject json2 = new JSONObject();
				json2.put("id", detail.get(j).getId());
				json2.put("img", detail.get(j).getImg());
				json2.put("name", detail.get(j).getName());
				json2.put("number", detail.get(j).getNumber());
				json2.put("money", detail.get(j).getPrice());
				js2.add(json2);
			}
			String detail1 = js2.toString();
			json.put("id", id);
			json.put("memo", memo);
			json.put("time", sdFormat.format(time));
			json.put("detail", detail1);
			json.put("sender", sendername);
			json.put("state", state);
			js.add(json);
		}
		
		response.getOutputStream().write(js.toString().getBytes("utf-8"));
	}

}
