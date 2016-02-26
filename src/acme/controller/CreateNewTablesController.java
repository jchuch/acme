package acme.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dao.TableSecurityDAO;
import acme.dao.UserDAO;
import acme.dbmodel.User;
import acme.model.Authenticator;
import acme.model.Message;

public class CreateNewTablesController extends HttpServlet{
	
	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final long serialVersionUID = 1L;

	public CreateNewTablesController() {
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
		
		if ("createNewTables".equals(request.getParameter("createNewTablesAction"))) {
			String tablename = request.getParameter("tablename");



			String securitylevel = currentUser.getLevel();
			
			

			TableSecurityDAO table  = new TableSecurityDAO();
			table.createNewTable(tablename,securitylevel);
			
			Message msg = new Message();

			msg.setMessage("Table "+tablename+ " created!");

			request.getSession().setAttribute("message", msg);
			
			response.sendRedirect(request.getContextPath()+"/pages/home/create_new_tables.jsp");
			
		}
		
		



	}


}
