package com.noone.common;

public class Tools {
	public static Boolean isEmpty(String str){
		if(str != null && !str.isEmpty() && str.length()>0){
			return false;
		}
		return true;
	}
}
