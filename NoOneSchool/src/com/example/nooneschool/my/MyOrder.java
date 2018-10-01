package com.example.nooneschool.my;

public class MyOrder {
	private String name;
	private String total;
	private String time;
	private String state;
	private String image;
	private String orderid;
	private String memo;
	private String iphone;

	public MyOrder(String name, String total, String time, String state, String image, String orderid, String memo,
			String iphone) {
		super();
		this.name = name;
		this.total = total;
		this.time = time;
		this.state = state;
		this.image = image;
		this.orderid = orderid;
		this.memo = memo;
		this.iphone = iphone;
	}

	public String getIphone() {
		return iphone;
	}

	public void setIphone(String iphone) {
		this.iphone = iphone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
