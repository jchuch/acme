package acme.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dbmodel.User;
import acme.model.Authenticator;

public class LoginController extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Authenticator authenticator = new Authenticator();
		User user = authenticator.authenticate(username, password);

		if (user!=null) {
			//TODO set session token

			request.getSession().setAttribute("token", username+System.currentTimeMillis());

			request.getSession().setAttribute("user", user);

			if (user.getAdmin()) {
				response.sendRedirect(request.getContextPath()+"/admin/dashboard.jsp");
			} else {
				response.sendRedirect(request.getContextPath()+"/home/welcome.jsp");
			}

		} else {

			request.getSession().setAttribute("error", "Login failed!");

			response.sendRedirect(request.getContextPath()+"/");
		}

	}


}
