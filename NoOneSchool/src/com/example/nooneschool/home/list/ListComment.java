package com.example.nooneschool.home.list;

public class ListComment {
	private String id;
	private String name;
	private String content;
	private String time;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public ListComment(String id, String name, String content, String time, String imgurl) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.time = time;
		this.imgurl = imgurl;
	}

}
