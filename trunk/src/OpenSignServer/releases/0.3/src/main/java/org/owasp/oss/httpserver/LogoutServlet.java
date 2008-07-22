package org.owasp.oss.httpserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class LogoutServlet extends OSSBaseServlet {
	
	private static Logger log = Logger.getLogger(LogoutServlet.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		load(req, resp);

		HttpSession session = req.getSession();
		session.invalidate();
				
		_content = "Successfully logged out!";
		_title = "Logout";
		
		log.info("User " + _userName + " has logged out");
		send();
	}
}
