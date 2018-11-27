package com.example.nooneschool.home.list;

public class ListFood {
	private String id;
	private String name;
	private String address;
	private String send;
	private String delivery;
	private String imgurl;
	private String momey;

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

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getMomey() {
		return momey;
	}

	public void setMomey(String momey) {
		this.momey = momey;
	}

	public ListFood(String id, String name, String address, String send, String delivery, String imgurl, String momey) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.send = send;
		this.delivery = delivery;
		this.imgurl = imgurl;
		this.momey = momey;
	}

}
