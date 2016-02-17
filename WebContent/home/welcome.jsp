<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,acme.dao.TableSecurityDAO,acme.dbmodel.TableSecurity,acme.dbmodel.User,acme.util.*" %>
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

	TableSecurityDAO tableSecDAO = new TableSecurityDAO();
	List<TableSecurity> ls = tableSecDAO.getList();

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
						<th><label>Id</label></th>
						<th><label>Name</label></th>
						<th><label>Level</label></th>
						<th></th>
					</tr>
					<%

					for(TableSecurity tableSec : ls) {

					%>
					<tr>
						<td><label><%= tableSec.getId() %></label></td>
						<td><label><%= tableSec.getName() %></label></td>
						<td><label><%= tableSec.getLevel() %></label></td>
						<td>
							<input type="submit" name="select_<%= tableSec.getId() %>" value="Select" />
							<input type="submit" name="update_<%= tableSec.getId() %>" value="Update" />
						</td>
					</tr>
					<%
					} // END, for
					%>


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