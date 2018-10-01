package com.zh.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.zh.Dao.CommentDao;
import com.zh.Dao.OrderDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.Comment;
import com.zh.utils.JsonUtil;

@WebServlet("/PublishComment")
public class PublishCommentServlet extends HttpServlet{
	
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
		String uid = obj.getString("userid");
		String oid = obj.getString("orderid");
		String content = obj.getString("con");
		String rating = obj.getString("rating");

		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String t = sdf.format(new Date());
		Date time = null;
		try {
			time = sdf.parse(t);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int orderid = Integer.parseInt(oid);
		int userid = Integer.parseInt(uid);
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setOrderid(orderid);
		comment.setRating(rating);
		comment.setUserid(userid);
		comment.setTime(time);
		
		CommentDao commentDao = (CommentDao) DaoFactory.getInstance("commentDao");
		Comment comm = commentDao.save(comment);
		
		resp.setContentType("text/html");
		Long id = comm.getId();
		
		if(commentDao.exists(id)){
			OrderDao orderDao = (OrderDao) DaoFactory.getInstance("orderDao");
			String sql = "update indent set state = 4  where userid = ? and id = ? and state = 3";
			int rs = orderDao.update(sql,userid,orderid);
			System.out.println(rs);
			if(rs > 0){
				resp.getOutputStream().write("感谢您的评价".getBytes("utf-8"));
			}else{
				resp.getOutputStream().write("请稍后评价".getBytes("utf-8"));
			}
		}else{
			resp.getOutputStream().write("请稍后评价".getBytes("utf-8"));
		}
		
	}
}
