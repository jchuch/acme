<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,acme.dao.TableSecurityDAO,acme.dbmodel.TableSecurity,acme.dbmodel.User,acme.model.Message,acme.util.*" %>
<%
	// Only normal user can access

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

	TableSecurityDAO tableSecDAO = new TableSecurityDAO();
	List<TableSecurity> ls = tableSecDAO.getList();

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ACME Home - Welcome</title>
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


		<div class="row">
			<div class="col-md-4 col-md-offset-4">

				<% if(message!=null) { %>
				<div class="alert alert-<%= message.getMsgType() %>"><%= message.getMessage() %></div>
				<% } %>

			</div>
		</div>


		<form action="${pageContext.request.contextPath}/AccessController" method="post" role="form">
		<table class="table table-striped">
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
				<input class="btn btn-default" type="submit" name="select_<%= tableSec.getId() %>" value="Select" />
				<input class="btn btn-default" type="submit" name="insert_<%= tableSec.getId() %>" value="Insert" />
			</td>
		</tr>
		<%
		} // END, for
		%>
		</table>
		</form>

	</div>

</body>
</html>