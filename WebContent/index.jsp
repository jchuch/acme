<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="acme.util.*" %>
<%
	String errorMsg = "";
	if (request.getSession().getAttribute("error")!=null) {
		errorMsg = (String)request.getSession().getAttribute("error");
		request.getSession().removeAttribute("error");
	}


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACME Login</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/LoginController" method="post">
		<div><label>Welcome</label></div>
		<table >
		<tr>
			<td colspan="2"><%= errorMsg %></td>
		</tr>
		<tr>
			<td>Enter username : </td>
			<td><input type="text" name="username"></td>
		</tr>
		<tr>
			<td>Enter password : </td>
			<td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Login" /></td>
		</tr>
		</table>
	</form>
</body>
</html>