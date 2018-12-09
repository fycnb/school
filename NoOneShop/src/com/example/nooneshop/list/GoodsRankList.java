package com.example.nooneshop.list;

public class GoodsRankList {
	private String name;
	private String img;
	private int number;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public GoodsRankList(String name, String img, int number) {
		super();
		this.name = name;
		this.img = img;
		this.number = number;
	}
	
}
