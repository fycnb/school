package com.noone.common;

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
		Part part = req.getPart("file");
		String path = req.getRealPath("/Upload");
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		file.createNewFile();
		String name = userid + ".png";
		part.write(path +"/"+ name);
//		String fileName = "head.png";
//		Part part = req.getPart("file");
//
//		part.write(fileName);

		resp.setCharacterEncoding("utf-8");
		PrintWriter pw = resp.getWriter();
		pw.print("upload success");
	}
}
