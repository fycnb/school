package com.zh.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.zh.Dao.UserDao;
import com.zh.Dao.common.DaoFactory;
import com.zh.entity.User;

@WebServlet("/Upload")
//@MultipartConfig(location = "H:\\Tomcat\\apache-tomcat-7.0.82\\webapps\\NoOneSchoolService\\images")
@MultipartConfig()
public class UploadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userid = req.getParameter("userid");
	
		Part part = req.getPart("head");
		String path = req.getRealPath("/images");
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		file.createNewFile();
		String name = System.currentTimeMillis() + ".jpg";
		part.write(path +"/"+ name);
		System.out.println("path"+ path + name);
		UserDao userDao = (UserDao) DaoFactory.getInstance("userDao");
		User user = userDao.findOne(Long.parseLong(userid));
		user.setHead(path +"/"+ name);
		userDao.update(user);
//		String fileName = "head.png";
//		Part part = req.getPart("file");
//
//		part.write(fileName);

		resp.setCharacterEncoding("utf-8");
		PrintWriter pw = resp.getWriter();
		pw.print("upload success");
		System.out.println("upload success");
	}
}
