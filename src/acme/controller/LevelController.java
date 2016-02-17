package acme.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dao.UserDAO;
import acme.dbmodel.User;

public class LevelController extends HttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private static final long serialVersionUID = 1L;

	public LevelController() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int errorCount = 0;
		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
		    if(parameter.toLowerCase().startsWith("level_")) {
		    	LOG.debug("parameter="+parameter);

		    	String userIdStr = parameter.substring("level_".length(), parameter.length());
		    	LOG.debug("userIdStr="+userIdStr);

		        //String[] values = parameters.get(parameter);
		        //LOG.debug("values="+values);

		        String level = request.getParameter(parameter);
		        LOG.debug("level="+level);

		        User user = new User();
		        user.setId(Integer.parseInt(userIdStr));
		        user.setLevel(level);

		        UserDAO userDao = new UserDAO();
		        boolean isUpdateSuccess = userDao.updateLevel(user);
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

		response.sendRedirect(request.getContextPath()+"/admin/dashboard.jsp");
	}


}
