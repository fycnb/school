package com.example.nooneshop.list;

import java.util.List;

public class OrderList {
	private String orderid;
	private String sender;
	private int number;
	private float money;
	private int state;
	private String memo;
	private String time;
	private List<DetailList> list;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getNumber() {
		number = 0;
		for (int i = 0; i < list.size(); i++) {
			number += list.get(i).getNumber();
		}
		return number;
	}

	public float getMoney() {
		money = 0;
		for (int i = 0; i < list.size(); i++) {
			money += list.get(i).getMoney()*list.get(i).getNumber();
		}
		return money;
	}

	public List<DetailList> getList() {
		return list;
	}

	public void setList(List<DetailList> list) {
		this.list = list;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public OrderList(String orderid,String memo, String sender, String time, int state, List<DetailList> list) {
		super();
		this.orderid = orderid;
		this.memo = memo;
		this.sender = sender;
		this.state = state;
		this.time = time;
		this.list = list;
	}

}
