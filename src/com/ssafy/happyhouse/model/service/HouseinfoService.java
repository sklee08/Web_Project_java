package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.dto.HouseInfo;
import com.ssafy.happyhouse.model.dto.HousePageBean;
 

public interface HouseinfoService {
	public List<HouseInfo> searchAll(HousePageBean bean);

	/**
	 * 아파트 식별 번호에 해당하는 아파트 거래 정보를 검색해서 반환.
	 * 
	 * @param no 검색할 아파트 식별 번호
	 * @return 아파트 식별 번호에 해당하는 아파트 거래 정보를 찾아서 리턴한다, 없으면 null이 리턴됨
	 */
	public HouseInfo search(String dong, String aptName);

	public int insert(  HouseInfo houseDeal);

	public int update(int no, HouseInfo houseDeal);

	public int delete(int no);
}
