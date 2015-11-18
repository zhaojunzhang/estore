package cn.itcast.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domin.Order;
import cn.itcast.domin.Orderitem;
import cn.itcast.domin.Product;
import cn.itcast.domin.User;
import cn.itcast.service.OrderService;

public class AddOrdersServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//����ջ�����Ϣ
		String receiverinfo = request.getParameter("receiverinfo");
		//��������Ϣ����Orders��OrderItem��Javabean������
		Order order = new Order();//һ������
		List<Orderitem> orderitems = new ArrayList<Orderitem>();//�ܶඩ����
		
		order.setReceiverinfo(receiverinfo);//�ջ���
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		order.setUser_id(loginUser.getId());//�û����
		
		//��ù��ﳵ����
		Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
		int totalmoney = 0;
		for(Map.Entry<Product, Integer> entry:cart.entrySet()){
			//�ۼ��ܼ�
			totalmoney +=entry.getKey().getPrice()*entry.getValue();
			//���ﳵ��һ����ֵ��-----OrderItem����
			Orderitem orderitem = new Orderitem();//������
			orderitem.setProduct_id(entry.getKey().getId());//��Ʒ���
			orderitem.setBuynum(entry.getValue());//��������
			orderitem.setMoney(entry.getKey().getPrice()*entry.getValue());//�����ܼ�
			orderitems.add(orderitem);
		}
		order.setTotalmoney(totalmoney);//��װ�ܼ۵�orders
		
		//����  �Ͷ�����   ��Ӧ��ϵ --- һ������  ����  ������
		order.setOrderItems(orderitems);//����������   ����   ������   list����
		
		
		//���ݶ��������ҵ���
		OrderService  orderService = new OrderService();
		orderService.addOrder(order);
		//��չ��ﳵ
		request.getSession().removeAttribute("cart");
		
		//���û�����
		response.getWriter().println("�����Ѿ��ύ��<a href='/listOrders'>�鿴����</a>");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
