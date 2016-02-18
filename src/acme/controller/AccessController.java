package acme.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dao.TableSecurityDAO;
import acme.dbmodel.TableSecurity;
import acme.dbmodel.User;
import acme.model.Message;

public class AccessController extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final long serialVersionUID = 1L;

	public static final ArrayList<String> securityLevelIndex = new ArrayList<String>();
	static {
		securityLevelIndex.add("G");
		securityLevelIndex.add("H");
		securityLevelIndex.add("F");
		securityLevelIndex.add("E");
		securityLevelIndex.add("HF");
		securityLevelIndex.add("HE");
		securityLevelIndex.add("FE");
		securityLevelIndex.add("L");
	}

	/*
	the first dimension represents user security level, the second dimension represents table security level;
	return l represents that the user's security level is higher than the table's security level;
	s for lower
	e for equal;
	i for incompatible;
	*/
	private static final char[][] securityLevelMatrix = new char[][]{
		  { 'e', 's', 's', 's', 's', 's', 's', 's'},
		  { 'l', 'e', 'i', 'i', 's', 's', 'i', 's'},
		  { 'l', 'i', 'e', 'i', 's', 'i', 's', 's'},
		  { 'l', 'i', 'i', 'e', 'i', 's', 's', 's'},
		  { 'l', 'l', 'l', 'i', 'e', 'i', 'i', 's'},
		  { 'l', 'l', 'i', 'l', 'i', 'e', 'i', 's'},
		  { 'l', 'i', 'l', 'l', 'i', 'i', 'e', 's'},
		  { 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'e'}
	};

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


		TableSecurityDAO tableSecDao = new TableSecurityDAO();


		String tableSecId = "0";
		int action = 0; // no action
		// action : 1 = select
		// action : 2 = insert


		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
			LOG.debug("parameter="+parameter);

			if(parameter.toLowerCase().startsWith("select_")) {

				tableSecId = parameter.substring("select_".length(), parameter.length());
				LOG.debug("tableSecId="+tableSecId);

				action = 1;

				break;

			} else if (parameter.toLowerCase().startsWith("update_")) {

				tableSecId = parameter.substring("update_".length(), parameter.length());
				LOG.debug("tableSecId="+tableSecId);

				action = 2;

				break;

			}

		}


		TableSecurity tableSec = tableSecDao.getTableSecurity(Integer.parseInt(tableSecId));
		String tableLevel = tableSec.getLevel();


		// TODO: check access

		int indexUserLevel = securityLevelIndex.indexOf(level);
		int indexTableLevel = securityLevelIndex.indexOf(tableLevel);

		Message msg = new Message();
		try {
			char queryResult = securityLevelMatrix[indexUserLevel][indexTableLevel];

			// select
			if(action == 1 && (queryResult == 'l' || queryResult == 'e')){
				//success operation

				msg.setMsgType("success");
				msg.setMessage("Operation Success!");
			}
			// insert
			else if(action == 2 && (queryResult == 's' || queryResult == 'e')){
				//success operation

				msg.setMsgType("success");
				msg.setMessage("Operation Success!");
			}
			else {
				// failed operation

				//deny,log,inform user
				msg.setMsgType("warning");
				msg.setMessage("Operation Failed!");

				LOG.debug("user operation denied!");
			}
		} catch(Exception e) {
			LOG.error("check access errro!", e);
			msg.setMsgType("danger");
			msg.setMessage("Operation Error!");
		}

		request.getSession().setAttribute("message", msg);

		response.sendRedirect(request.getContextPath()+"/pages/home/welcome.jsp");
	}


}
