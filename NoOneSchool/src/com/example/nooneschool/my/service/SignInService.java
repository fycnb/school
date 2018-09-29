package com.example.nooneschool.my.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.example.nooneschool.my.utils.StreamTools;



public class SignInService {
	public static String SignInByPost(String userid){
        try{
            JSONObject json = new JSONObject();
            json.put("userid",userid);
            String content = String.valueOf(json);

            //访问的资源路径
            String path = "http://169.254.96.11:8080/NoOneSchoolService/SignIn?";

            //创建url实例
            URL url = new URL(path);

            //获取HttpURLConnection对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //设置超时对象
            conn.setConnectTimeout(5000);

            //指定请求方式
            conn.setRequestMethod("POST");

            //将数据传给服务器
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //设置请求头
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            conn.setRequestProperty("ser-Agent", "Fiddler");

            //得到输出流
            OutputStream os = conn.getOutputStream();
            os.write(content.getBytes());
            os.close();

            int code = conn.getResponseCode();
            if(code==200){
                //得到服务器返回的输入流
                InputStream is = conn.getInputStream();

                //将输入流转换成字符串
                String text = StreamTools.readInputStream(is);

                return text;
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
