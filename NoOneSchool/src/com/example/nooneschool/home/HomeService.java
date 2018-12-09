package com.example.nooneschool.home;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.example.nooneschool.util.StreamTools;

public class HomeService {

	private static String ip = "192.168.0.107";

//	public static String HomeServiceByPost(int number, String type, String homepath) {
//		try {
//
//			JSONObject json = new JSONObject();
//			json.put("type", type);
//			json.put("number", number);
//			String content = String.valueOf(json);
//
//			String path = "http://" + ip + ":8080/" + homepath;
//
//			URL url = new URL(path);
//
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setConnectTimeout(2000);
//			conn.setRequestMethod("POST");
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//			conn.setRequestProperty("accept", "application/json");
//			conn.setRequestProperty("ser-Agent", "Fiddler");
//
//			OutputStream os = conn.getOutputStream();
//			os.write(content.getBytes());
//			os.close();
//
//			int code = conn.getResponseCode();
//
//			if (code == 200) {
//
//				InputStream is = conn.getInputStream();
//				String text = StreamTools.readInputStream(is);
//
//				return text;
//			} else {
//				return null;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public static String AdServiceByPost(int type) {
		try {
			JSONObject json = new JSONObject();
			json.put("type", type);
			String content = String.valueOf(json);
			String path = "http://" + ip + ":8080/NoOneShop/noone/school/ad/get";

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

	public static String ShopServiceByPost() {
		try {

			String path = "http://" + ip + ":8080/NoOneShop/noone/school/shop/get";

			URL url = new URL(path);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(2000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("accept", "application/json");
			conn.setRequestProperty("ser-Agent", "Fiddler");

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
	
	public static String FoodsServiceByPost(int type) {
		try {

			JSONObject json = new JSONObject();
			json.put("type", type);
			String content = String.valueOf(json);

			String path = "http://" + ip + ":8080/NoOneShop/noone/school/foods/get";

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
	
	public static String FoodsServiceByPost(String id,int type) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("type", type);
			String content = String.valueOf(json);

			String path = "http://" + ip + ":8080/NoOneShop/noone/school/shop/foods";

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
	
	public static String ShopInfoServiceByPost(String id) {
		try {
			JSONObject json = new JSONObject();
			json.put("id", id);
			String content = String.valueOf(json);
			String path = "http://" + ip + ":8080/NoOneShop/noone/school/shop/info";

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
	
	public static String CommentServiceByPost(String id) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", id);
			String content = String.valueOf(json);

			String path = "http://" + ip + ":8080/NoOneShop/noone/school/shop/comment";

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

	public static String OrderServiceByPost(String id, String order, String memo, String address) {
		try {

			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("order", order);
			json.put("memo", memo);
			json.put("address", address);
			String content = String.valueOf(json);

			String path = "http://" + ip + ":8080/NoOneShop/noone/school/shop/order";

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

	public static String SearchServiceByPost(String string) {
		try {

			JSONObject json = new JSONObject();
			json.put("string", string);
			String content = String.valueOf(json);

			String path = "http://" + ip + ":8080/NoOneShop/noone/school/search";

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
