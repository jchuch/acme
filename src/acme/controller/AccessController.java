package acme.controller;

import java.io.IOException;
import java.util.ArrayList;

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
	
	public ArrayList<String> securityLevelIndex;

	public char[][] securityLevelMatrix; 

	public AccessController() {
		super();
		
		securityLevelIndex = new ArrayList<String>();
		securityLevelIndex.add("G");
		securityLevelIndex.add("H");
		securityLevelIndex.add("F");
		securityLevelIndex.add("E");
		securityLevelIndex.add("HF");
		securityLevelIndex.add("HE");
		securityLevelIndex.add("FE");
		securityLevelIndex.add("L");
		
		/*
		the first dimension represents user security level, the second dimension represents table security level;
		return l represents that the user's security level is higher than the table's security level;
		s for lower
		e for equal;
		i for incompatible;
		*/
		
		securityLevelMatrix = new char[][]{
	  { 'e', 's', 's', 's', 's', 's', 's', 's'},
	  { 'l', 'e', 'i', 'i', 's', 's', 'i', 's'},
	  { 'l', 'i', 'e', 'i', 's', 'i', 's', 's'},
	  { 'l', 'i', 'i', 'e', 'i', 's', 's', 's'},
	  { 'l', 'l', 'l', 'i', 'e', 'i', 'i', 's'},
	  { 'l', 'l', 'i', 'l', 'i', 'e', 'i', 's'},
	  { 'l', 'i', 'l', 'l', 'i', 'i', 'e', 's'},
	  { 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'e'}
	};
		
		
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
		
		String userLevel;
		String tableLevel;
		String operation;
		
		int indexUserLevel = securityLevelIndex.indexOf(userLevel);
		int indexTableLevel = securityLevelIndex.indexOf(tableLevel);
		char queryResult = securityLevelMatrix[indexUserLevel][indexTableLevel];
		
		if(operation == "select"&& (queryResult == 'l' || queryResult == 'e')){
			//seccuss operation
		}
		else if(operation == "insert" && (queryResult == 's' || queryResult == 'e')){
			//seccuss operation
		}
		else {
			//deny,log,inform user
			request.getSession().setAttribute("error", "Operation failed!");
			LOG.debug("user operation denied!");
		}
			
		

		response.sendRedirect(request.getContextPath()+"/home/welcome.jsp");
	}


}
