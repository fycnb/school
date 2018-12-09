package com.noone.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.noone.Dao.common.DaoException;

public class DBHelper {

	private static final Properties props = new Properties();
	private static DataSource ds;

	private DBHelper() {
	}

	static {

		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));

			DruidDataSource dds = new DruidDataSource();
			dds.setDriverClassName(props.getProperty("driverClass"));
			dds.setUrl(props.getProperty("url"));
			dds.setUsername(props.getProperty("user"));
			dds.setPassword(props.getProperty("password"));
			dds.configFromPropety(props);
			dds.init();

			ds = dds;
		} catch (IOException e) {
			System.err.println("在classpath下未找到jdbc.properties配置文件！！！");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Druid连接池初始化出现异常，请检查jdbc.properties配置文件！！！");
			e.printStackTrace();
		}
	}

	public static Connection getConn() throws DaoException {
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new DaoException(DaoException.CODE_CONNECTION_FAIL, "获取数据库的连接失败", e);
		}

		return conn;
	}
}
