package com.ssafy.happyhouse.model.service;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.ssafy.happyhouse.model.dao.UserInfoDao;
import com.ssafy.happyhouse.model.dao.UserInfoDaoImpl;
import com.ssafy.happyhouse.model.dto.UserInfo;
import com.ssafy.happyhouse.util.DBUtil;

public class UserInfoServiceImpl implements UserInfoService{

	private static UserInfoServiceImpl service = new UserInfoServiceImpl();
	
	private static UserInfoDaoImpl dao = UserInfoDaoImpl.getDao();
	
	public static UserInfoServiceImpl getService() {
		return service;
	}
	
	private UserInfoServiceImpl() {
		
	}
	
	@Override
	public UserInfo login(UserInfo user) throws Exception{
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			UserInfo selected = null;
			selected = dao.select(con, user.getId());
			if(selected == null) return null;
			if(selected.getPw().equals(user.getPw())) {
				// 비밀번호 동일
				return selected;
			}else {
				// 비밀번호 틀림
				// 에러 페이지 발생필요
				throw new Exception("비밀번호 틀렸습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(con);
		}
		return null;
	}

	@Override
	public boolean register(UserInfo ui) {
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			if(dao.insert(con, ui)) {
				return true;
			}
			con.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(con);
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			if(dao.delete(con, id)) {
				
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(con);
		}
		return false;
	}

	@Override
	public boolean updateInfo(UserInfo user) {
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			if(dao.update(con, user)) {
				return true;				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {	
			DBUtil.close(con);
		}
		return false;
	}
	
	public UserInfo select(String id) throws SQLException{
		Connection con = null;
		UserInfo ui = null;
		try{
			// 1. connection 획득
			con = DBUtil.getConnection();
			// 2. transaction 준비
			ui = dao.select(con, id);
			
		}catch (SQLException e) {
			e.printStackTrace();	// 로깅
			throw e;	// 전파 controller
		}finally {
			// 정리 작업
			DBUtil.close(con);			
		}
		
		return ui;
	}

	public Collection<UserInfo> list() throws SQLException{
		Connection con = null;
		List<UserInfo> list = null;
		try{
			// 1. connection 획득
			con = DBUtil.getConnection();
			// 2. transaction 준비
			list = dao.searchAll(con);
			
		}catch (SQLException e) {
			e.printStackTrace();	// 로깅
			throw e;	// 전파 controller
		}finally {
			// 정리 작업
			DBUtil.close(con);			
		}
		
		return list;
	}
	
}
