package com.example.nooneschool.my;


public class MyOrder {
	private String name;
	private String total;
	private String time;
	private String state;
	
	public MyOrder() {
		super();
	}

	public MyOrder(String name, String total, String time, String state) {
		super();
		this.name = name;
		this.total = total;
		this.time = time;
		this.state = state;
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
	
	
	
}
