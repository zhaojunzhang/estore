<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>生成订单</h1>
<!-- 让用户填写收货人信息，显示订单信息 -->
<c:if test="${not empty cart}">
   <h2>订单信息确认</h2>
   <table border = "1" width="100%">
   <tr>
        <th>编号</th>
        <th>名称</th>
        <th>单价</th>
        <th>购买数量</th>
        <th>单项总价</th>
   </tr>
   	<c:set var="totalmoney" value="0" scope="page" />
   <c:forEach items = "${cart}" var="entry">
       <tr>
          <td>${entry.key.id }</td>
          <td>${entry.key.name }</td>
          <td>${entry.key.price }</td>
          <td>${entry.value }</td>
          <td>${entry.key.price*entry.value }</td>
            <c:set var="totalmoney" value="${totalmoney +entry.key.price* entry.value }" scope="page" ></c:set>
       </tr>
   </c:forEach>
   <tr>
     <td colspan="5" align="right">总价：${totalmoney }</td>
   </tr>
   </table>
   <h2>填写收货人信息</h2>
   <form action="/addOrders" method="post">
                  收货人信息<textarea rows="6" cols="80"
                  name="receiverinfo"></textarea><br/>
                  <input type="submit" value="生成订单"/>
   
   </form>
</c:if>
</body>
</html>