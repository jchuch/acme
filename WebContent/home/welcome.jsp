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
	<table >
	<tr>
		<td>
			<div>
				<table >
				<tr>
					<td>
						<label>Welcome : <%= user.getUsername() %></label>
					</td>
					<td>
						<button><a href="${pageContext.request.contextPath}/logout.jsp">Logout</a></button>
					</td>
				</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div><p><%= errorMsg %>&nbsp;</p></div>
		</td>
	</tr>
	<tr>
		<td>
			<div>
				<form action="${pageContext.request.contextPath}/AccessController" method="post">
					<table >
					<tr>
						<td><label>General</label></td>
						<td><input type="submit" name="add_g" value="Add" /></td>
						<td><input type="submit" name="update_g" value="Update" /></td>
					</tr>
					<tr>
						<td><label>Engineer</label></td>
						<td><input type="submit" name="add_e" value="Add" /></td>
						<td><input type="submit" name="update_e" value="Update" /></td>
					</tr>
					<tr>
						<td><label>Finance</label></td>
						<td><input type="submit" name="add_f" value="Add" /></td>
						<td><input type="submit" name="update_f" value="Update" /></td>
					</tr>
					<tr>
						<td><label>Human Resource</label></td>
						<td><input type="submit" name="add_hr" value="Add" /></td>
						<td><input type="submit" name="update_hr" value="Update" /></td>
					</tr>
					<tr>
						<td><label>Human Resource / Engineer</label></td>
						<td><input type="submit" name="add_he" value="Add" /></td>
						<td><input type="submit" name="update_he" value="Update" /></td>
					</tr>
					<tr>
						<td><label>Human Resource / Finance</label></td>
						<td><input type="submit" name="add_hf" value="Add" /></td>
						<td><input type="submit" name="update_hf" value="Update" /></td>
					</tr>
					<tr>
						<td><label>Finance / Engineer</label></td>
						<td><input type="submit" name="add_fe" value="Add" /></td>
						<td><input type="submit" name="update_fe" value="Update" /></td>
					</tr>
					<tr>
						<td><label>Leader</label></td>
						<td><input type="submit" name="add_l" value="Add" /></td>
						<td><input type="submit" name="update_l" value="Update" /></td>
					</tr>

					<!--
					<tr>
						<td></td>
						<td><input type="submit" value="Run" /></td>
					</tr>
					-->

					</table>
				</form>
			</div>
		</td>
	</tr>
	</table>
</body>
</html>