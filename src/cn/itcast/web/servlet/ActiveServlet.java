package cn.itcast.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.service.UserService;
/**
 * 激活账户
 * @author samsung
 *
 */
public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String activecode= request.getParameter("activecode");
		
		
		//将激活账户传递给业务层  完成账户的激活
		UserService service= new UserService();
		service.active(activecode);
		
		//通知用户 账户激活成功
		response.getWriter().println("账户激活成功");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
