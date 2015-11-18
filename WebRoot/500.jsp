<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 处理错误，提示错误信息给用户 -->
<h1>您的访问有错误！</h1>
<%
   if(exception!=null){
   out.print("<h3>"+exception.getMessage()+"</h3>");
   }
 %>
</body>
</html>