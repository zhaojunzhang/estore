package cn.itcast.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.domin.Order;
import cn.itcast.domin.Orderitem;
import cn.itcast.domin.User;
import cn.itcast.utils.JDBCUtils;

/**
 * ���ݲ�
 * 
 * @author seawind
 * 
 */
public class OrderDAO {
	//���ݶ���   ��ѯ������
	public List<Orderitem> findOrderItems(Order order){
		String sql = "select * from orderitem where order_id=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		try {
			List<Orderitem> orderitems = queryRunner.query(sql, new BeanListHandler<Orderitem>(Orderitem.class),order.getId());
			//����id ��ѯ��Ʒ��
			for(Orderitem orderitem:orderitems){
				//����  ��Ʒid ȥ��ѯ��Ʒ��
				ProductDAO productDAO = new ProductDAO();
				orderitem.setProduct(productDAO.findById(orderitem.getProduct_id()));
			}
			return orderitems;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	// ���ɶ��� ������
	public void insertOrder(Order order) {
		// �������
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);// ��������

			// ���붩����
			String sql = "insert into orders values(?,?,?,null,0,?)";
			// ���ɶ�����
			String orderid = JDBCUtils.generateOrderId();
			Object[] param = { orderid, order.getTotalmoney(),
					order.getReceiverinfo(), order.getUser_id() };
			QueryRunner queryRunner = new QueryRunner(); // �������ӳأ��Լ���������

			queryRunner.update(conn, sql, param);

			// ���붩����
			List<Orderitem> orderitems = order.getOrderItems();
			String sql2 = "insert into orderitem values(?,?,?,?)";
			for (Orderitem orderitem : orderitems) {
				Object[] param2 = { orderid, orderitem.getProduct_id(),
						orderitem.getBuynum(), orderitem.getMoney() };
				queryRunner.update(conn, sql2, param2);
			}

			// �����ύ
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			// ����ع�
			DbUtils.rollbackAndCloseQuietly(conn);
			e.printStackTrace();
			throw new RuntimeException("��������ʧ�ܣ�");
		}
	}
//�鿴���ж���---�鿴������
	public List<Order> findAll() {
		String sql = "select * from orders ";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		try {
			List<Order> orders = queryRunner.query(sql, new BeanListHandler<Order>(Order.class));
			//��������  ֻ��user_id  �ֶ� ----��ҪUserȫ����Ϣ
			for(Order order:orders){
				//�鿴User�� ���user����
				UserDAO userDAO =new UserDAO();
				order.setUser(userDAO.findById(order.getUser_id()));
			}
			return orders;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
//���ݱ�Ų�ѯ����
	public List<Order> findOrdersByUser(User user) {
		String sql = "select * from orders where user_id=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		try {
			List<Order> orders = queryRunner.query(sql, new BeanListHandler<Order>(Order.class),user.getId());
			// �������� ֻ��user_id �ֶ� ---- ��ҪUserȫ����Ϣ ---- ����Ҳ���Բ���ѯ ���� session���û�
			// for (Order order : orders) {
			// // �鿴User�� ���user����
			// UserDAO userDAO = new UserDAO();
			// order.setUser(userDAO.findById(order.getUser_id()));// ����id
			// // ��ѯuser������Ϣ
			// }
			
			return orders;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//���ݶ������  ��ѯ����
	public Order findById(String id) {
		String sql = "select * from orders where id=?";
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		try {
			Order order = queryRunner.query(sql, new BeanHandler<Order>(Order.class),id);
			
			return order;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

//���� ���� ��� ȡ������ --- ͬʱɾ�� ������ �Ͷ����� ������
	public void deleteOrder(String id) {
		//�������
		Connection conn=null;
		try {
			conn=JDBCUtils.getConnection();
			conn.setAutoCommit(false);//��������
			//��ɾ��������
			String sql1 = "delete from orderitem where order_id=?";
			QueryRunner queryRunner = new QueryRunner();
			queryRunner.update(conn, sql1, id);
			
			//ɾ������
			String sql2 = "delete from orders where id=?";
			
			queryRunner.update(conn, sql2, id);
			//�ر�����
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			//�ع�����
			DbUtils.rollbackAndCloseQuietly(conn);
			e.printStackTrace();
			throw new RuntimeException("ȡ������ʧ�ܣ�");
		}
		
		
	}

//�޶� ����״̬  �Ѿ�֧��
	public void updateOrderState(String orderid) {
		String sql = "update orders set state=1 where id=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		try {
			queryRunner.update(sql,orderid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

