package com.example.nooneshop.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.example.nooneshop.MyApplication;
import com.example.nooneshop.utils.StreamTools;

public class LoginService {
	private static String ip = MyApplication.ip;

	public static String LoginServiceByPost(String account, String password, String homepath) {
		try {

			JSONObject json = new JSONObject();
			json.put("account", account);
			json.put("password", password);
			String content = String.valueOf(json);

			String path = "http://" + ip + ":8080/" + homepath;

			URL url = new URL(path);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(2000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("accept", "application/json");
			conn.setRequestProperty("ser-Agent", "Fiddler");

			OutputStream os = conn.getOutputStream();
			os.write(content.getBytes());
			os.close();

			int code = conn.getResponseCode();

			if (code == 200) {

				InputStream is = conn.getInputStream();
				String text = StreamTools.readInputStream(is);

				return text;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String RegisterServiceByPost(String phone, String password, String name, String address, String homepath) {
		try {

			JSONObject json = new JSONObject();
			json.put("password", password);
			json.put("name", name);
			json.put("address", address);
			json.put("phone", phone);
			String content = String.valueOf(json);

			String path = "http://" + ip + ":8080/" + homepath;

			URL url = new URL(path);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(2000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("accept", "application/json");
			conn.setRequestProperty("ser-Agent", "Fiddler");

			OutputStream os = conn.getOutputStream();
			os.write(content.getBytes());
			os.close();

			int code = conn.getResponseCode();

			if (code == 200) {

				InputStream is = conn.getInputStream();
				String text = StreamTools.readInputStream(is);

				return text;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
