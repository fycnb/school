package com.example.nooneshop.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.example.nooneshop.MyApplication;
import com.example.nooneshop.utils.StreamTools;

public class MyService {
	private static String ip = MyApplication.ip;

	public static String AllOrderServiceByPost(String id, int month, int state, int sequence, String homepath) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("state", state);
			json.put("month", month);
			json.put("sequence", sequence);
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

	public static String OneOrderServiceByPost(String orderid, String homepath) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", orderid);
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

	public static String GoodsServiceByPost(String id, String homepath) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", id);
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

	public static String UpdateGoodsServiceByPost(String id, int state, String homepath) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("state", state);
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

	public static String AddGoodsServiceByPost(String id, String name, int classify, String money,
			String homepath) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("name", name);
			json.put("classify", classify);
			json.put("money", money);
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

	public static String ActivityServiceByPost(String id, String homepath) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", id);
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

	public static String AddActivityServiceByPost(String id, int type, String start, String end, String content_1,
			String homepath) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("type", type);
			json.put("start", start);
			json.put("end", end);
			json.put("content", content_1);
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

	public static String ShopDetailServiceByPost(String id, String homepath) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", id);
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

	public static String EditShopDetailServiceByPost(String id, String name, String address,
			String homepath) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("name", name);
			json.put("address", address);
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
