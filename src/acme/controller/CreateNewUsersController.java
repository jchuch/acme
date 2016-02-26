package acme.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dao.UserDAO;
import acme.dbmodel.User;
import acme.model.Authenticator;
import acme.model.Message;

public class CreateNewUsersController extends HttpServlet{
	
	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final long serialVersionUID = 1L;

	public CreateNewUsersController() {
		super();
}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// clear the session first
		request.getSession().invalidate();

		if ("createNewUsers".equals(request.getParameter("createNewUsersAction"))) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			UserDAO user  = new UserDAO();
			user.createNewUser(username,password);
			
			Message msg = new Message();

			msg.setMessage("User "+username+ " created!");

			request.getSession().setAttribute("message", msg);
			
			response.sendRedirect(request.getContextPath()+"/pages/admin/create_new_users.jsp");
			
		}
		
		



	}


}
