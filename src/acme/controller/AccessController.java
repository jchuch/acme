package acme.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dbmodel.User;

public class AccessController extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final long serialVersionUID = 1L;

	public AccessController() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		User currentUser = (User)request.getSession().getAttribute("user");
		if (currentUser==null) {
			// if user not found in session, redirect to login
			response.sendRedirect(request.getContextPath()+"/");
			return;
		}

		String level = currentUser.getLevel();
		LOG.debug("user level="+level);


		// if add_g button clicked
		String add_g = request.getParameter("add_g");
		if (add_g!=null && add_g.length()>0) {
			LOG.debug("add_g clicked!");
		}

		// if update_g button clicked
		String update_g = request.getParameter("update_g");
		if (update_g!=null && update_g.length()>0) {
			LOG.debug("update_g clicked!");
		}

		// TODO: check other buttons




		// TODO: check access

		response.sendRedirect(request.getContextPath()+"/home/welcome.jsp");
	}


}
