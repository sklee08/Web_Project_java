package com.ssafy.happyhouse.model.service;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.HappyHouseException;
import com.ssafy.happyhouse.model.dao.HouseDao;
import com.ssafy.happyhouse.model.dao.HouseDaoImpl;
import com.ssafy.happyhouse.model.dto.HouseDeal;
import com.ssafy.happyhouse.model.dto.HousePageBean;
import com.ssafy.happyhouse.util.DBUtil;

public class HouseServiceImpl implements HouseService {
	private HouseDao dao;

	public HouseServiceImpl() {
		dao = new HouseDaoImpl();
	}
	private static HouseServiceImpl service = new HouseServiceImpl();
	public static HouseServiceImpl getService() {
		return service;
	}	
	/**
	 * 검색 조건(key) 검색 단어(word)에 해당하는 아파트 거래 정보(HouseInfo)를 검색해서 반환.
	 * 
	 * @param bean 검색 조건과 검색 단어가 있는 객체
	 * @return 조회한 식품 목록
	 */
	
	public int getTotal() throws SQLException{
		Connection con = null;
		int cnt = 0;
		try {
			con = DBUtil.getConnection();
			HousePageBean bean = new HousePageBean();
			cnt = dao.getTotalCount(con, bean);
		}finally {
			DBUtil.close(con);
		}
		
		return cnt;
	}
	
	public List<HouseDeal> searchAll(HousePageBean bean) {
		Connection con = null;
		try {
		 
			con = DBUtil.getConnection();
			con.setAutoCommit(false);			 

			return dao.searchAll(con, bean);
		} catch (SQLException e) {
			DBUtil.rollback(con);

			throw new HappyHouseException(e+ "주택 정보 조회 중 오류 발생");

		} finally {
			DBUtil.close(con);		
		}
	}

	/**
	 * 아파트 식별 번호에 해당하는 아파트 거래 정보를 검색해서 반환.
	 * 
	 * @param no 검색할 아파트 식별 번호
	 * @return 아파트 식별 번호에 해당하는 아파트 거래 정보를 찾아서 리턴한다, 없으면 null이 리턴됨
	 */
	public HouseDeal search(int no) {
		Connection con = null;

		try {
			con = DBUtil.getConnection();
			 con.setAutoCommit(false);
			HouseDeal deal = dao.search(con, no);

			DBUtil.commit(con);

			if (deal == null) {
				throw new HappyHouseException(String.format("거래번호 %d번에 해당하는 주택거래 정보가 존재하지 않습니다.", no));
			}
			return deal;
		} catch (SQLException e) {
			// DBUtil.rollback(con);
			throw new HappyHouseException("주택 정보 조회 중 오류 발생");
		} finally {
			DBUtil.close(con);
		}
	}

	@Override
	public int insert(HouseDeal deal) {
		Connection con = null;

		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			int result = dao.insert(con, deal);

			DBUtil.commit(con);

			if (result == -1) { //에러나면
				throw new HappyHouseException(String.format("HouseDeal잘못입력했습니다" ));
			}
			return result;
		} catch (Exception e) {
			 DBUtil.rollback(con);
			throw new HappyHouseException("HouseDealinsert중 오류발생");
		} finally {
			DBUtil.close(con);
		}
	}

	@Override
	public int update(int no, HouseDeal deal) {
		Connection con = null;

		try {
			con = DBUtil.getConnection();
			con.setAutoCommit(false);
			int result = dao.update(con, no, deal);

			DBUtil.commit(con);

			if (result == -1) { //에러나면
				throw new HappyHouseException(String.format("HouseDeal잘못입력했습니다" ));
			}
			return result;
		} catch (Exception e) {
			 DBUtil.rollback(con);
			throw new HappyHouseException("HouseDealupdate중 오류발생");
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
				throw new HappyHouseException(String.format("HouseDeal잘못입력했습니다" ));
			}
			return result;
		} catch (Exception e) {
			 DBUtil.rollback(con);
			throw new HappyHouseException("HouseDealdelete중 오류발생");
		} finally {
			DBUtil.close(con);
		}
	}
	@Override
	public List<HouseDeal> searchByDong(HousePageBean bean, String dong) {
		Connection con = null;
		try {
		 
			con = DBUtil.getConnection();
			con.setAutoCommit(false);			 

			return dao.searchByDong(con, dong);
		} catch (SQLException e) {
			DBUtil.rollback(con);
			throw new HappyHouseException("주택 정보 조회 중 오류 발생");
		} finally {
			DBUtil.close(con);
		}
	}
	@Override
	public List<HouseDeal> searchByAptName(HousePageBean bean, String AptName) {
		Connection con = null;
		try {
		 
			con = DBUtil.getConnection();
			con.setAutoCommit(false);			 

			return dao.searchByAptName(con, AptName);
		} catch (SQLException e) {
			DBUtil.rollback(con);
			throw new HappyHouseException("주택 정보 조회 중 오류 발생");
		} finally {
			DBUtil.close(con);
		}
	}

}