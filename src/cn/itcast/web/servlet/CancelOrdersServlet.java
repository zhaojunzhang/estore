package cn.itcast.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domin.User;
import cn.itcast.service.OrderService;

public class CancelOrdersServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		
		//���������ݸ�ҵ���
		OrderService orderService = new OrderService();
		orderService.cancelOrder(id,loginUser);
		
		//ɾ��������  ��ת��  �����б�
		response.sendRedirect("listOrders");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
