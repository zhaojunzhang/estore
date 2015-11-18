<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>销售排行表单</h1>
<!-- 显示  applicationScope 中  rank 数据  List -->
<c:if test="${empty rank}">
<h1>商城  没有销售记录！无法生成榜单</h1>
</c:if>
<c:if test="${not empty rank}">
<h2><a href = "/exportRank">导出榜单</a></h2>
<table border="1" width="100%">
		<tr>
			<th>排名</th>
			<th>商品编号</th>
			<th>名称</th>
			<th>单价</th>
			<th>销售数量</th>
		</tr>
		<!-- 这里rankitem 就是 orderitem对象 -->
		<c:forEach items="${rank}" var="rankitem" varStatus="status">
			<tr>
				<td>${status.index + 1 }</td>
				<td>${rankitem.product.id }</td>
				<td>${rankitem.product.name }</td>
				<td>${rankitem.product.price }</td>
				<td>${rankitem.buynum }</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
</body>
</html>