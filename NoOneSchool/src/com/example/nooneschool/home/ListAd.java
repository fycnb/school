package com.example.nooneschool.home;

public class ListAd {
	private String id;
	private String imgurl;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public ListAd(String id, String imgurl) {
		super();
		this.id = id;
		this.imgurl = imgurl;
	}
		
	
}
