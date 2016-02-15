package acme.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSPLogger {

	private static final Logger LOG = LoggerFactory.getLogger("acme");

	private String loggerName = "JSPLogger";

	public JSPLogger(String name) {
		this.loggerName = name;
	}

	public void debug(Object o) {
		LOG.debug(loggerName, o);
	}

	public void error(Object o, Throwable t) {
		LOG.error(loggerName, o, t);
	}

}
