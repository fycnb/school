package com.noone.util;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

public class JsonUtil {
	public static StringBuffer getjson(HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
	    String s = null;
	    try{
	    BufferedReader br = request.getReader();
	        while((s=br.readLine())!=null){
	            sb.append(s);
	        }
		}catch (Exception e){
			e.printStackTrace();
		}
		return sb;
	}
	
}
