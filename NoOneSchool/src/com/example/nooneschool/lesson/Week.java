package com.example.nooneschool.lesson;

public class Week {
	private Integer week;
	private Integer courseid;
	
	
	
	
	public Week() {
		super();
	}
	public Week(Integer week, Integer courseid) {
		super();
		this.week = week;
		this.courseid = courseid;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Integer getCourseid() {
		return courseid;
	}
	public void setCourseid(Integer courseid) {
		this.courseid = courseid;
	}
	
	
}
