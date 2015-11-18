package cn.itcast.service;
/**
 * 业务层
 */

import java.util.List;

import cn.itcast.dao.OrderDAO;
import cn.itcast.domin.Order;
import cn.itcast.domin.Orderitem;
import cn.itcast.domin.User;

public class OrderService {
//生成订单
	public void addOrder(Order order) {
	OrderDAO orderDAO= new OrderDAO();
	orderDAO.insertOrder(order);
		
	}

	public List<Order> listOrders(User loginUser) {
		//判断身份
		if(loginUser.getRole().equals("admin")){//身份是管理员
			//查看所有的订单
			OrderDAO orderDAO = new OrderDAO();
			List<Order> orders= orderDAO.findAll();//这里订单没有订单项
			//根据订单   查询订单项
			for(Order order: orders){
				List<Orderitem> orderitems =orderDAO.findOrderItems(order);
				order.setOrderItems(orderitems);//将订单项  保存到订单
			}
			
			return orders;
		}else if(loginUser.getRole().equals("user")){//身份商城用户
			//查看用户自己的订单
			OrderDAO orderDAO =new OrderDAO();
			List<Order> orders = orderDAO.findOrdersByUser(loginUser);
			
			
			for(Order order:orders){
				List<Orderitem> orderitems =orderDAO.findOrderItems(order);
				order.setOrderItems(orderitems);//将订单项  保存到订单
				
				order.setUser(loginUser);
			}
			
			return orders;
		}
		
		return null;
	}
	
    //取消订单  id　订单编号　　loignUser谁要取消
	public void cancelOrder(String id, User loginUser) {
		//根据  订单编号   ，查询订单号
		
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.findById(id);
		
		if(order==null){
			throw new RuntimeException("您取消的订单编号不存在！");
		}
		// 判断订单是不是已支付
		if(order.getState()==1){
			//订单已经支付
			throw new RuntimeException("订单已支付，无法取消！");
		}
		//判断订单是不是自己的
		if(order.getUser_id()!=loginUser.getId()){
			// 不是自己的
			throw new RuntimeException("用户只能取消自己的订单！");
		}
		orderDAO.deleteOrder(id);
	}

	// 支付订单
	public void pay(String orderid) {
		// 传递订单号 给 数据层，修改订单状态 已经支付
		OrderDAO orderDAO = new OrderDAO();
		orderDAO.updateOrderState(orderid);
	}
	

}
