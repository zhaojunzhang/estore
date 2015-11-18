<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>商品详情</h1>
<!-- 显示  javabean对象信息 -->
<c:if test="${empty product}">
<h2>您查询商品不存在</h2>
</c:if>
<c:if test="${not empty product}">
商品编号 ${product.id }<br/>
	<h2>商品名称 ${product.name }</h2>
	<h3>商品分类 ${product.category }</h3>
	<img src="${product.img}" /><br/>
	<h3 style="color:red;font-weight: bold">价格 ： ${product.price } <img src="/img/buy.bmp" onclick="location.href='/addCart?id=${product.id }';" style="cursor: pointer;"/></h3>
	<p>商品详细信息：${product.description }</p>
</c:if>
</body>
</html>