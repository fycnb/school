package com.example.nooneshop.list;

public class GoodsList {
	private String id;
	private String name;
	private String img;
	private int classify;
	private String money;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getClassifyNumber() {
		return classify;
	}
	public String getClassify() {
		switch (classify) {
		case 1:
			return "盖饭";
		case 2:
			return "面食";
		case 3:
			return "小吃";
		case 4:
			return "其他";
		}
		return "未知";
	}
	public void setClassify(int classify) {
		this.classify = classify;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public GoodsList(String id, String name, String img, int classify, String money) {
		super();
		this.id = id;
		this.name = name;
		this.img = img;
		this.classify = classify;
		this.money = money;
	}
	
	
}
