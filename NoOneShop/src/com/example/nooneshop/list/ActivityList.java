package com.example.nooneshop.list;

public class ActivityList {
	private String activityid;
	private int type;
	private int state;
	private String startime;
	private String endtime;
	private String context;
	
	
	public String getStartime() {
		return startime;
	}
	public void setStartime(String startime) {
		this.startime = startime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getActivityid() {
		return activityid;
	}
	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}
	public int getTypeNumber() {
		return type;
	}
	public String getType() {
		String tp = "代币";
		switch (type) {
		case 0:
			tp = "未知";
		case 1:
			tp = "代币";
			break;
		case 2:
			tp = "降价";
			break;
		case 3:
			tp = "赠送";
			break;
		}
		return tp;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStateNumber() {
		return state;
	}
	public String getState() {
		String tp = "未开启";
		switch (state) {
		case 0:
			tp = "未开启";
			break;
		case 2:
			tp = "已开启";
			break;
		case 1:
			tp = "审核中";
			break;
		}
		return tp;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public ActivityList(String activityid, int type, int state, String startime, String endtime, String context) {
		super();
		this.activityid = activityid;
		this.type = type;
		this.state = state;
		this.startime = startime;
		this.endtime = endtime;
		this.context = context;
	}
		
}
