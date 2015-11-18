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
		//获得收货人信息
		String receiverinfo = request.getParameter("receiverinfo");
		//将订单信息保存Orders和OrderItem的Javabean对象中
		Order order = new Order();//一个订单
		List<Orderitem> orderitems = new ArrayList<Orderitem>();//很多订单项
		
		order.setReceiverinfo(receiverinfo);//收货人
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		order.setUser_id(loginUser.getId());//用户编号
		
		//获得购物车对象
		Map<Product,Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
		int totalmoney = 0;
		for(Map.Entry<Product, Integer> entry:cart.entrySet()){
			//累加总价
			totalmoney +=entry.getKey().getPrice()*entry.getValue();
			//购物车中一个键值对-----OrderItem对象
			Orderitem orderitem = new Orderitem();//订单项
			orderitem.setProduct_id(entry.getKey().getId());//商品编号
			orderitem.setBuynum(entry.getValue());//购买数量
			orderitem.setMoney(entry.getKey().getPrice()*entry.getValue());//单项总价
			orderitems.add(orderitem);
		}
		order.setTotalmoney(totalmoney);//封装总价到orders
		
		//订单  和订单项   对应关系 --- 一个订单  包含  订单项
		order.setOrderItems(orderitems);//订单对象中   引用   订单项   list集合
		
		
		//传递订单对象给业务层
		OrderService  orderService = new OrderService();
		orderService.addOrder(order);
		//清空购物车
		request.getSession().removeAttribute("cart");
		
		//给用户提升
		response.getWriter().println("订单已经提交！<a href='/listOrders'>查看订单</a>");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
