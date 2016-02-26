package acme.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dao.LogDAO;
import acme.dao.TableSecurityDAO;
import acme.dbmodel.Log;
import acme.dbmodel.TableSecurity;
import acme.dbmodel.User;
import acme.model.Message;

public class AccessController extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final long serialVersionUID = 1L;

	//private static final ArrayList<String> securityLevelIndex = new ArrayList<String>();
	private static final HashMap<String,Integer> securityLevelIdx = new HashMap<String,Integer>();
	static {
//		securityLevelIndex.add("G");
//		securityLevelIndex.add("H");
//		securityLevelIndex.add("F");
//		securityLevelIndex.add("E");
//		securityLevelIndex.add("HF");
//		securityLevelIndex.add("HE");
//		securityLevelIndex.add("FE");
//		securityLevelIndex.add("L");

		// (security level, index)
		securityLevelIdx.put("G", 0);
		securityLevelIdx.put("H", 1);
		securityLevelIdx.put("F", 2);
		securityLevelIdx.put("E", 3);
		securityLevelIdx.put("HF", 4);
		securityLevelIdx.put("HE", 5);
		securityLevelIdx.put("FE", 6);
		securityLevelIdx.put("L", 7);
	}

	/*
	the first dimension represents user security level, the second dimension represents table security level;
	return l represents that the user's security level is higher than the table's security level;
	s for lower
	e for equal;
	i for incompatible;
	*/
	private static final char[][] securityLevelMatrix = new char[][]{
		  { 'e', 's', 's', 's', 's', 's', 's', 's'}, // G
		  { 'l', 'e', 'i', 'i', 's', 's', 'i', 's'}, // H
		  { 'l', 'i', 'e', 'i', 's', 'i', 's', 's'}, // F
		  { 'l', 'i', 'i', 'e', 'i', 's', 's', 's'}, // E
		  { 'l', 'l', 'l', 'i', 'e', 'i', 'i', 's'}, // HF
		  { 'l', 'l', 'i', 'l', 'i', 'e', 'i', 's'}, // HE
		  { 'l', 'i', 'l', 'l', 'i', 'i', 'e', 's'}, // FE
		  { 'l', 'l', 'l', 'l', 'l', 'l', 'l', 'e'}  // L
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

		String userLevel = currentUser.getLevel();
		LOG.debug("user level="+userLevel);


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

			} else if (parameter.toLowerCase().startsWith("insert_")) {

				tableSecId = parameter.substring("insert_".length(), parameter.length());
				LOG.debug("tableSecId="+tableSecId);

				action = 2;

				break;

			}

		}


		TableSecurity tableSec = tableSecDao.getTableSecurity(Integer.parseInt(tableSecId));
		String tableLevel = tableSec.getLevel();


		// TODO: check access
		/*System.out.println("user level:" + level);
		System.out.println("table level:" + tableLevel);*/


		Message msg = new Message();

		int indexUserLevel = -1;
		int indexTableLevel = -1;

		boolean isSecLvFound = false;
		try {
			indexUserLevel = securityLevelIdx.get(userLevel);
			indexTableLevel = securityLevelIdx.get(tableLevel);
			isSecLvFound = true;
		} catch(Exception e) {
			msg.setMsgType("danger");
			msg.setMessage("Security level Error!");
		}

		if (isSecLvFound) {
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

					// action : 1 = select
					// action : 2 = insert
					String operation = null;
					if(action==1)
						operation = "select";
					else if(action==2)
						operation ="insert";

			        Log log = new Log();
			        log.setUserId(currentUser.getId());
			        log.setOperation(operation);
			        log.setTableId(Integer.parseInt(tableSecId));
			        log.setMessage("operation failed");

			        LogDAO logDao = new LogDAO();
			        boolean isUpdateSuccess = logDao.wrLog(log);

				}
			} catch(Exception e) {
				LOG.error("check access errro!", e);
				msg.setMsgType("danger");
				msg.setMessage("Operation Error!");
			}
		}

		request.getSession().setAttribute("message", msg);

		response.sendRedirect(request.getContextPath()+"/pages/home/welcome.jsp");
	}


}
