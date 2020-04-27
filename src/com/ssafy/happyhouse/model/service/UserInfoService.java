package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.Collection;

import com.ssafy.happyhouse.model.dto.UserInfo;

public interface UserInfoService { 
	
	public UserInfo select(String id) throws SQLException;
	
	public UserInfo login(UserInfo user) throws Exception;
		
	public boolean register(UserInfo ui);
		
	public boolean delete(String id);
	
	
	public boolean updateInfo(UserInfo user);
	
	public Collection<UserInfo> list() throws SQLException;

}
