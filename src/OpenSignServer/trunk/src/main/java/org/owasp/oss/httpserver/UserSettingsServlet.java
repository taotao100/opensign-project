package org.owasp.oss.httpserver;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.oss.ca.UserManager;
import org.owasp.oss.ca.model.User;

public class UserSettingsServlet extends OSSBaseServlet {

	private String buildSubEntitiesBlock() {
		List<User> subEntitiesList = UserManager.getInstance()
				.getAllSubEntities(_user.getResourceName());
		String subEntities;
		subEntities = "sub-entities:<br />";
		subEntities += "<form name=\"subEntities\" action=\"settings\" method=\"post\">";
		subEntities += "<input type=\"hidden\" name=\"sub\" value=\"1\">";
		subEntities += "<table class=\"issuerSubEntities\">\r\n";
		subEntities += "<tr><th>resource name</th><th>approve</th><th>issuer</th></tr>";

		Iterator<User> iter = subEntitiesList.iterator();
		while (iter.hasNext()) {
			User user = iter.next();
			subEntities += "<tr class=\"row\">";
			subEntities += "<td<span id=\"menu_link\"><a href=\""
					+ user.getResourceName() + "\">" + user.getResourceName()
					+ "</span></td/>";

			subEntities += "<td align=center><input name=\"approve_"
					+ user.getUserName() + "\" id=\"tableCheckbox\" type=\"checkbox\" ";
			if (user.isApproved())
				subEntities += "checked";
			subEntities += " ></td>";

			subEntities += "<td align=center><input name=\"issuer_"
					+ user.getUserName() + "\" id=\"tableCheckbox\" type=\"checkbox\" ";
			if (user.isIssuer())
				subEntities += "checked";
			subEntities += " ></td>";

			subEntities += "</tr>\r\n";
		}

		subEntities += "<tr><td colspan=\"3\" align=\"center\"><input type=\"submit\" value=\"submit\" id=\"button\"></td></tr>\r\n";
		subEntities += "</table>\r\n";

		return subEntities;

	}

	private void buildForms() {

		if (_content == null)
			_content = "profile settings:<br />";
		else
			_content += "profile settings:<br />";

		_content += "<form name=\"settings\" action=\"settings\" method=\"post\">"
				+ "<input type=\"hidden\" name=\"profile\" value=\"1\">"
				+ "<table>"
				+ "<tr><td>user name</td><td>"
				+ _user.getUserName()
				+ "</td></tr>"
				+ "<tr><td>first name</td><td><input type=\"text\" name=\"first_name\" value=\"";

		if (_user.getFirstName() != null)
			_content += _user.getFirstName();

		_content += "\"></td></tr>"
				+ "<tr><td>last name</td><td><input type=\"text\" name=\"last_name\" value=\"";

		if (_user.getLastName() != null)
			_content += _user.getLastName();

		_content += "\"></td></tr>"
				+ "<tr><td>password</td><td><input type=\"text\" name=\"password\" value=\"";

		if (_user.getPassword() != null)
			_content += _user.getPassword();

		_content += "\"></td></tr>"
				+ "<tr><td>email address</td><td><input type=\"text\" name=\"email_addr\" value=\"";
		if (_user.getEmailAddress() != null)
			_content += _user.getEmailAddress();

		_content += "\"></td></tr>"
				+ "<tr><td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"submit\" id=\"button\"></td></tr>"
				+ "</table></form><br />";

		if (_user.isIssuer())
			_content += buildSubEntitiesBlock();
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

		_title = "settings";

		if (!isUserSet())
			return;

		buildForms();

		send();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.load(req, resp);

		_title = "settings";

		if (!isUserSet())
			return;

		HttpSession session = req.getSession();

		if (req.getParameter("profile") != null) {

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

		} else if (req.getParameter("sub") != null) {

			List<User> subEntitiesList = UserManager.getInstance()
					.getAllSubEntities(_user.getResourceName());

			Iterator<User> iter = subEntitiesList.iterator();
			UserManager um = UserManager.getInstance();
			while (iter.hasNext()) {
				User subUser = iter.next();

				if (req.getParameter("approve_" + subUser.getUserName()) == null
						&& subUser.isApproved() == true) {
					subUser.setApproved(false);
					um.storeUser(subUser);
				} else if (req.getParameter("approve_" + subUser.getUserName()) != null
						&& subUser.isApproved() == false) {
					subUser.setApproved(true);
					um.storeUser(subUser);
				}
				
				if (req.getParameter("issuer_" + subUser.getUserName()) == null
						&& subUser.isIssuer() == true) {
					subUser.setIssuer(false);
					um.storeUser(subUser);
				} else if (req.getParameter("issuer_" + subUser.getUserName()) != null
						&& subUser.isIssuer() == false) {
					subUser.setIssuer(true);
					um.storeUser(subUser);
				}
			}
		}

		_content = "<div id=\"notification\">data successfully stored</div>";

		buildForms();

		send();
	}

}
