package com.zh.controller;

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
@MultipartConfig(location = "H:\\Tomcat\\apache-tomcat-7.0.82\\webapps\\NoOneSchoolService\\images")
public class UploadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = "head.png";
		Part part = req.getPart("file");

		part.write(fileName);

		resp.setCharacterEncoding("utf-8");
		PrintWriter pw = resp.getWriter();
		pw.print("upload success");
		System.out.println("upload success");
	}
}
