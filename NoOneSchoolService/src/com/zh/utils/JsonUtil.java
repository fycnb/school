package com.zh.utils;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.zh.entity.common.Entity;

public class JsonUtil {
	public static StringBuffer getjson(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		String s = null;
		try {
			BufferedReader br = request.getReader();
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb;
	}

}
