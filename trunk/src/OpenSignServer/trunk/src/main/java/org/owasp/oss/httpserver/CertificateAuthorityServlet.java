package org.owasp.oss.httpserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class CertificateAuthorityServlet extends OSSBaseServlet {

	private static Logger log = Logger
			.getLogger(CertificateAuthorityServlet.class);

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

		_title = "certificate authority";

		_content = "<p>The certificate authority issues certificate for users having the corresponding privileges. This service may be used online at <a href=\"/csr\">csr</a> or by use of the OpenSign client application.<br />"
				+ "Furthermore, the certificate authority allows concerned users to brows the X.509 hierarchy starting at <a href=\"/root\">root</a> as the top-level issuer.</p>";

		_leftMenu = "ca services:<br />"
				+ "<span id=\"menu_link\"><a href=\"/root\">root</a></span><br />"
				+ "<span id=\"menu_link\"><a href=\"/csr\">csr</a></span>";

		send();
	}
}
