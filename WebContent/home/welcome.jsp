<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="acme.dbmodel.User,acme.util.*" %>
<%
	// Only normal user can access

	String errorMsg = "";
	if (request.getSession().getAttribute("error")!=null) {
		errorMsg = (String)request.getSession().getAttribute("error");
		request.getSession().removeAttribute("error");
	}

	User user = null;
	if (request.getSession().getAttribute("user")!=null) {
		user = (User)request.getSession().getAttribute("user");
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACME Home - Welcome</title>
</head>
<body>
	<div>
		<label>Welcome : <%= user.getUsername() %></label>
		<a href="${pageContext.request.contextPath}/logout.jsp"/>Logout</a>
	</div>
	<div>
		<form xxaction="${pageContext.request.contextPath}/AccessController" method="post">
			<table >
			<tr>
				<td><label>Commands</label></td>
				<td><textarea name="sqlcommand"></textarea></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Run" /></td>
			</tr>
			</table>
		</form>
	</div>
</body>
</html>