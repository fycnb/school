package com.example.nooneshop.list;

public class RankList {
	private int number;
	private String name;
	private String img;
	public int getNumebr() {
		return number;
	}
	public void setNumebr(int number) {
		this.number = number;
	}
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
	public RankList(int number, String name, String img) {
		super();
		this.number = number;
		this.name = name;
		this.img = img;
	}

	

}
