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

public class OpenSignResourceServlet extends HttpServlet {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String path = req.getPathInfo();
		
		path = req.getPathTranslated();
		
		path = req.getQueryString();
		
		path = req.getServletPath();
		
		path = req.getRequestURI();				
		
		String property = req.getParameter("property");
		
		if (property == null) {
			// Send ressource profile
			
			HttpSession session = req.getSession();
			User user = (User)session.getAttribute("userName");
			String name = (user == null)? "guest" : user.getUserName();
			
			PrintWriter respBody = resp.getWriter();
			respBody.write("<p><b>Profile</b></p>");
			respBody.write(name + " @ " + path);
			respBody.flush();
			return;
			
		}
		if (property.equals("cert")){
			try {
								
				String certName = (path.charAt(0) == '/')? path.substring(1) : path;
				CertificationAuthority ca =	CertificationAuthority.getInstance();
				Certificate cert = ca.getCertificate(certName);
				
				if (cert == null) {
					resp.sendError(HttpServletResponse.SC_FORBIDDEN);
					return;
				}					
				
				resp.setStatus(HttpServletResponse.SC_OK);
				ServletOutputStream respBody = resp.getOutputStream();			
				
				 String format = req.getParameter("responseFormat");
				    if (format != null){
				    	if (format.equals("PEM")) {
				    		resp.setContentType("text/plain");
				    		respBody.write(ca.certificateToPEM(cert));
				    		respBody.flush();
				    		return;
				    	}
				    }
				    	        
				    respBody.write(cert.getEncoded());	
				    respBody.flush();
			} catch (CertificateEncodingException e) {				
				e.printStackTrace();
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (CertificationAuthorityException e) {
			    e.printStackTrace();
			    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}			
			
		} else 
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
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

		try {
			String csr = req.getParameter("csr");
			req.getUserPrincipal();
			if (csr == null) {
				// Check if only binary data is sent (client app)
				InputStream bodyStream = req.getInputStream();
				if (bodyStream == null){
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
					
				ByteArrayOutputStream bodyByteStream = new ByteArrayOutputStream();;
				int current = -1;
				while ((current = bodyStream.read()) != -1)
					bodyByteStream.write(current);
				
				csr = new String(bodyByteStream.toByteArray());				
			}

			CertificationAuthority ca = CertificationAuthority.getInstance();
			Certificate cert = ca.processCsr(csr, "user1");
				        
	        resp.setStatus(HttpServletResponse.SC_OK);
	        ServletOutputStream respBody = resp.getOutputStream();
	        
	        String format = req.getParameter("responseFormat");
	        if (format != null){
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
