package com.example.nooneschool.home.list;

public class AdList {
	private String id;
	private String imgurl;
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AdList(String id, String imgurl, String url) {
		super();
		this.id = id;
		this.imgurl = imgurl;
		this.url = url;
	}

}
