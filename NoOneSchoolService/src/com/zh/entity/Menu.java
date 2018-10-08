package com.zh.entity;

import com.zh.entity.common.Entity;

public class Menu extends Entity {
	private int restaurantid;
	private String image;
	private String foodname;
	private String price;
	private int sale;

	public int getRestaurantid() {
		return restaurantid;
	}

	public void setRestaurantid(int restaurantid) {
		this.restaurantid = restaurantid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getSale() {
		return sale;
	}

	public void setSale(int sale) {
		this.sale = sale;
	}

}
