package acme.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dao.UserDAO;
import acme.model.Message;

public class CreateNewUsersController extends HttpServlet{

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final long serialVersionUID = 1L;

	public CreateNewUsersController() {
		super();
}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if ("createNewUsers".equals(request.getParameter("createNewUsersAction"))) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String level = request.getParameter("level");

			UserDAO user = new UserDAO();
			boolean isCreated = user.createNewUser(username,password,level);

			Message msg = new Message();
			if (isCreated) {
				msg.setMsgType("success");
				msg.setMessage("User "+username+" created!");
			} else {
				msg.setMsgType("warning");
				msg.setMessage("User "+username+" cannot be created!");
			}

			request.getSession().setAttribute("message", msg);

			response.sendRedirect(request.getContextPath()+"/pages/admin/create_new_users.jsp");

		}

	}


}
