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
					+ "<textarea cols=\"60\" rows=\"15\" name=\"csr\">"
					+ "-----BEGIN NEW CERTIFICATE REQUEST-----\r\n"
					+ "MIIBmDCCAQECAQAwWDELMAkGA1UEBhMCQVQxDTALBgNVBAgTBHRlc3QxDTALBgNVBAcTBHRlc3Qx\r\n"
					+ "DTALBgNVBAoTBHRlc3QxDTALBgNVBAsTBHRlc3QxDTALBgNVBAMTBHRlc3QwgZ8wDQYJKoZIhvcN\r\n"
					+ "AQEBBQADgY0AMIGJAoGBAJIPmwapJe8qp3d2/iS7ScdUXa+55AfF656GC8hhTq5oXvh2phacBXzf\r\n"
					+ "EsdZy4s50qb2X8TG3AeaMYADgOCpsOjsliqjSbrEy2KNMKzQsQSCXCJh4Bealm6pNwPMCFXf+QE2\r\n"
					+ "lEpPZih/q9FprzynHxW7S6XYgCJHuGnsJBHDeY83AgMBAAGgADANBgkqhkiG9w0BAQUFAAOBgQAP\r\n"
					+ "8BMyNMdLKAWcFtbFnFgD6qs9IkdM9aFHgbnl3BDmM7tAVaCSNvIyHlvDrjs+m6tGyac7XaFne453\r\n"
					+ "1VVRJxICQo3qBRkqZadeJSCW+oNcz2jFqDk2KNECzErhpt1WuOglw1JT6e2ieb0f6dWSg8jdo0zy\r\n"
					+ "5dV9A3sN3wILUvmPrw==\r\n"
					+ "-----END NEW CERTIFICATE REQUEST-----" + "</textarea>"
					+ "<br />" + "<input type=\"submit\" value=\"submit\">"
					+ "</form>";
		}
		send();
	}

}
