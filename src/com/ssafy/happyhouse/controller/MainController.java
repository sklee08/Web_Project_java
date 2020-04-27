package com.ssafy.happyhouse.controller;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssafy.happyhouse.model.dto.UserInfo;
import com.ssafy.happyhouse.model.service.UserInfoServiceImpl; 
 

/**
 * Servlet implementation class login
 */
@WebServlet("/main.do")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			process(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 필요한 전처리
		request.setCharacterEncoding("utf-8");
		try {
			process(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 업무에 대한 분기 처리
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// act: 실제 하고 싶은 일
		String act = request.getParameter("act");

		// 분기는 크게 2종류: 로직이 타는것, 단순 페이지 전환
		String root = request.getContextPath();
		// String path = "/index.jsp";

		// System.out.println("넘어왔당 root : " + root);
		// System.out.println("path : " + path);

		if ("login".equals(act)) {
			// 길잡이 끝 --> sub controller에게 전달
			login(request, response);
		} else if ("logout".equals(act)) {
			logout(request, response);
		}   else if ("search_deal".equals(act)) {// 이름검색, 가격검색
			search_deal(request, response);
		}  

	}

	 

	private void search_deal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		
		SearchController searchController = new SearchController();
		System.out.println("searchController로 넘어간다");
		searchController.doGet(request, response); 
		
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		String path = "view/index.jsp";
		RequestDispatcher disp = request.getRequestDispatcher(path);
		disp.forward(request, response);
	}

 

	// sub controller
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 웹과 관련된 처리
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		// 2. 웹과 무관한 비지니스 로직 연결 - Model
		try {
			UserInfo selected = UserInfoServiceImpl.getService().login(new UserInfo(id,pw));
			if (selected == null) { // 사용자가 없는 경우 - 로그인 페이지에 정보 전달
				request.setAttribute("msg", "아이디/비밀번호를 확인하세요.");
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("userinfo", selected);
			}
		} catch (Exception e) {
			// 로깅   
			e.printStackTrace();
			// 전파
			request.setAttribute("msg", e.getMessage());
		}
		// 3. 결과를 화면에 전송 - View(JSP)
		String path = "view/index.jsp";
		RequestDispatcher disp = request.getRequestDispatcher(path);
		disp.forward(request, response);
	}

	 
}
