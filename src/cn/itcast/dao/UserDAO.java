package cn.itcast.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.domin.User;
import cn.itcast.utils.JDBCUtils;
import cn.itcast.utils.MD5Utils;

public class UserDAO {
	//登录查询
	
	public User findByUsernameAndPassword(String username,String password){
		String sql = "select *from users where username=? and password=?";
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		try {
			User user = queryRunner.query(sql, new BeanHandler<User>(User.class),username,MD5Utils.md5(password));
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//激活账户
	public void updateState(User user){
		String sql="update users set state= ? where id= ?";
		QueryRunner queryRunner  = new QueryRunner(JDBCUtils.getDataSource());
		
		try {
			queryRunner.update(sql,user.getState(),user.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	//根据激活码查询用户
	public User findByActivecode(String activecode) {
		String sql = "select * from users where activecode = ?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		try {
			User user = queryRunner.query(sql,
					new BeanHandler<User>(User.class), activecode);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
			
	
//用户注册
	public void insert(User user){
		String sql = "insert into users values(null,?,?,?,?,'user',null,0,?)";
		QueryRunner queryRunner  =new QueryRunner(JDBCUtils.getDataSource());
		Object[] param = {user.getUsername(),MD5Utils.md5(user.getPassword()),
				user.getNickname(),user.getEmail(),user.getActivecode()
		};
		//密码需要md5  加密
		try {
			queryRunner.update(sql,param);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 用户名重复 、邮箱重复
			throw new RuntimeException("注册失败！" + e.getMessage());
			
		}
	}
	//根据id  查询User对象  方法
	public User findById(int id) {
		String sql = "select * from users where id=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		User user;
		try {
			user = queryRunner.query(sql, new BeanHandler<User>(User.class),id);
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
