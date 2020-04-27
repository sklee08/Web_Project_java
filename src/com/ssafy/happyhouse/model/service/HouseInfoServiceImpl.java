package com.ssafy.happyhouse.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
   
import com.ssafy.happyhouse.HappyHouseException; 
import com.ssafy.happyhouse.model.dao.HouseInfoDao;
import com.ssafy.happyhouse.model.dao.HouseinfoDaoImpl; 
import com.ssafy.happyhouse.model.dto.HouseInfo;
import com.ssafy.happyhouse.model.dto.HousePageBean;
import com.ssafy.happyhouse.util.DBUtil;

public class HouseInfoServiceImpl implements HouseinfoService {
	private HouseInfoDao dao;
	public HouseInfoServiceImpl() {
		dao = new HouseinfoDaoImpl();
	}
	private static HouseInfoServiceImpl service = new HouseInfoServiceImpl();
	public static HouseInfoServiceImpl getService() {
		return service;
	}	
	
	@Override
	public List<HouseInfo> searchAll(HousePageBean bean) {
		Connection con = null;
		try {
			boolean[] types = bean.getSearchType();
			int cnt = 0;
			con = DBUtil.getConnection();
			 
			con.setAutoCommit(false);
			for (boolean t : types) {
				if (t) {
					cnt++;
				}
			}

			if (cnt == 0) {
				throw new HappyHouseException("houseinfo:주택 타입은 반드시 한개 이상을 선택해야합니다");
			}

			return dao.searchAllHouseInfo(con, bean);
		} catch (SQLException e) { 
			DBUtil.rollback(con);
			System.out.println(e);
			throw new HappyHouseException("houseinfo 조회 중 오류 발생");
		} finally {
			DBUtil.close(con);
		}
	}

	@Override
	public HouseInfo search( String dong, String aptName) {
		Connection con = null;

		try {
			con = DBUtil.getConnection();
			 con.setAutoCommit(false);
			HouseInfo deal = dao.search(con,  dong, aptName);

			DBUtil.commit(con);

			if (deal == null) {
				throw new HappyHouseException(String.format(" 주택거래 정보가 존재하지 않습니다."));
			}
			return deal;
		} catch (SQLException e) {
			// DBUtil.rollback(con);
			throw new HappyHouseException("houseinfo 정보 조회 중 오류 발생");
		} finally {
			DBUtil.close(con);
		}
	}

	@Override
	public int insert(  HouseInfo houseDeal) {
		Connection con = null;

		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			int result = dao.insert(con, houseDeal);

			DBUtil.commit(con);

			if (result == -1) { //에러나면
				throw new HappyHouseException(String.format("houseinfo 잘못입력했습니다" ));
			}
			return result;
		} catch (SQLException e) {
			System.out.println(e);
			 DBUtil.rollback(con);
			throw new HappyHouseException("houseinfo insert중 오류발생");
		} finally {
			DBUtil.close(con);
		}
	}

	@Override
	public int update(int no, HouseInfo houseDeal) {
		Connection con = null;

		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			int result = dao.update(con, no, houseDeal);

			DBUtil.commit(con);

			if (result == -1) { //에러나면
				throw new HappyHouseException(String.format("houseinfo잘못입력했습니다" ));
			}
			return result;
		} catch (Exception e) {
			 DBUtil.rollback(con);
			throw new HappyHouseException("houseinfo update중 오류발생");
		} finally {
			DBUtil.close(con);
		}
	}

	@Override
	public int delete(int no) {
		Connection con = null;

		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			int result = dao.delete(con, no);

			DBUtil.commit(con);

			if (result == -1) { //에러나면
				throw new HappyHouseException(String.format("houseinfo잘못입력했습니다" ));
			}
			return result;
		} catch (Exception e) {
			 DBUtil.rollback(con);
			throw new HappyHouseException("houseinfodelete중 오류발생");
		} finally {
			DBUtil.close(con);
		}
	}

}
