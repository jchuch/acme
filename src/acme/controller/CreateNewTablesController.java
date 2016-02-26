package acme.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dao.TableSecurityDAO;
import acme.dbmodel.User;
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

			TableSecurityDAO table = new TableSecurityDAO();
			boolean isCreated = table.createNewTable(tablename,securitylevel);

			Message msg = new Message();
			if (isCreated) {
				msg.setMsgType("success");
				msg.setMessage("Table "+tablename+" created!");
			} else {
				msg.setMsgType("warning");
				msg.setMessage("Table "+tablename+" cannot be created!");
			}

			request.getSession().setAttribute("message", msg);

			response.sendRedirect(request.getContextPath()+"/pages/home/create_new_tables.jsp");

		}

	}


}
