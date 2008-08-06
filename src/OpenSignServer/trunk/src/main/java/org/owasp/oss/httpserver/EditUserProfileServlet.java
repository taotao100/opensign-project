package org.owasp.oss.httpserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.owasp.oss.ca.CertificationAuthority;
import org.owasp.oss.ca.UserManager;
import org.owasp.oss.ca.model.User;

public class EditUserProfileServlet extends OSSBaseServlet {

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

		this.load(req, resp);

		_title = "edit profile";

		if (!isUserSet())
			return;
				

		_content = "<p>User: "
				+ _user.getUserName()
				+ "</p>"
				+ "<form name=\"edit_profile\" action=\"edit_profile\" method=\"post\">"
				+ "<table>"
				+ "<tr><td>First name</td><td><input type=\"text\" name=\"first_name\" value=\"" +  _user.getFirstName() + "\"></td></tr>"
				+ "<tr><td>Last name</td><td><input type=\"text\" name=\"last_name\" value=\"" + _user.getLastName() + "\"></td></tr>"
				+ "<tr><td>Password</td><td><input type=\"text\" name=\"password\" value=\"" + _user.getPassword() + "\"></td></tr>"
				+ "<tr><td>Email address</td><td><input type=\"text\" name=\"email_addr\" value=\"" + _user.getEmailAddress() + "\"></td></tr>"
				+ "<tr><td></td><td><input type=\"submit\"></td></tr>"
				+ "</table></form>";

		send();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.load(req, resp);

		_title = "edit profile";

		if (!isUserSet())
			return;
		
		HttpSession session = req.getSession();
		String firstName = req.getParameter("first_name");
		String lastName = req.getParameter("last_name");
		String password = req.getParameter("password");
		String emailAddress = req.getParameter("email_addr");

		UserManager um = UserManager.getInstance();
		_user.setFirstName(firstName);
		_user.setLastName(lastName);
		_user.setPassword(password);
		_user.setEmailAddress(emailAddress);
		um.storeUser(_user);
		
		_content = "data successfully stored";
		
		send();
	}

}
