<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>商品列表</h1>
<img src="/img/cart.bmb" onclick="location.href='/cart.jsp';" style="cursor:pointer;"/><br/>
<hr/>
<c:if test="${empty products}">
     <h1>系统还没有添加任何商品</h1>
</c:if>
<c:if test="${not empty products}">
     <!--用分割线分割 -->
     <c:forEach items="${products}" var="product">
                       商品编号${product.id }<br/>
     <h2><a href="/showProduct?id=${product.id}">商品名称${product.name }</a></h2>
     <h3>商品分类${product.category }</h3>
     <img src="${product.img_s }"
     onclick="location.href='/showProduct?id=${product.id}';"
     style="cursor:pointer;" /><br/>
     <h3 style="color:red;font-weight:bold">价格 ${product.price }</h3>
     <hr/>
     </c:forEach>
</c:if>
</body>
</html>