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
import acme.model.Message;

public class LoginController extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// if user exists in session, redirect to the users homepage
//		User currentUser = (User)request.getSession().getAttribute("user");
//		if (currentUser!=null) {
//			if (currentUser.getAdmin()) {
//				response.sendRedirect(request.getContextPath()+"/pages/admin/dashboard.jsp");
//				return;
//			} else {
//				response.sendRedirect(request.getContextPath()+"/pages/home/welcome.jsp");
//				return;
//			}
//		}

		// clear the session first
		request.getSession().invalidate();


		//String loginAction = request.getParameter("loginAction");
		User user = null;
		if ("login".equals(request.getParameter("loginAction"))) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			Authenticator authenticator = new Authenticator();
			user = authenticator.authenticate(username, password);
		}

		if (user!=null) {

			//request.getSession().setAttribute("token", username+System.currentTimeMillis());

			request.getSession().setAttribute("user", user);

			if (user.getAdmin()) {
				response.sendRedirect(request.getContextPath()+"/pages/admin/dashboard.jsp");
				return;
			} else {
				response.sendRedirect(request.getContextPath()+"/pages/home/welcome.jsp");
				return;
			}

		} else {

			Message msg = new Message();
			msg.setMsgType("warning");
			msg.setMessage("Login failed!");

			request.getSession().setAttribute("message", msg);

			response.sendRedirect(request.getContextPath()+"/");
			return;
		}

	}


}
