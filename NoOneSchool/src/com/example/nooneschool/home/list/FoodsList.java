package com.example.nooneschool.home.list;

public class FoodsList {
	private String id;
	private String name;
	private String address;
	private String imgurl;
	private String money;
	private String sale;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getSale() {
		return sale;
	}
	public void setSale(String sale) {
		this.sale = sale;
	}
	public FoodsList(String id, String name, String address, String imgurl, String money, String sale) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.imgurl = imgurl;
		this.money = money;
		this.sale = sale;
	}
}
