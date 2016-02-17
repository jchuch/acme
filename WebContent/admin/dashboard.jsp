<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,acme.dao.UserDAO,acme.dbmodel.User,acme.util.*" %>
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

	UserDAO userDao = new UserDAO();
	List<User> ls = userDao.getList();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ACME Admin - Dashboard</title>
<script type="javascript">

</script>
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
				<form action="${pageContext.request.contextPath}/LevelController" method="post">
					<table >
					<tr>
						<th><label>Id</label></th>
						<th><label>Username</label></th>
						<th><label>Level</label></th>
					</tr>

					<%
					String[] levelOptions = { "G", "E", "F", "H", "HE", "HF", "FE", "L" };

					for(User userObj : ls) {
						String level = userObj.getLevel();
					%>
					<tr>
						<td><label><%= userObj.getId() %></label></td>
						<td><label><%= userObj.getUsername() %></label></td>
						<td>
							<select name="level_<%= userObj.getId() %>">
								<option value="">-</option>
								<%
								String selected = "";
								for (int i=0; i<levelOptions.length; i++) {
									if (levelOptions[i].equals(level)) {
										selected = "selected";
									} else {
										selected = "";
									}
									%>
									<option value="<%= levelOptions[i] %>" <%= selected %>><%= levelOptions[i] %></option>
									<%
								}
								%>

							</select>
						</td>
					</tr>
					<%
					} // END, for
					%>

					<tr>
						<td></td>
						<td></td>
						<td align="right"><input type="submit" value="Update" /></td>
					</tr>
					</table>
				</form>
			</div>
		</td>
	</tr>
	</table>
</body>
</html>