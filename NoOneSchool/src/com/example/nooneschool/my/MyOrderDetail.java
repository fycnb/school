package com.example.nooneschool.my;

public class MyOrderDetail {
	private String image;
	private String name;
	private String number;
	private String total;

	public MyOrderDetail(String image, String name, String number, String total) {
		super();
		this.image = image;
		this.name = name;
		this.number = number;
		this.total = total;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}
