package acme.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dao.TableSecurityDAO;
import acme.dao.UserDAO;
import acme.dbmodel.TableSecurity;
import acme.model.Message;

public class TableSecurityLevelController extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final long serialVersionUID = 1L;

	public TableSecurityLevelController() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int errorCount = 0;
		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
			//LOG.debug("parameter="+parameter);
		    if(parameter.toLowerCase().startsWith("level_")) {

		    	String tableSecIdStr = parameter.substring("level_".length(), parameter.length());
		    	LOG.debug("tableSecIdStr="+tableSecIdStr);

		        //String[] values = parameters.get(parameter);
		        //LOG.debug("values="+values);

		        String level = request.getParameter(parameter);
		        LOG.debug("level="+level);

		        TableSecurity tableSec = new TableSecurity();
		        tableSec.setId(Integer.parseInt(tableSecIdStr));
		        tableSec.setLevel(level);

		        TableSecurityDAO tableSecDao = new TableSecurityDAO();
		        boolean isUpdateSuccess = tableSecDao.updateLevel(tableSec);
		        if (!isUpdateSuccess) {
		        	errorCount++;
		        }

		    }
		} // END, for


		// if error found, set message
		Message msg = new Message();
		if (errorCount>0) {
			msg.setMsgType("warning");
			msg.setMessage("Update Failed!");
		} else {
			msg.setMsgType("success");
			msg.setMessage("Update Success!");
		}
		request.getSession().setAttribute("message", msg);

		response.sendRedirect(request.getContextPath()+"/pages/admin/maintain_table_security.jsp");
	}


}
