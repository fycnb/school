package com.example.nooneschool.home.list;

public class ShopList {
	private String id;
	private String name;
	private String address;
	private String send;
	private String delivery;
	private String sale;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSend() {
		return send;
	}
	public void setSend(String send) {
		this.send = send;
	}
	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public String getSale() {
		return sale;
	}
	public void setSale(String sale) {
		this.sale = sale;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public ShopList(String id, String name, String address, String send, String delivery, String sale, String imgurl) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.send = send;
		this.delivery = delivery;
		this.sale = sale;
		this.imgurl = imgurl;
	}

}
