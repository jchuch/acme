<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="acme.util.*" %>
<%
	// clear session and redirect to index/login
	request.getSession().invalidate();
	response.sendRedirect(request.getContextPath()+"/");
%>