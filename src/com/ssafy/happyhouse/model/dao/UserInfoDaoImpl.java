package com.ssafy.happyhouse.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.happyhouse.model.dto.UserInfo;
import com.ssafy.happyhouse.util.DBUtil;

public class UserInfoDaoImpl implements UserInfoDao{

	
	
	private static UserInfoDaoImpl dao = new UserInfoDaoImpl();
	
	public static UserInfoDaoImpl getDao() {
		return dao;
	}
	
	private UserInfoDaoImpl() {}
	
	@Override
	public boolean insert(Connection con, UserInfo ui) throws SQLException {
		PreparedStatement stmt = null;
		int result = 0;
		try {
			StringBuilder sql = new StringBuilder(100);
			sql.append("insert into user_info ")
			.append("(id,pw,name, address, phone) values (?,?,?,?,?)");
			
			stmt = con.prepareStatement(sql.toString());
			
			stmt.setString(1, ui.getId());
			stmt.setString(2, ui.getPw());
			stmt.setString(3, ui.getName());
			stmt.setString(4, ui.getAddress());
			stmt.setString(5, ui.getPhone());
			result = stmt.executeUpdate();
		} finally {
			// TODO: handle finally clause
			DBUtil.close(stmt);
		}
		if(result != 0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean update(Connection con, UserInfo ui) throws SQLException {
		StringBuilder sb = new StringBuilder("update user_info ");
		sb.append("set id=?, pw=?, name=?, address=?, phone=?")
		.append("where id=?");
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, ui.getId());
			pstmt.setString(2, ui.getPw());
			pstmt.setString(3, ui.getName());
			pstmt.setString(4, ui.getAddress());
			pstmt.setString(5, ui.getPhone());
			pstmt.setString(6, ui.getId());
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(pstmt);
		}
		
		if(result == 0) {
			return false;
		}else {
			return true;
		}
		
	}

	@Override
	public boolean delete(Connection con, String id) throws SQLException {
		StringBuilder sb = new StringBuilder("delete from user_info ");
		sb.append("where id=?");
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, id);			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(pstmt);
		}
		
		if(result == 0) {
			return false;
		}else {
			return true;
		}
		
	}

	@Override
	public UserInfo select(Connection con, String id) throws SQLException {
		String cmd = "select * from user_info where id=?";
		
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		UserInfo ui = null;
		
		try {
			pstmt = con.prepareStatement(cmd);
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				ui = new UserInfo();
				String pw = rset.getString(2);
				String name = rset.getString(3);
				String address = rset.getString(4);
				String phone = rset.getString(5);
				
				ui.setAddress(address);
				ui.setId(id);
				ui.setName(name);
				ui.setPhone(phone);
				ui.setPw(pw);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(pstmt);
		}
		
		return ui;
		
	}

	public List<UserInfo> searchAll(Connection con) throws SQLException{
		String cmd = "select * from user_info";
		
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<UserInfo> list = new ArrayList<UserInfo>();
		
		try {
			pstmt = con.prepareStatement(cmd);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				UserInfo ui = new UserInfo();
				String id = rset.getString(1);
				String pw = rset.getString(2);
				String name = rset.getString(3);
				String address = rset.getString(4);
				String phone = rset.getString(5);
				
				ui.setAddress(address);
				ui.setId(id);
				ui.setName(name);
				ui.setPhone(phone);
				ui.setPw(pw);
				list.add(ui);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(pstmt);
		}
		
		
		return list;
	}

}
