package cn.itcast.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 工具类 提供数据库连接池 和数据库连接
 * 
 * @author seawind
 * 
 */
public class JDBCUtils {
	private static DataSource dataSource = new ComboPooledDataSource();

	public static DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 当DBUtils需要手动控制事务时，调用该方法获得一个连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	//生成随机  商品编号
	public static String generateProductId(){
		String uuid = UUID.randomUUID().toString();
		int hashCode = Math.abs(uuid.hashCode());
		return "ep-"+hashCode;
	}
	public static String generateOrderId(){
		String uuid = UUID.randomUUID().toString();
		int hashCode = Math.abs(uuid.hashCode());
		return "Order-"+hashCode;
	}
}
