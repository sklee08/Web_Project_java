package com.ssafy.happyhouse.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssafy.happyhouse.model.dto.UserInfo;
import com.ssafy.happyhouse.model.service.UserInfoServiceImpl;


/**
 * Servlet implementation class UserController
 */
@WebServlet("/user.do")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // act: 실제 하고 싶은 일
        String act = request.getParameter("act");

        // 분기는 크게 2종류: 로직이 타는것, 단순 페이지 전환
        String root = request.getContextPath();
        try {
        	if ("login".equals(act)) {
                login(request, response);
            }else if ("register".equals(act)) {
                register(request, response);
            }else if ("mvreguser".equals(act)) {
                response.sendRedirect(root + "/view/registerUser.jsp");
            }else if("logout".equals(act)) {
            	logout(request, response);        	
            }else if("mvlist".equals(act)) {
            	mvlist(request,response);
            }else if("delete".equals(act)) {
            	delete(request, response);
            }else if("mvupdate".equals(act)) {
            	mvupdate(request, response);
            }else if("update".equals(act)) {
            	update(request,response);
            }else if("mvfindpw".equals(act)) {
            	response.sendRedirect(root + "/view/findpw.jsp");
            }else if("findpw".equals(act)) {
            	findpw(request,response);
            }

        }catch(SQLException e) {
        	// 에러 페이지 연결
        	RequestDispatcher disp = request.getRequestDispatcher("/view/error.jsp");
            request.setAttribute("msg", e.getMessage());
        	disp.forward(request, response);

        }
        
    }
	
	private void mvupdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
    	
    	try {
        	String id = request.getParameter("id");
        	UserInfo ui = UserInfoServiceImpl.getService().select(id);

        	request.setAttribute("userinfo", ui);
            RequestDispatcher disp = request.getRequestDispatcher("/view/userupdate.jsp");
            disp.forward(request, response);

        }catch(SQLException e) {
        	throw e;
        }
    }
	
	private void mvlist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
    	
    	try {
        	String id = request.getParameter("id");
        	UserInfo ui = UserInfoServiceImpl.getService().select(id);

        	request.setAttribute("userinfo", ui);
            RequestDispatcher disp = request.getRequestDispatcher("/view/showlist.jsp");
            disp.forward(request, response);

        }catch(SQLException e) {
        	throw e;
        }
    }
	
	private void findpw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		try {
			String id = request.getParameter("id");
        	String name = request.getParameter("name");
        	String address = request.getParameter("address");
        	String phone = request.getParameter("phone");
        	UserInfo ui = UserInfoServiceImpl.getService().select(id);
        	
        	if(ui != null && (ui.getName().equals(name) && ui.getAddress().equals(address) && ui.getPhone().equals(phone))) {
        		request.setAttribute("userinfo", ui);
        		
        	}else {
        		request.setAttribute("userinfo", null);
        	}
        	RequestDispatcher disp = request.getRequestDispatcher("/view/showpw.jsp");
            disp.forward(request, response);
        }catch(SQLException e) {
        	throw e;
        }
    }
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
    	// 삭제 성공시 리스트 페이지 이동
    	try {
        	String id = request.getParameter("id");
        	UserInfoServiceImpl.getService().delete(id);
        	
        	response.sendRedirect(request.getContextPath()+"/user.do?act=logout");
        }catch(Exception e) {
        	throw e;
        }
    }
	
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
    	
    	try {
        	String id = request.getParameter("id");
        	String pass = request.getParameter("pass");
        	String name = request.getParameter("name");
        	String address = request.getParameter("address");
        	String phone = request.getParameter("phone");
        	
        	UserInfoServiceImpl.getService().updateInfo(new UserInfo(id, pass, name, address, phone));
        	

        	response.sendRedirect(request.getContextPath()+"/view/main.jsp");
        }catch(Exception e) {
        	throw e;
        }
    }
	
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	session.invalidate();
    	
    	response.sendRedirect(request.getContextPath()+"/view/main.jsp");
    }
	
	
	
	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String id = request.getParameter("id");
    	String pass = request.getParameter("pass");
    	String name = request.getParameter("name");
    	String address = request.getParameter("address");
    	String phone = request.getParameter("phone");
    	
        UserInfo user = new UserInfo(id, pass, name, address, phone);

        try {
        	if(UserInfoServiceImpl.getService().register(user)) {
                // 등록성공
        		RequestDispatcher disp = request.getRequestDispatcher("/view/main.jsp");
                disp.forward(request, response);
        	}else {
        		// 등록실패
        	}
        }catch(Exception e) {
        	e.printStackTrace();
        	throw e;
        }        
    }

    // sub controller
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 웹과 관련된 처리
        String id = request.getParameter("id");
        String pass = request.getParameter("pass");
        // 2. 웹과 무관한 비지니스 로직 연결 - Model
        try {
            UserInfo selected = UserInfoServiceImpl.getService().login(new UserInfo(id, pass));
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
        String path = "/view/main.jsp";
        RequestDispatcher disp = request.getRequestDispatcher(path);
        disp.forward(request, response);
    }
}
