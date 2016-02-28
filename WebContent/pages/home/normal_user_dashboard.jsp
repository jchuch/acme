<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,acme.dao.UserDAO,acme.dbmodel.User,acme.model.Message,acme.util.*" %>
<%
	//Only normal user can access

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

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ACME User - Dashboard</title>
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

		<div class="row">
			<div class="col-md-12">
				<a href="access_tables.jsp">Access Tables</a>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12">
				<a href="create_new_tables.jsp">Create New Table</a>
			</div>
		</div>

	</div>

</body>
</html>