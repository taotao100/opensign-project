/**
 * 
 */
package org.owasp.oss.httpserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class serves the index of the web portal
 */
public class HomeServlet extends OSSBaseServlet {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		load(req, resp);
		_title = "home";
		_content = "<h2>Welcome to the OpenSign server project!</h2>" +
			"<br />" +			
			"<br />" +
				"<p>Get the CA root certificate here:" + 
					"<ul><a href=\"root?property=cert\"> Binary </a></ul>" + 
					"<ul><a href=\"root?property=cert&responseFormat=PEM\"> PEM </a></ul>" +
				"</p>" +
			"<br />" +
				"<p>Additional sites:" + 					
					"<ul><a href=\"login\"> login </a></ul>" +
					"<ul><a href=\"logout\"> logout </a></ul>" +
					"<ul><a href=\"register\"> register </a></ul>" +
					"<ul><a href=\"csr\"> certificate sign request (protected)</a></ul>" +
					"<ul><a href=\"settings\"> settings (protected)</a></ul>" +
				"</p>";		  
		send();
	}
	

}
