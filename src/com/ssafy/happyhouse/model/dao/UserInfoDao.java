package com.ssafy.happyhouse.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.dto.UserInfo;

public interface UserInfoDao {

	
	public boolean insert(Connection con, UserInfo ui) throws SQLException;
	
	public boolean update(Connection con, UserInfo ui) throws SQLException;
	
	public boolean delete(Connection con, String id) throws SQLException;
	
	public UserInfo select(Connection con, String id) throws SQLException;
	
	public List<UserInfo> searchAll(Connection con) throws SQLException;
	
}
