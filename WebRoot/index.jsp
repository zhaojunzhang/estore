<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
     <h1>Estore商城主页</h1>
    <!-- 显示用户登录状态-->
    <c:if test="${empty loginUser}">
    	<h2>您还未登录，去<a href="/login.jsp">登录!</a> 还没有账号，<a href="/regist.jsp">赶快注册</a></h2>
    </c:if>
    <c:if test="${not empty loginUser}">
    	<h2>欢迎您 ，${loginUser.nickname } ，您的角色是：${loginUser.role=='user'?"商城用户":"管理员" }  <a href="/invalidate.jsp">注销</a></h2> 
    </c:if>
    <a href="regist.jsp">用户注册</a>
    <a href="/add_product.jsp">添加商品</a>
    <a href="/listproduct">查看商品列表</a>
    <a href="/detail_product.jsp">查看商品详情</a>
    <a href="/cart.jsp">查看购物车</a>
    <a href="/listOrders">查看订单</a>
    <a href="/showRank">查看榜单</a>
    <a href="/pay.jsp">在线支付</a>
  </body>
</html>
