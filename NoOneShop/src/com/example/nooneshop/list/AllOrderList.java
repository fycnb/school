package com.example.nooneshop.list;

import java.util.List;

public class AllOrderList {
	private String orderid;
	private String time;
	private int state;
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getState() {
		String s = "未知";
		switch (state) {
		case 0:
			s = "待接单";
			break;
		case 1:
			s = "等待骑手";
			break;
		case 2:
			s = "已出餐";
			break;
		case 3:
			s = "配送中";
			break;
		case 4:
			s = "已完成";
			break;
		case -1:
			s = "已取消";
			break;
		}
		return s;
	}
	public void setState(int state) {
		this.state = state;
	}
	public AllOrderList(String orderid, String time, int state) {
		super();
		this.orderid = orderid;
		this.time = time;
		this.state = state;
	}
	
}
