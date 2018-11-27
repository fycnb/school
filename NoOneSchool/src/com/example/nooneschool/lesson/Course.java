package com.example.nooneschool.lesson;

import java.io.Serializable;

public class Course{
	private Integer courseid;
	private String course_name;
	private String teacher;
	private String class_room;
	private Integer day;
	private Integer class_start;
	private Integer class_end;

	public Course() {
		super();
	}


	public Course(Integer courseid, String course_name, String teacher, String class_room, Integer day,
			Integer class_start, Integer class_end) {
		super();
		this.courseid = courseid;
		this.course_name = course_name;
		this.teacher = teacher;
		this.class_room = class_room;
		this.day = day;
		this.class_start = class_start;
		this.class_end = class_end;
	}


	public Integer getCourseid() {
		return courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid = courseid;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getClass_room() {
		return class_room;
	}

	public void setClass_room(String class_room) {
		this.class_room = class_room;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getClass_start() {
		return class_start;
	}

	public void setClass_start(Integer class_start) {
		this.class_start = class_start;
	}

	public Integer getClass_end() {
		return class_end;
	}

	public void setClass_end(Integer class_end) {
		this.class_end = class_end;
	}

}
