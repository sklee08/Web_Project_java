package com.ssafy.happyhouse.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.HouseDeal;
import com.ssafy.happyhouse.model.dto.HouseInfo;
import com.ssafy.happyhouse.model.dto.HousePageBean;
import com.ssafy.happyhouse.util.DBUtil;

public class HouseDaoImpl implements HouseDao {
	private Map<String, HouseInfo> houseInfo;
	private Map<String, List<HouseDeal>> deals;
	private int size;
	private List<HouseDeal> search;
	private String[] searchType = { HouseDeal.APT_DEAL, HouseDeal.APT_RENT, HouseDeal.HOUSE_DEAL,
			HouseDeal.HOUSE_RENT };

	public HouseDaoImpl() {
	}

	/**
	 * 아파트 정보와 아파트 거래 정보를 xml 파일에서 읽어온다.
	 */
	public void loadData() {
	}

	/**
	 * 검색 조건(key) 검색 단어(word)에 해당하는 아파트 거래 정보(HouseInfo)를 검색해서 반환.
	 * 
	 * @param bean 검색 조건과 검색 단어가 있는 객체
	 * @return 조회한 식품 목록
	 */
	
	public int getTotalCount(Connection con, HousePageBean bean) throws SQLException {
        int cnt = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

    try {
        conn = DBUtil.getConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("select count(no) \n");
        sql.append("from housedeal");

        System.out.println(sql);

        pstmt = con.prepareStatement(sql.toString());
        rs = pstmt.executeQuery();
        rs.next();
        cnt = rs.getInt(1);
    } finally {
        DBUtil.close(rs);
        DBUtil.close(pstmt);
        DBUtil.close(conn);
    }
    return cnt;
}
	
	public List<HouseDeal> searchAll(Connection con, HousePageBean bean) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			int pageNum = bean.getPageNo();

