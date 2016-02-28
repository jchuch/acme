package acme.util;

import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.dbmodel.User;
import acme.model.Message;

/**
 * if user object is in session, redirect the user to his/her dashboard page
 * @author justinchu
 *
 */

public class LoginFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub

	}


	public void doFilter(ServletRequest request, ServletResponse response,
		FilterChain chain) throws java.io.IOException, ServletException {

		// Get the IP address of client machine.
		String ipAddress = request.getRemoteAddr();

		// Log the IP address and current timestamp.
		LOG.debug("IP "+ ipAddress + ", Time " + new Date().toString());

		//LOG.debug("chain="+chain+" , request="+request+" , response="+response);

		//boolean isGoingToIndexPage = false;

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if (req.getSession()!=null &&
				req.getSession().getAttribute("user")!=null) {

			try {
				User user = (User)req.getSession().getAttribute("user");
				if (user.getAdmin()) {
					resp.sendRedirect(req.getContextPath()+"/pages/admin/dashboard.jsp");
					return;
				} else {
					resp.sendRedirect(req.getContextPath()+"/pages/home/normal_user_dashboard.jsp");
					return;
				}
			} catch(Exception e) {
				LOG.error("LoginFilter - user error!", e);
				//isGoingToIndexPage = true;
			}

		}

		// Pass request back down the filter chain
		chain.doFilter(request,response);
	}


	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
