package com.ssafy.happyhouse.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

/**
 * 클래스를 메모리에 로딩시키는 방법 
 * 1. 객체 생성    		=> JVM에서 사용하는 ClassLoader를 이용해서 클래스를 로딩 
 * 2. static에 접근      => JVM에서 사용하는 ClassLoader를 이용해서 클래스를 로딩
 * 3. Class.forName("패지키를 포함한 클래스이름"); => JVM에서 사용하는 ClassLoader를 이용해서 클래스를 로딩
 * 3. ClassLoader를 통해 직접 로딩. 
 */
public class DBUtil {
	static final String url = "jdbc:mysql://127.0.0.1:3306/ssafydb?useSSL=false&serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
    static final String driver = "com.mysql.cj.jdbc.Driver";
    static final String id = "root";
    static final String password = "dltjrrb1";
	
	static {
		try {
			Class.forName(driver);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, id, password);
	}
	
	public static void close(AutoCloseable c) {
		try {
			c.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void commit(Connection con) {
		if(con != null) {
			try {
				con.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void rollback(Connection con) {
		if(con != null) {
			try {
				con.setAutoCommit(true);
				con.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}










