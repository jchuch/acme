<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,acme.dao.TableSecurityDAO,acme.dbmodel.TableSecurity,acme.dbmodel.User,acme.model.Message,acme.util.*" %>
<%
	// Only admin / SO can access

	Message message = null;
	if (request.getSession().getAttribute("message")!=null) {
		message = (Message)request.getSession().getAttribute("message");
		request.getSession().removeAttribute("message");
	}

	User user = null;
	if (request.getSession().getAttribute("user")!=null) {
		user = (User)request.getSession().getAttribute("user");
	} else {
		// redirect to logout page
		response.sendRedirect(request.getContextPath()+"/logout.jsp");
		return;
	}

	TableSecurityDAO tableSecDao = new TableSecurityDAO();
	List<TableSecurity> ls = tableSecDao.getList();

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ACME Admin - Maintain Table Security</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">

		<div class="page-header">
			<div class="row">
				<div class="col-md-9">
					<h2>Welcome : <%= user.getUsername() %></h2>
				</div>

				<div class="col-md-3">
					<a href="${pageContext.request.contextPath}/logout.jsp" class="btn btn-info" role="button"><span class="glyphicon glyphicon-log-in"></span> Logout</a>
				</div>
			</div>
		</div>


		<ol class="breadcrumb">
			<li><a href="${pageContext.request.contextPath}/pages/admin/dashboard.jsp">Home</a></li>
			<li class="active">Maintain Table Security</li>
		</ol>


		<div class="row">
			<div class="col-md-4 col-md-offset-4">

				<% if(message!=null) { %>
				<div class="alert alert-<%= message.getMsgType() %>"><%= message.getMessage() %></div>
				<% } %>

			</div>
		</div>


		<form action="${pageContext.request.contextPath}/TableSecurityLevelController" method="post" role="form">
		<table class="table table-striped">
		<tr>
			<th><label>Id</label></th>
			<th><label>Name</label></th>
			<th><label>Level</label></th>
		</tr>

		<%
		String[] levelOptions = { "G", "E", "F", "H", "HE", "HF", "FE", "L" };

		for(TableSecurity tableSecObj : ls) {
			String level = tableSecObj.getLevel();
		%>
		<tr>
			<td><label><%= tableSecObj.getId() %></label></td>
			<td><label><%= tableSecObj.getName() %></label></td>
			<td>
				<select name="level_<%= tableSecObj.getId() %>">
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


<%--
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
				<form action="${pageContext.request.contextPath}/TableSecurityLevelController" method="post">
					<table >
					<tr>
						<th><label>Id</label></th>
						<th><label>Name</label></th>
						<th><label>Level</label></th>
					</tr>

					<%
					String[] levelOptions = { "G", "E", "F", "H", "HE", "HF", "FE", "L" };

					for(TableSecurity tableSecObj : ls) {
						String level = tableSecObj.getLevel();
					%>
					<tr>
						<td><label><%= tableSecObj.getId() %></label></td>
						<td><label><%= tableSecObj.getName() %></label></td>
						<td>
							<select name="level_<%= tableSecObj.getId() %>">
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
--%>
</body>
</html>