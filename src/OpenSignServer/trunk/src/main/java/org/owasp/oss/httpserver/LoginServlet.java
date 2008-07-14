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

public class LoginServlet extends HttpServlet {

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
		
		HttpSession session = req.getSession();
		String userName = req.getParameter("user_name");
		String password = req.getParameter("password");					
		
		if (userName != null || password != null) {
			// New login attempt
			User user = UserManager.getInstance().getUser(userName);
			if (user != null) {
				if (user.getPassword().equals(password)) {
					session.setAttribute("userName", user);
					OSSHtmlTemplate template = new OSSHtmlTemplate();
					template.setUserName(user.getUserName());
					template.setContent("Successfully logged in!");
					template.setTitle("Login");
					
					PrintWriter respBody = resp.getWriter();
					respBody.write(template.build());
					respBody.flush();
				}
			}			
		} 
		
		System.out.println(req.toString());
		resp.sendRedirect("error.html");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
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
	
		template.setContent(content);
		template.setTitle("Login");
		
		PrintWriter respBody = resp.getWriter();
		respBody.write(template.build());
		respBody.flush();
	}

	
}
