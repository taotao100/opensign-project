package org.owasp.oss.httpserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.oss.ca.CertificationAuthority;
import org.owasp.oss.ca.CertificationAuthorityException;
import org.owasp.oss.ca.User;
import org.owasp.oss.ca.UserManager;

public class OpenSignResourceServlet extends OSSBaseServlet {
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

		String path = req.getRequestURI();

		String property = req.getParameter("property");

		CertificationAuthority ca = CertificationAuthority.getInstance();
		
		_title = "user profile";
		
		String resourceName = (path.charAt(0) == '/') ? path.substring(1)
				: path;		

		try {
			if (property == null) {
				// Send resource profile				

				_content = "Requested resource: " + resourceName;

				Certificate cert = ca.getCertificate(resourceName);
				if (cert == null)
					_content += "<br /><br /><br />Resource has no certificate";
				else
					_content += "<br /><br /><br />Certificate: <br /><br />" +
								"<span id=\"certificate\">" +
							    new String(ca.certificateToPEM(cert)) +
							    "</span>";
				

				send();
				return;

			} else if (property.equals("cert")) {

				Certificate cert = ca.getCertificate(resourceName);

				if (cert == null) {
					resp.sendError(HttpServletResponse.SC_FORBIDDEN);
					return;
				}

				resp.setStatus(HttpServletResponse.SC_OK);
				ServletOutputStream respBody = resp.getOutputStream();

				String format = req.getParameter("responseFormat");
				if (format != null) {
					if (format.equals("PEM")) {
						resp.setContentType("text/plain");
						respBody.write(ca.certificateToPEM(cert));
						respBody.flush();
						return;
					}
				}

				respBody.write(cert.getEncoded());
				respBody.flush();

			} else
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} catch (CertificationAuthorityException e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
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
		
		load(req, resp);

		try {

			_title = "Issue CSR";

			this.load(req, resp);
			if (!isUserSet())
				return;

			String resourceName = _user.getResourcePath() + "/" + _userName;

			String csr = req.getParameter("csr");

			if (csr == null) {
				// Check if only binary data is sent (client app)
				InputStream bodyStream = req.getInputStream();
				if (bodyStream == null) {
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}

				ByteArrayOutputStream bodyByteStream = new ByteArrayOutputStream();
				;
				int current = -1;
				while ((current = bodyStream.read()) != -1)
					bodyByteStream.write(current);

				csr = new String(bodyByteStream.toByteArray());
			}

			CertificationAuthority ca = CertificationAuthority.getInstance();
			Certificate cert = ca.processCsr(csr, resourceName);

			resp.setStatus(HttpServletResponse.SC_OK);
			ServletOutputStream respBody = resp.getOutputStream();

			String format = req.getParameter("responseFormat");
			if (format != null) {
				if (format.equals("PEM")) {
					resp.setContentType("text/plain");
					respBody.write(ca.certificateToPEM(cert));
					respBody.flush();
					return;
				}
			}

			respBody.write(cert.getEncoded());
			respBody.flush();

		} catch (CertificationAuthorityException e) {
			e.printStackTrace();
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		}
	}

}
