<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,acme.dao.LogDAO,acme.dbmodel.Log,acme.dbmodel.User,acme.model.Message,acme.util.*" %>
<%
	// Only admin / SO can access

	User user = null;
	if (request.getSession().getAttribute("user")!=null) {
		user = (User)request.getSession().getAttribute("user");
	} else {
		// redirect to logout page
		response.sendRedirect(request.getContextPath()+"/logout.jsp");
		return;
	}

	Message message = null;
	if (request.getSession().getAttribute("message")!=null) {
		message = (Message)request.getSession().getAttribute("message");
		request.getSession().removeAttribute("message");
	}

	LogDAO logDao = new LogDAO();
	List<Log> ls = logDao.getList();

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ACME Admin - View Log</title>
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
			<li class="active">View Logs</li>
		</ol>


		<div class="row">
			<div class="col-md-4 col-md-offset-4">

				<% if(message!=null) { %>
				<div class="alert alert-<%= message.getMsgType() %>"><%= message.getMessage() %></div>
				<% } %>

			</div>
		</div>


		<table class="table table-striped">

		<tr>
			<th class="col-md-1"><label>Id</label></th>
			<th class="col-md-1"><label>UserId</label></th>
			<th class="col-md-1"><label>Username</label></th>
			<th class="col-md-1"><label>Operation</label></th>
			<th class="col-md-1"><label>TableId</label></th>
			<th class="col-md-1"><label>Table Name</label></th>
			<th class="col-md-2"><label>Time</label></th>
			<th class="col-md-2"><label>Message</label></th>
			<th class="col-md-2"></th>
		</tr>
		<%

		for(Log logObj : ls) {

		%>
		<tr>
			<td><label><%= logObj.getId() %></label></td>
			<td><label><%= logObj.getUserId() %></label></td>
			<td><label><%= logObj.getUserName() %></label></td>
			<td><label><%= logObj.getOperation() %></label></td>
			<td><label><%= logObj.getTableId() %></label></td>
			<td><label><%= logObj.getTableName() %></label></td>
			<td><label><%= logObj.getTime() %></label></td>
			<td><label><%= logObj.getMessage() %></label></td>
			<td></td>
		</tr>
		<%
		} // END, for
		%>

		</table>

	</div>

</body>
</html>