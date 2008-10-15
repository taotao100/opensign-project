package org.owasp.oss.httpserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.owasp.oss.ca.CertificationAuthority;

public class CsrServlet extends OSSBaseServlet {

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
		
		_title = "issue csr";
		
		if (!isUserSet())
			return;		

		if (_user.isIssuer()) {
			_content = "<p>An issuer can't request a new certificate</p>";
		} else {

			_content = "<p>Issuing request to: "
					+ _user.getResourcePath()
					+ "</p>"					
					+ "<form name=\"input\" action=\""
					+ _user.getResourcePath()
					+ "\" method=\"post\">"
					+ "<p>Select response format:"
					+ "<select name=\"responseFormat\"><option>binary</option><option>PEM</option></select></p>"
					+ "<p>Enter your certification request here:</p>"
					+ "<textarea cols=\"55\" rows=\"15\" name=\"csr\">"
					+ "</textarea>"
					+ "<br />" + "<input type=\"submit\" value=\"submit\">"
					+ "</form>";
		}
		send();
	}

}
