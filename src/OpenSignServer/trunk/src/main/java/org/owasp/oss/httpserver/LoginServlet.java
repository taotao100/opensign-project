package org.owasp.oss.httpserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.oss.ca.User;
import org.owasp.oss.ca.UserManager;

public class LoginServlet extends OSSBaseServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.load(req, resp);
		
		HttpSession session = req.getSession();
		String userName = req.getParameter("user_name");
		String password = req.getParameter("password");					
		
		if (userName != null || password != null) {
			// New login attempt
			User user = UserManager.getInstance().getUser(userName);
			if (user != null) {
				if (user.getPassword().equals(password)) {
					session.setAttribute("user", user);
					
					_user = user;
					_userName = userName;
					_content = "Successfully logged in!";
					_title = "Login";
					
					send();
					return;
				}
			}			
		} 
		
		resp.sendRedirect("error.html");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		load(req, resp);
		
		String content = 
		"<form name=\"login\" action=\"/login\" method=\"POST\">" +
			"<table><tr>" +
					"<td>user name:</td><td><input id=\"menu_input\" name=\"user_name\" value=\"user1\" type=text size=40></td>" +
				"</tr><tr>" +
					"<td>password:</td><td><input id=\"menu_input\" name=\"password\" value=\"123\" type=text size=40></td>" +
				"</tr><tr>" +
					"<td></td><td><input id=\"menu_input\" type=\"submit\" value=\"submit\"></td>" +
				"</tr>" +
			"</table>" +
		"</form>";
		
		OSSHtmlTemplate template = new OSSHtmlTemplate();
			
		_title = "Login";
		
		send();
	}

	
}
