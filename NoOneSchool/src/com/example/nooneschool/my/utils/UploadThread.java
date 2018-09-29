package com.example.nooneschool.my.utils;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.content.Entity;
import android.os.Environment;

public  class UploadThread extends Thread {
	
	private String fileName;
	private String url;
	
	public UploadThread(String url,String fileName){
		this.url = url;
		this.fileName = fileName;
		
	}
	@SuppressWarnings("deprecation")
	public void run(){
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		MultipartEntity muti = new MultipartEntity();
//		File parent = Environment.getExternalStorageDirectory();
//		File fileAbs = new File(parent,"head.png");
		File file = new File(fileName);
		FileBody fileBody = new FileBody(file);
		muti.addPart("file",fileBody);	
		post.setEntity(muti);
		try {
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					System.out.print(EntityUtils.toString(response.getEntity()));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
