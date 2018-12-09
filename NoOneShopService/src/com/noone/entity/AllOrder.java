package com.noone.entity;

import java.util.Date;

import com.noone.entity.common.Entity;

public class AllOrder extends Entity{
	private int userid;
	private int restaurantid;
	private int takerid;
	private String address;
	private String iphone;
	private Date time;
	private int state;
	private String memo;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getRestaurantid() {
		return restaurantid;
	}
	public void setRestaurantid(int restaurantid) {
		this.restaurantid = restaurantid;
	}
	public int getTakerid() {
		return takerid;
	}
	public void setTakerid(int takerid) {
		this.takerid = takerid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIphone() {
		return iphone;
	}
	public void setIphone(String iphone) {
		this.iphone = iphone;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
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
	
}
