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
import com.noone.Dao.DetailDao;
import com.noone.Dao.OrderDao;
import com.noone.Dao.SenderDao;
import com.noone.Dao.common.DaoFactory;
import com.noone.common.JsonUtil;
import com.noone.entity.AllOrder;
import com.noone.entity.Detail;
import com.noone.entity.Sender;

@WebServlet("/noone/shop/order/getone")
public class GetOneOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuffer sb = JsonUtil.getjson(request);
		JSONObject obj = JSONObject.parseObject(sb.toString());
		Long orderid = obj.getLong("id");

		DetailDao detailDao = (DetailDao) DaoFactory.getInstance("detailDao");
		List<Detail> detail = detailDao.findByOrderId(orderid);
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
		response.getOutputStream().write(js2.toString().getBytes("utf-8"));
	}

}
