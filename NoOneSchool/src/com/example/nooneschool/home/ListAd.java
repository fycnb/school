package com.example.nooneschool.home;

public class ListAd {
	private String id;
	private String name;
	private byte[] imgbit;
	private String imgurl;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public byte[] getImgbit() {
		return imgbit;
	}
	public void setImgbit(byte[] imgbit) {
		this.imgbit = imgbit;
	}
	
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public ListAd(String id, String name, byte[] imgbit, String imgurl) {
		super();
		this.id = id;
		this.name = name;
		this.imgbit = imgbit;
		this.imgurl = imgurl;
	}
		
	
}
