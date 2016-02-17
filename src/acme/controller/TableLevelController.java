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

public class TableLevelController extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final long serialVersionUID = 1L;

	public TableLevelController() {
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




		// if error found
		if (errorCount>0) {
			request.getSession().setAttribute("error", "Update failed!");
		} else {
			request.getSession().setAttribute("error", "Update success!");
		}

		response.sendRedirect(request.getContextPath()+"/admin/maintain_table_security.jsp");
	}


}
