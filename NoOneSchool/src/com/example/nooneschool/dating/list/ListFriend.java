package com.example.nooneschool.dating.list;

public class ListFriend {
	private String id;
	private String name;
	private String imgurl;
	
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
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public ListFriend(String id, String name, String imgurl) {
		super();
		this.id = id;
		this.name = name;
		this.imgurl = imgurl;
	}
	
	
}
