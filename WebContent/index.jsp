<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="acme.model.Message,acme.util.*" %>
<%
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
	<title>ACME Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">

		<form class="form-signin" action="${pageContext.request.contextPath}/LoginController" id="loginForm" method="post" role="form">
			<input type="hidden" id="loginAction" name="loginAction" value="login"/>

			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<h2 class="form-signin-heading">Welcome To ACME</h2>
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
				<div class="col-md-4 col-md-offset-4">
					<div class="form-group">
						<label for="username">User name</label>
						<input type="text" name="username" id="username" class="form-control" required="true"
							placeholder="Please enter your username"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<div class="form-group">
						<label for="password">Password</label>
						<input type="password" name="password" id="password" class="form-control" required="true"
							placeholder="Please enter your password"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<button type="submit" class="btn btn-info">
						<span class="glyphicon glyphicon-log-in"></span> Login
					</button>
				</div>
			</div>
		</form>
	</div>

</body>
</html>