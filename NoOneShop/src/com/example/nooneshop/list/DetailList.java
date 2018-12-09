package com.example.nooneshop.list;

public class DetailList {
	private String id;
	private String img;
	private String name;
	private int number;
	private float money;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public DetailList(String id, String img, String name, int number, float money) {
		super();
		this.id = id;
		this.img = img;
		this.name = name;
		this.number = number;
		this.money = money;
	}	
	
	
}
