<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="acme.dbmodel.User,acme.util.*" %>
<%
	// Only admin / SO can access

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
<title>ACME Admin - Dashboard</title>
</head>
<body>
	<div>
		<label>Welcome : <%= user.getUsername() %></label>
		<a href="${pageContext.request.contextPath}/logout.jsp"/>Logout</a>
	</div>
	<div>
		<form action="${pageContext.request.contextPath}/AccessController" method="post">
			<table >
			<tr>
				<td><label>User</label></td>
				<td>
					<select name="level">
						<option value="G">G</option>
						<option value="E">E</option>
						<option value="F">F</option>
						<option value="H">H</option>
						<option value="HE">HE</option>
						<option value="HF">HF</option>
						<option value="FE">FE</option>
						<option value="L">L</option>
					</select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Update" /></td>
			</tr>
			</table>
		</form>
	</div>
</body>
</html>