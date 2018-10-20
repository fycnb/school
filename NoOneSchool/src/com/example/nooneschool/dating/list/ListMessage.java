package com.example.nooneschool.dating.list;

public class ListMessage {
	private String id;
	private String friendid;
	private String send;
	private String read;
	private String content;
	private String time;
	private String name;
	private String imgurl;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFriendid() {
		return friendid;
	}
	public void setFriendid(String friendid) {
		this.friendid = friendid;
	}
	public String getSend() {
		return send;
	}
	public void setSend(String send) {
		this.send = send;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
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
	public ListMessage(String id, String friendid, String send, String read, String content, String time, String name,
			String imgurl) {
		super();
		this.id = id;
		this.friendid = friendid;
		this.send = send;
		this.read = read;
		this.content = content;
		this.time = time;
		this.name = name;
		this.imgurl = imgurl;
	}
	
	
}
