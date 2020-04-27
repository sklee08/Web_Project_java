package com.ssafy.happyhouse.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.happyhouse.model.dto.HouseDeal;
import com.ssafy.happyhouse.model.dto.HouseInfo;
import com.ssafy.happyhouse.model.dto.HousePageBean;
import com.ssafy.happyhouse.model.service.HouseInfoServiceImpl;
import com.ssafy.happyhouse.model.service.HouseServiceImpl;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/search")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 request.setCharacterEncoding("utf-8");
		try {
			process(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

	// 업무에 대한 분기 처리
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,SQLException {
		// act: 실제 하고 싶은 일
		String act = request.getParameter("act");

		// 분기는 크게 2종류: 로직이 타는것, 단순 페이지 전환
		String root = request.getContextPath();
		// String path = "/index.jsp";

		// System.out.println("넘어왔당 root : " + root);
		// System.out.println("path : " + path);

		if ("search_deal".equals(act)) {
			// 길잡이 끝 --> sub controller에게 전달
			searchAll(request, response);
		} else if ("search".equals(act)) {
			search(request, response);
		} else if ("detail".equals(act)) {
			detail(request, response);
		}else if("page".equals(act)) {
			searchAll(request, response);
		}

	}

	private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("searchController에서 detail 메소드 동작중..");

		HouseInfo houseInfo = new HouseInfo(); 
		
	 	String s = " "; //db에 동 문자가 공백문자+동이름 이렇게 들어가서..
	 //	String dong = s.concat(request.getParameter("dong"));
	 	String dong =  request.getParameter("dong") ;
	 	String aptName = request.getParameter("aptName");
	 	System.out.println("디테일 페이지...: " + dong + " " + aptName);
	 	
	 	 
	 	houseInfo = HouseInfoServiceImpl.getService().search( dong, aptName); 
		
	 	
		// 이미지 세팅하기  
	 	// 문자열 패턴 검색
		String path = getServletContext().getRealPath("img");
		File fileDir = new File(path);
		String files[] = fileDir.list();
		for (String fNm : files) {
		 
			StringTokenizer st = new StringTokenizer(fNm, ".");
			  String [] names = new String[st.countTokens()];
		 
			  int i = 0;
			  while(st.hasMoreElements()){
				  names[i++] = st.nextToken();
			  }
			 
			if(aptName.contains(names[0])) {
				houseInfo.setImg(fNm);
				System.out.println(fNm);
				break;
			}else {
				houseInfo.setImg("다세대주택.jpg");
			}  
		}
 
	 
		request.setAttribute("house_detail", houseInfo);

		RequestDispatcher disp = request.getRequestDispatcher("view/search_deal_detail.jsp");
		disp.forward(request, response);
	}

	private void searchAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		System.out.println("searchController에서 searchAll 메소드 동작중..");
		List<HouseDeal> houseDeals = null;
		HousePageBean bean = new HousePageBean();
		bean.setInterval(5);
		bean.setStart(1);
		bean.setEnd(5);
		int page = 1;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		bean.setPageNo(page);
		
		bean.setTotalCount(HouseServiceImpl.getService().getTotal());
		houseDeals = HouseServiceImpl.getService().searchAll(bean);
						
		request.setAttribute("houseDeals", houseDeals);
		request.setAttribute("paging", bean);
		

		request.setAttribute("houseDeals_size", houseDeals.size());
		System.out.println("req houseDeals:  " + houseDeals.size());
		RequestDispatcher disp = request.getRequestDispatcher("view/search_deal.jsp");
		disp.forward(request, response);
	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Collection<HouseDeal> houseDeals = null;
		HousePageBean bean = new HousePageBean();
		String by = request.getParameter("by");
		String keyword = request.getParameter("keyword");
		System.out.println("조회 파라미터 확인: " + by + " : " + keyword);
		if (by.equals("dongName")) {
			bean.setDong(keyword);
			houseDeals = HouseServiceImpl.getService().searchByDong(bean, keyword);
		} else if (by.equals("aptName")) {
			bean.setAptname(keyword);
			houseDeals = HouseServiceImpl.getService().searchByAptName(bean, keyword);
		}

		request.setAttribute("houseDeals", houseDeals);
		System.out.println("req houseDeals:  " + houseDeals.size());
		System.out.println(request.getAttribute("houseDeals"));
		RequestDispatcher disp = request.getRequestDispatcher("view/search_deal.jsp");
		disp.forward(request, response);
	}
}
