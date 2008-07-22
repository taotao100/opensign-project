/**
 * 
 */
package org.owasp.oss.httpserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.oss.ca.model.User;

/**
 * This class serves as base for all other servlet's in order to provide generic
 * methods
 */
public class OSSBaseServlet extends HttpServlet {

	protected String _title;
	protected String _content;
	protected User _user;
	protected String _userName;
	private HttpServletRequest _req;
	private HttpServletResponse _resp;

	protected void load(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		_req = req;
		_resp = resp;

		_user = (User) req.getSession().getAttribute("user");
		if (_user != null) {
			_userName = _user.getUserName();
		}
	}

	protected boolean isUserSet() throws IOException {
		if (_user != null)
			return true;

		_content = "You need to log in before using this service";
		send();

		return false;
	}

	protected void send() throws IOException {
		OSSHtmlTemplate template = new OSSHtmlTemplate();

		template.setContent(_content);
		template.setTitle(_title);
		
		if (_user == null) {
			template.setUserName("guest");
		}else{
			template.setLogin(true);
			template.setUserName("<a href=\"/" + _user.getResourceName() + "\" >" + _userName + "</a>");
		}
		
		PrintWriter respBody = _resp.getWriter();
		respBody.write(template.build());
		respBody.flush();
		reset();
	}

	private void reset() {
		_content = "";
		_userName = "";
		_title = "";
	}
}
