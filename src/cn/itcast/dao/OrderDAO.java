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
 * 数据层
 * 
 * @author seawind
 * 
 */
public class OrderDAO {
	//根据订单   查询订单项
	public List<Orderitem> findOrderItems(Order order){
		String sql = "select * from orderitem where order_id=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		try {
			List<Orderitem> orderitems = queryRunner.query(sql, new BeanListHandler<Orderitem>(Orderitem.class),order.getId());
			//根据id 查询商品表
			for(Orderitem orderitem:orderitems){
				//根据  商品id 去查询商品表
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


	// 生成订单 多表插入
	public void insertOrder(Order order) {
		// 获得连接
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);// 开启事务

			// 插入订单表
			String sql = "insert into orders values(?,?,?,null,0,?)";
			// 生成订单号
			String orderid = JDBCUtils.generateOrderId();
			Object[] param = { orderid, order.getTotalmoney(),
					order.getReceiverinfo(), order.getUser_id() };
			QueryRunner queryRunner = new QueryRunner(); // 不给连接池，自己控制事务

			queryRunner.update(conn, sql, param);

			// 插入订单项
			List<Orderitem> orderitems = order.getOrderItems();
			String sql2 = "insert into orderitem values(?,?,?,?)";
			for (Orderitem orderitem : orderitems) {
				Object[] param2 = { orderid, orderitem.getProduct_id(),
						orderitem.getBuynum(), orderitem.getMoney() };
				queryRunner.update(conn, sql2, param2);
			}

			// 事务提交
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			// 事务回滚
			DbUtils.rollbackAndCloseQuietly(conn);
			e.printStackTrace();
			throw new RuntimeException("订单生成失败！");
		}
	}
//查看所有订单---查看订单项
	public List<Order> findAll() {
		String sql = "select * from orders ";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		try {
			List<Order> orders = queryRunner.query(sql, new BeanListHandler<Order>(Order.class));
			//订单表中  只有user_id  字段 ----需要User全部信息
			for(Order order:orders){
				//查看User表 获得user对象
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
//根据编号查询订单
	public List<Order> findOrdersByUser(User user) {
		String sql = "select * from orders where user_id=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		try {
			List<Order> orders = queryRunner.query(sql, new BeanListHandler<Order>(Order.class),user.getId());
			// 订单表中 只有user_id 字段 ---- 需要User全部信息 ---- 这里也可以不查询 设置 session中用户
			// for (Order order : orders) {
			// // 查看User表 获得user对象
			// UserDAO userDAO = new UserDAO();
			// order.setUser(userDAO.findById(order.getUser_id()));// 根据id
			// // 查询user对象信息
			// }
			
			return orders;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//根据订单编号  查询订单
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

//根据 订单 编号 取消订单 --- 同时删除 订单表 和订单项 表数据
	public void deleteOrder(String id) {
		//事物控制
		Connection conn=null;
		try {
			conn=JDBCUtils.getConnection();
			conn.setAutoCommit(false);//开启事物
			//先删除订单项
			String sql1 = "delete from orderitem where order_id=?";
			QueryRunner queryRunner = new QueryRunner();
			queryRunner.update(conn, sql1, id);
			
			//删除订单
			String sql2 = "delete from orders where id=?";
			
			queryRunner.update(conn, sql2, id);
			//关闭事物
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			//回滚事物
			DbUtils.rollbackAndCloseQuietly(conn);
			e.printStackTrace();
			throw new RuntimeException("取消订单失败！");
		}
		
		
	}

//修订 订单状态  已经支付
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

