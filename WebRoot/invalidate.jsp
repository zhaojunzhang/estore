<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//销毁session 
session.invalidate();
//跳回主页
response.sendRedirect("/index.jsp");
%>