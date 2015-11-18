<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>订单列表</h1>
<!-- 显示订单列表信息  List -->
<c:if test="${empty orders}">
	<h1>没有您查询的订单！</h1>
</c:if>
<c:if test="${not empty orders}">
	<c:forEach items="${orders}" var="order">
		订单编号： ${ order.id} 下单用户 ：${order.user.nickname } 下单时间 ：${order.ordertime }<br/>
		金额 : ${order.totalmoney } 支付状态 ：${order.state==0?"未支付":"已支付" } 
		<c:if test="${order.state==0 and sessionScope.loginUser.role=='user'}">
		<!-- 订单必须未支付，取消订单必须是商城用户，不能是管理员 -->
		<a href="/cancelOrders?id=${order.id }">取消订单</a>
		<!-- 对为支付订单，而且  必须是普通用户身份，选择付款 -->
		
		<!-- 将订单号  和 金额  传递  银行选择页面 -->
		<form action="/pay.jsp" method="post" style="display:inline;">
		     <input type="hidden" name="order_id" value="${order.id }"/>
		     <input type="hidden" name="totalmoney" value="${order.totalmoney}"/>
		     <input type="submit" value="支付"/>
		</form>
		</c:if>
		<br/>
		<p>收货人信息 : ${order.receiverinfo }</p>
		<!-- 订单项列表 -->
		<table border="1" width="100%">
			<tr>
				<th>编号</th>
				<th>名称</th>
				<th>单价</th>
				<th>购买数量</th>
			</tr>
			<c:forEach items="${order.orderItems}" var="orderitem">
				<tr>
					<td>${orderitem.product_id }</td>
					<td>${orderitem.product.name }</td>
					<td>${orderitem.product.price }</td>
					<td>${orderitem.buynum }</td>
				</tr>
			</c:forEach>
		</table>
		<hr/>
	</c:forEach>
</c:if>
</body>
</html>