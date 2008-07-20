package org.owasp.oss.util;

import org.mortbay.log.Logger;

public class JettyLogger implements Logger {

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(JettyLogger.class);

	@Override
	public void debug(String msg, Throwable th) {
	}

	@Override
	public void debug(String msg, Object arg0, Object arg1) {		
	}

	@Override
	public Logger getLogger(String name) {
		return null;
	}

	@Override
	public void info(String msg, Object arg0, Object arg1) {
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public void setDebugEnabled(boolean enabled) {
	}

	@Override
	public void warn(String msg, Throwable th) {
		log.warn(msg, th);
	}

	@Override
	public void warn(String msg, Object arg0, Object arg1) {
		log.warn(msg);
	}

}
