package com.ssafy.happyhouse.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.dto.HouseInfo;
import com.ssafy.happyhouse.model.dto.HousePageBean;

public interface HouseInfoDao {

	/** HouseInfo DB에 위도 경도 입력하기 위해서 등록된 모든 집의 동과 지번을 추출한다. */
	public List<HouseInfo> searchAllHouseInfo(Connection con, HousePageBean bean) throws SQLException;

	public HouseInfo search(Connection con, String dong, String aptName) throws SQLException;

	public int insert(Connection con, HouseInfo bean) throws SQLException;

	public int update(Connection con, int no, HouseInfo houseDeal) throws SQLException;

	public int delete(Connection con, int no) throws SQLException;
}
