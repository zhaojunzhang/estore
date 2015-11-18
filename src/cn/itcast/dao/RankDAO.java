package cn.itcast.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.domin.Orderitem;
import cn.itcast.domin.Product;
import cn.itcast.utils.JDBCUtils;
/**
 * ���ݲ�
 * �鿴��
 * @author samsung
 *
 */
public class RankDAO {

	public List<Orderitem> findRankData() {
		//������й�����ѯ���ܿ��ܲ�ѯ�ֶκ�������  ��һ����Ϊ��DBUtils���Է�װ��ͨ��������ʽ
		String sql = "select product_id , sum(orderitem.buynum) buynum from orders,orderitem where orders.id = orderitem.order_id and orders.state =1 group by orderitem.product_id";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		try {
			List<Orderitem> rank= queryRunner.query(sql, new BeanListHandler<Orderitem>(Orderitem.class));
			//�鿴������Ҫ��Ʒ���ƣ����۵���Ϣ----����  ��Ʒid��ѯproduct
			for(Orderitem orderitem:rank){
				String sql2 = "select * from products where id=?";
				Product product=queryRunner.query(sql2, new BeanHandler<Product>(Product.class),orderitem.getProduct_id());
				//����Ʒ  ���� orderitem
				orderitem.setProduct(product);
			}
			return rank;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