			StringBuilder sql = new StringBuilder(100);
			sql.append("select * from housedeal limit ?,10");		
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, (pageNum-1) * 10);
			rs = pstmt.executeQuery();
			List<HouseDeal> list = new LinkedList<>();
			while (rs.next()) {
				HouseDeal deal = new HouseDeal();
				deal.setNo(rs.getInt("no"));
				deal.setDong(rs.getString("dong"));
				deal.setAptName(rs.getString("aptName"));
				deal.setCode(rs.getInt("code"));
				deal.setDealAmount(rs.getString("dealAmount"));
				deal.setBuildYear(rs.getInt("buildYear"));
				deal.setDealYear(rs.getInt("dealYear"));
				deal.setDealMonth(rs.getInt("dealMonth"));
				deal.setDealDay(rs.getInt("dealDay"));
				deal.setArea(rs.getDouble("area"));
				deal.setJibun(rs.getString("jibun"));
				deal.setType(rs.getString("type"));
				deal.setRentMoney(rs.getString("rentMoney"));

				list.add(deal);
			}
			list.sort(new Comparator<HouseDeal>() {

				@Override
				public int compare(HouseDeal o1, HouseDeal o2) {
					String s1 = o1.getAptName();
					String s2 = o2.getAptName();
					return s1.compareTo(s2);
				}
			});
			return list;
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			
		}
	}

	/**
	 * 주택 거래 식별 번호에 해당하는 아파트 거래 정보를 검색해서 반환한다.<br/>
	 * 
	 * @param no 검색할 아파트 식별 번호
	 * @return 아파트 식별 번호에 해당하는 아파트 거래 정보를 찾아서 리턴한다, 없으면 null이 리턴됨
	 */
	public HouseDeal search(Connection con, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			String sql = "select * from housedeal where no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				HouseDeal deal = new HouseDeal();
				deal.setNo(rs.getInt("no"));
				deal.setDong(rs.getString("dong"));
				deal.setAptName(rs.getString("aptName"));
				deal.setCode(rs.getInt("code"));
				deal.setDealAmount(rs.getString("dealAmount"));
				deal.setBuildYear(rs.getInt("buildYear"));
				deal.setDealYear(rs.getInt("dealYear"));
				deal.setDealMonth(rs.getInt("dealMonth"));
				deal.setDealDay(rs.getInt("dealDay"));
				deal.setArea(rs.getDouble("area"));
				deal.setJibun(rs.getString("jibun"));
				deal.setType(rs.getString("type"));
				deal.setRentMoney(rs.getString("rentMoney"));
				return deal;
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			// DBUtil.close(con);
		}
		return null;
	}

	public static void main(String[] args) {
		HouseDao dao = new HouseDaoImpl();

		try {
			// HouseDeal deal = dao.search(1);
			// System.out.println(deal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insert(Connection con, HouseDeal houseDeal) throws SQLException {
		StringBuilder sb = new StringBuilder("insert housedeal ");
		sb.append("(dong,AptName,code,dealAmount,buildYear,dealYear,dealMonth,\r\n"
				+ "dealDay,area,floor,jibun,type,rentMoney)").append("values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

		PreparedStatement pstmt = null;
		int result = 0;
		try {

			// 인서트 할 임시 데이터
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, houseDeal.getDong());
			pstmt.setString(2, houseDeal.getAptName());
			pstmt.setInt(3, houseDeal.getCode());
			pstmt.setString(4, houseDeal.getDealAmount());
			pstmt.setInt(5, houseDeal.getBuildYear());
			pstmt.setInt(6, houseDeal.getDealYear());
			pstmt.setInt(7, houseDeal.getDealMonth());
			pstmt.setInt(8, houseDeal.getDealDay());
			pstmt.setDouble(9, houseDeal.getArea());
			pstmt.setInt(10, houseDeal.getFloor());
			pstmt.setString(11, houseDeal.getJibun());
			pstmt.setString(12, houseDeal.getType());
			pstmt.setString(13, houseDeal.getRentMoney());

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
	public int update(Connection con, int no, HouseDeal houseDeal) throws SQLException {
		StringBuilder sb = new StringBuilder("update housedeal ");
		sb.append("set dong=?,AptName=?,code=?,dealAmount=?,buildYear=?," + "dealYear=?,"
				+ "dealMonth=?,dealDay=?,area=?,floor=?,jibun=?,type=?,rentMoney=?").append("where no=?");

		PreparedStatement pstmt = null;
		int result = 0;
		try {
			// 업데이트 할 임시 데이터
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, houseDeal.getDong());
			pstmt.setString(2, houseDeal.getAptName());
			pstmt.setInt(3, houseDeal.getCode());
			pstmt.setString(4, houseDeal.getDealAmount());
			pstmt.setInt(5, houseDeal.getBuildYear());
			pstmt.setInt(6, houseDeal.getDealYear());
			pstmt.setInt(7, houseDeal.getDealMonth());
			pstmt.setInt(8, houseDeal.getDealDay());
			pstmt.setDouble(9, houseDeal.getArea());
			pstmt.setInt(10, houseDeal.getFloor());
			pstmt.setString(11, houseDeal.getJibun());
			pstmt.setString(12, houseDeal.getType());
			pstmt.setString(13, houseDeal.getRentMoney());
			pstmt.setInt(14, no);

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
		StringBuilder sb = new StringBuilder("delete from housedeal ");
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

	@Override
	public List<HouseDeal> searchByDong(Connection con, String dong) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// con = DBUtil.getConnection();
			StringBuilder sql = new StringBuilder(100);
			sql.append("select * from HouseDeal where 1=1");

			 
			if (dong != null && !dong.trim().equals("")) {
				sql.append(" and dong like ?");
			}  
			System.out.println("sql문..." + sql.toString());
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+dong+"%");

			rs = pstmt.executeQuery();
			List<HouseDeal> list = new LinkedList<>();
			while (rs.next()) {
				HouseDeal deal = new HouseDeal();
				deal.setNo(rs.getInt("no"));
				deal.setDong(rs.getString("dong"));
				deal.setAptName(rs.getString("aptName"));
				deal.setCode(rs.getInt("code"));
				deal.setDealAmount(rs.getString("dealAmount"));
				deal.setBuildYear(rs.getInt("buildYear"));
				deal.setDealYear(rs.getInt("dealYear"));
				deal.setDealMonth(rs.getInt("dealMonth"));
				deal.setDealDay(rs.getInt("dealDay"));
				deal.setArea(rs.getDouble("area"));
				deal.setJibun(rs.getString("jibun"));
				deal.setType(rs.getString("type"));
				deal.setRentMoney(rs.getString("rentMoney"));

				list.add(deal);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			// DBUtil.close(con);
		}
		return null;
	}

	@Override
	public List<HouseDeal> searchByAptName(Connection con, String AptName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// con = DBUtil.getConnection();
			StringBuilder sql = new StringBuilder(100);
			sql.append("select * from HouseDeal where 1=1");

			 
			if (AptName != null && !AptName.trim().equals("")) {
				sql.append(" and AptName like ?");
			}  
			System.out.println("sql문..." + sql.toString());
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+AptName+"%");

			rs = pstmt.executeQuery();
			List<HouseDeal> list = new LinkedList<>();
			while (rs.next()) {
				HouseDeal deal = new HouseDeal();
				deal.setNo(rs.getInt("no"));
				deal.setDong(rs.getString("dong"));
				deal.setAptName(rs.getString("aptName"));
				deal.setCode(rs.getInt("code"));
				deal.setDealAmount(rs.getString("dealAmount"));
				deal.setBuildYear(rs.getInt("buildYear"));
				deal.setDealYear(rs.getInt("dealYear"));
				deal.setDealMonth(rs.getInt("dealMonth"));
				deal.setDealDay(rs.getInt("dealDay"));
				deal.setArea(rs.getDouble("area"));
				deal.setJibun(rs.getString("jibun"));
				deal.setType(rs.getString("type"));
				deal.setRentMoney(rs.getString("rentMoney"));

				list.add(deal);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			// DBUtil.close(con);
		}
		return null;
	}

}