package com.example.nooneschool.home.list;

public class MenuList {
	private String id;
	private String name;
	private String imgurl;
	private Float money;
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
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public MenuList(String id, String name, String imgurl, Float money) {
		super();
		this.id = id;
		this.name = name;
		this.imgurl = imgurl;
		this.money = money;
	}
	
}
