package com.example.nooneschool.home;

public class ListMeal {
	private String id;
	private String name;
	private String money;
	private String address;
	private String sale;
	private String imgname;
	private String imgurl;

	private Boolean isNoData = false;
	private int mHeight;
	
    
	public Boolean getIsNoData() {
		return isNoData;
	}
	public void setIsNoData(Boolean isNoData) {
		this.isNoData = isNoData;
	}
	public int getmHeight() {
		return mHeight;
	}
	public void setmHeight(int mHeight) {
		this.mHeight = mHeight;
	}
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
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSale() {
		return sale;
	}
	public void setSale(String sale) {
		this.sale = sale;
	}
	
	public String getImgname() {
		return imgname;
	}
	public void setImgname(String imgname) {
		this.imgname = imgname;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	
	public ListMeal(String id, String name, String money, String address, String sale, String imgname, String imgurl) {
		super();
		this.id = id;
		this.name = name;
		this.money = money;
		this.address = address;
		this.sale = sale;
		this.imgname = imgname;
		this.imgurl = imgurl;
	}
	public ListMeal() {
		// TODO Auto-generated constructor stub
	}
	
	
}
