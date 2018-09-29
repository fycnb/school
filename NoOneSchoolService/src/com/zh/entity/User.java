package com.zh.entity;

import com.zh.entity.common.Entity;

public class User extends Entity{
	private String account;
	private String password;
	private String nickname;
	private String head;
	private int sobo;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSobo() {
		return sobo;
	}
	public void setSobo(int sobo) {
		this.sobo = sobo;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	
	
}
