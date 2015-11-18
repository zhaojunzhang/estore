<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
    function changeNum(id){
    //修改数量
    var number = document.getElementById("estore"+id).value;
    
    //校验number是数字
    if(isNaN(number)){
    alert("数量必须输入一个数字");
    return;
    }
    location.href = "/updateCart?id="+id+"&number="+number;
    
    }
</script>

</head>
<body>
<h1>查看购物车</h1>
<h2><a href="/listproduct">继续购物</a> <a href="/clearCart">清空购物车</a><img src="/img/gotoorder.bmp" style="cursor: ppinter;" 
onclick="location.href='/add_orders.jsp';"/></h2>
<!-- 显示 session中购物车 显示Map -->
<c:if test="${empty cart}">
	<h2>购物车中无记录！</h2>
</c:if>
<c:if test="${not empty cart}">
	<table border="1" width="100%">
		<tr>
			<th>编号</th>
			<th>名称</th>
			<th>单价</th>
			<th>购买数量</th>
			<th>单项总价</th>
			<th>删除</th>
		</tr>
		<c:set var="totalmoney" value="0" scope="page"></c:set>
		<c:forEach items="${cart}" var="entry">
			<tr>
				<td>${entry.key.id }</td>
				<td>${entry.key.name }</td>
				<td>${entry.key.price }</td>
				<td>
				<!-- +号  输入框   -号 -->
				<input type="button"value="-" onclick="location.href='updateCart?id=${entry.key.id}&number=${entry.value-1}'">
				
				<input type="text" name="number" value="${entry.value }" size="2" onblur="changeNum('${entry.key.id  }');" id="estore${entry.key.id  }"/>
				<input type="button" value="+" onclick="location.href='updateCart?id=${entry.key.id}&number=${entry.value+1}'">
				</td>
				<td>${ entry.key.price * entry.value}
				
			<c:set var="totalmoney" value="${entry.key.price * entry.value + totalmoney}" scope="page"></c:set>
				</td>
				<td><a href="/delCartItem?id=${entry.key.id }">删除</a></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="6" align="right">总价：${totalmoney }</td>
		</tr>
	</table>
</c:if>
</body>
</html>