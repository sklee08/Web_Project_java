package com.ssafy.happyhouse.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.HouseDeal;
import com.ssafy.happyhouse.model.dto.HouseInfo;
import com.ssafy.happyhouse.model.dto.HousePageBean;
import com.ssafy.happyhouse.util.DBUtil;

public class HouseinfoDaoImpl implements HouseInfoDao {
	private Map<String, HouseInfo> houseInfo;
	private int size;
	private List<HouseInfo> search;
	private String[] searchType = { HouseDeal.APT_DEAL, HouseDeal.APT_RENT, HouseDeal.HOUSE_DEAL,
			HouseDeal.HOUSE_RENT };

	public static void main(String[] args) {
		HouseInfoDao dao = new HouseinfoDaoImpl();

		try {
			// HouseDeal deal = dao.search(1);
			// System.out.println(deal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<HouseInfo> searchAllHouseInfo(Connection con, HousePageBean bean) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// con = DBUtil.getConnection();
			// and type in (1, 2)
			StringBuilder sql = new StringBuilder(100);
			sql.append("select * from HouseInfo where 1=1");

			String dong = bean.getDong();
			String aptName = bean.getAptname();
			if (dong != null && !dong.trim().equals("")) {
				sql.append(" and dong like ?");
			} else if (aptName != null && !aptName.trim().equals("")) {
				sql.append(" and aptname like ? ");
			}

			pstmt = con.prepareStatement(sql.toString());
			if (dong != null && !dong.trim().equals("")) {
				pstmt.setString(1, "%" + dong + "%");
			} else if (aptName != null && !aptName.trim().equals("")) {
				pstmt.setString(1, "%" + aptName + "%");
			}
			System.out.println(sql.toString());

			rs = pstmt.executeQuery();
			List<HouseInfo> list = new LinkedList<>();
			while (rs.next()) {
				HouseInfo deal = new HouseInfo();
				deal.setNo(rs.getInt("no"));
				deal.setDong(rs.getString("dong"));
				deal.setAptName(rs.getString("aptName"));
				deal.setCode(rs.getInt("code"));
				deal.setBuildYear(rs.getInt("buildYear"));
				deal.setJibun(rs.getString("jibun"));
				deal.setLat(rs.getString("lat"));
				deal.setLat(rs.getString("lng"));

				list.add(deal);
			}
			return list;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			// DBUtil.close(con);
		}
	}

	@Override
	public HouseInfo search(Connection con, String dong, String aptName) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// con = DBUtil.getConnection();
			StringBuilder sql = new StringBuilder(100);
			sql.append("select * from HouseInfo where");
			sql.append(" dong like ?");
			sql.append(" and AptName like ? ");
			System.out.println(sql.toString());
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, dong);
			pstmt.setString(2, aptName);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				HouseInfo deal = new HouseInfo();
				deal.setNo(rs.getInt("no"));
				deal.setDong(rs.getString("dong"));
				deal.setAptName(rs.getString("AptName"));
				deal.setCode(rs.getInt("code"));
				deal.setBuildYear(rs.getInt("buildYear"));
				deal.setJibun(rs.getString("jibun"));
				deal.setLat(rs.getString("lat"));
				deal.setLng(rs.getString("lng"));
				System.out.println("sql문 결과 : " + deal.toString());
				return deal;
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			// DBUtil.close(con);
		}
		return null;
	}

	@Override
	public int insert(Connection con, HouseInfo houseDeal) throws SQLException {
		StringBuilder sb = new StringBuilder("insert HouseInfo ");
		sb.append("(dong,AptName,code,buildYear,jibun,lat,lng)").append("values(?,?,?,?,?,?,?)");

		PreparedStatement pstmt = null;
		int result = 0;
		try {

			// 인서트 할 임시 데이터
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, houseDeal.getDong());
			pstmt.setString(2, houseDeal.getAptName());
			pstmt.setInt(3, houseDeal.getCode());
			pstmt.setInt(4, houseDeal.getBuildYear());
			pstmt.setString(5, houseDeal.getJibun());
			pstmt.setString(6, houseDeal.getLat());
			pstmt.setString(7, houseDeal.getLng());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();// 로깅
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
		return result;
	}

	@Override
	public int update(Connection con, int no, HouseInfo houseDeal) throws SQLException {
		StringBuilder sb = new StringBuilder("update HouseInfo ");
		sb.append("set dong=?,AptName=?,code=?,buildYear=?" + ",jibun=?,lat=?,lng=?").append("where no=?");

		PreparedStatement pstmt = null;
		int result = 0;
		try {
			// 인서트 할 임시 데이터
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, houseDeal.getDong());
			pstmt.setString(2, houseDeal.getAptName());
			pstmt.setInt(3, houseDeal.getCode());
			pstmt.setInt(4, houseDeal.getBuildYear());
			pstmt.setString(5, houseDeal.getJibun());
			pstmt.setString(6, houseDeal.getLat());
			pstmt.setString(7, houseDeal.getLng());
			pstmt.setInt(8, no);

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();// 로깅
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
		return result;
	}

	@Override
	public int delete(Connection con, int no) throws SQLException {
		StringBuilder sb = new StringBuilder("delete from HouseInfo ");
		sb.append("where no = ?");

		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, no);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();// 로깅
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
		return result;
	}

}
