package com.noone.db;

import java.sql.*;

public class DBOper {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public Connection getConn() 
			throws ClassNotFoundException,SQLException,InstantiationException,IllegalAccessException{
			String DRIVER = "com.mysql.jdbc.Driver";
			String URL = "jdbc:mysql://localhost:3306/noone";
			String usename = "root";
			String password = "mysql";
			Class.forName(DRIVER).newInstance();
			conn = DriverManager.getConnection(URL,usename,password);
			return conn;
	}
	
	public void closeALL(){
		if (rs != null){
			try{
				rs.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		if (pstmt != null){
			try{
				pstmt.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		if (conn != null){
			try{
				conn.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public int executeUpdate(String sql, String[] param)  {
		int num = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			if(param != null){
				for(int i=0;i<param.length;i++){
					pstmt.setString(i+1,param[i]);
				}
			}
			num = pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return num;
	}
     

	public ResultSet executeQuery(String sql, String[] param) {
		try{
			pstmt = conn.prepareStatement(sql);
			if(param != null){
				for(int i=0;i<param.length;i++){
					pstmt.setString(i+1,param[i]);
				}
			}
			rs = pstmt.executeQuery();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return rs;
	}                   
}
