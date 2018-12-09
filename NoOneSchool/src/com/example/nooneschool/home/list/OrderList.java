package com.example.nooneschool.home.list;

import java.io.Serializable;

public class OrderList implements Serializable{
	private String id;
	private int number;
	private float momey;
	private String name;
	private String img;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void addNumber() {
		this.number += 1;
	}
	public void minusNumber() {
		this.number -= 1;
	}
	public float getMomey() {
		return momey;
	}
	public void setMomey(float momey) {
		this.momey = momey;
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
	public OrderList(String id, int number, float momey, String name, String img) {
		super();
		this.id = id;
		this.number = number;
		this.momey = momey;
		this.name = name;
		this.img = img;
	}
		
}
