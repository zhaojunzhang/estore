package cn.itcast.service;
/**
 * ҵ���
 */

import java.util.List;

import cn.itcast.dao.OrderDAO;
import cn.itcast.domin.Order;
import cn.itcast.domin.Orderitem;
import cn.itcast.domin.User;

public class OrderService {
//���ɶ���
	public void addOrder(Order order) {
	OrderDAO orderDAO= new OrderDAO();
	orderDAO.insertOrder(order);
		
	}

	public List<Order> listOrders(User loginUser) {
		//�ж����
		if(loginUser.getRole().equals("admin")){//����ǹ���Ա
			//�鿴���еĶ���
			OrderDAO orderDAO = new OrderDAO();
			List<Order> orders= orderDAO.findAll();//���ﶩ��û�ж�����
			//���ݶ���   ��ѯ������
			for(Order order: orders){
				List<Orderitem> orderitems =orderDAO.findOrderItems(order);
				order.setOrderItems(orderitems);//��������  ���浽����
			}
			
			return orders;
		}else if(loginUser.getRole().equals("user")){//����̳��û�
			//�鿴�û��Լ��Ķ���
			OrderDAO orderDAO =new OrderDAO();
			List<Order> orders = orderDAO.findOrdersByUser(loginUser);
			
			
			for(Order order:orders){
				List<Orderitem> orderitems =orderDAO.findOrderItems(order);
				order.setOrderItems(orderitems);//��������  ���浽����
				
				order.setUser(loginUser);
			}
			
			return orders;
		}
		
		return null;
	}
	
    //ȡ������  id��������š���loignUser˭Ҫȡ��
	public void cancelOrder(String id, User loginUser) {
		//����  �������   ����ѯ������
		
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.findById(id);
		
		if(order==null){
			throw new RuntimeException("��ȡ���Ķ�����Ų����ڣ�");
		}
		// �ж϶����ǲ�����֧��
		if(order.getState()==1){
			//�����Ѿ�֧��
			throw new RuntimeException("������֧�����޷�ȡ����");
		}
		//�ж϶����ǲ����Լ���
		if(order.getUser_id()!=loginUser.getId()){
			// �����Լ���
			throw new RuntimeException("�û�ֻ��ȡ���Լ��Ķ�����");
		}
		orderDAO.deleteOrder(id);
	}

	// ֧������
	public void pay(String orderid) {
		// ���ݶ����� �� ���ݲ㣬�޸Ķ���״̬ �Ѿ�֧��
		OrderDAO orderDAO = new OrderDAO();
		orderDAO.updateOrderState(orderid);
	}
	

}
