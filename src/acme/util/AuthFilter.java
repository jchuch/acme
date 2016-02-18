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

public class AuthFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	public void init(FilterConfig config) throws ServletException {

		// Get init parameter
		//String testParam = config.getInitParameter("test-param");

		//Print the init parameter
		//LOG.debug("Test Param: " + testParam);
	}

	public void doFilter(ServletRequest request, ServletResponse response,
		FilterChain chain) throws java.io.IOException, ServletException {

		// Get the IP address of client machine.
		String ipAddress = request.getRemoteAddr();

		// Log the IP address and current timestamp.
		LOG.debug("IP "+ ipAddress + ", Time " + new Date().toString());

		//LOG.debug("chain="+chain+" , request="+request+" , response="+response);

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if (req.getSession()!=null &&
				req.getSession().getAttribute("user")!=null) {

			String reqUrl = req.getRequestURL()!=null ? req.getRequestURL().toString() : null;
			//LOG.debug("reqUrl="+reqUrl);


			String ctxPath = req.getContextPath();
			//LOG.debug("ctxPath="+ctxPath);


			boolean isValidPath = false;

			if (reqUrl!=null) {

				LOG.debug("reqUrl="+reqUrl);

				if (reqUrl.indexOf(ctxPath+"/pages/admin/")>=0) {

					User user = (User)req.getSession().getAttribute("user");
					if (user!=null && user.getAdmin()) {
						isValidPath = true;
					}
				} else if (reqUrl.indexOf(ctxPath+"/pages/home/")>=0) {
					User user = (User)req.getSession().getAttribute("user");
					if (user!=null && !user.getAdmin()) {
						isValidPath = true;
					}
				}

			}


			if (isValidPath) {
				// Pass request back down the filter chain
				chain.doFilter(request,response);
			} else {
				// invalid path, redirect to error page
				Message msg = new Message();
				msg.setMsgType("warning");
				msg.setMessage("Access Denied!");

				req.getSession().setAttribute("message", msg);

				resp.sendRedirect(req.getContextPath()+"/error.jsp");
				return;
			}

		} else {
			// redirect to logout
			resp.sendRedirect(req.getContextPath()+"/logout.jsp");
			return;
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
