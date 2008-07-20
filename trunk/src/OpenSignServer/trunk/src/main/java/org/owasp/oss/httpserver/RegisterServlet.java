package org.owasp.oss.httpserver;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.oss.ca.CertificationAuthority;
import org.owasp.oss.ca.CertificationAuthorityException;
import org.owasp.oss.ca.UserManager;
import org.owasp.oss.ca.model.User;

public class RegisterServlet extends OSSBaseServlet {

	private String getIssuers() {
		List<User> issuerList = UserManager.getInstance().getAllIssuers();
		StringBuffer selectStr = new StringBuffer();
		
		Iterator<User> iter = issuerList.iterator();
		while (iter.hasNext())
			selectStr.append("<option>" + iter.next().getResourceName() + "</option>");
		return selectStr.toString();
	}

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

		_title = "register";

		_content = "<form name=\"login\" action=\"/register\" method=\"POST\">"
				+ "<table><tr><td>super:</td><td><select name=\"super_resource\">"
				+ getIssuers()
				+ "</select></td></tr>"
				+ "<tr><td>is issuer:</td><td><input id=\"menu_input\" name=\"is_issuer\" type=\"checkbox\"></td></tr>"
				+ "<tr><td>user name:</td><td><input id=\"menu_input\" name=\"user_name\" value=\"user1\" type=text size=40></td></tr>"
				+ "<tr><td>password:</td><td><input id=\"menu_input\" name=\"password\" value=\"123\" type=text size=40></td></tr>"
				+ "<tr><td></td><td><input id=\"menu_input\" type=\"submit\" value=\"register\"></td>"
				+ "</tr>" + "</table>" + "</form>";

		send();

	}

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

		_title = "register";

		HttpSession session = req.getSession();
		String userName = req.getParameter("user_name");
		String password = req.getParameter("password");
		String superResource = req.getParameter("super_resource");
		String isIssuer = req.getParameter("is_issuer");

		UserManager um = UserManager.getInstance();
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setResourcePath(superResource);
		user.setResourceName(superResource + "/" + userName);
		user.setIssuer(isIssuer != null);

		um.registerUser(user);

		_content = "<p>user successfully registered!<p>"
				+ "<br /><p>please log in now at: <a href=\"/login\">login</a></p>"
				+ "<br /><p>user profile at: <a href=\"" + user.getResourceName() + "\">"
				+ user.getResourceName() + "</a></p>";

		send();

	}

}
