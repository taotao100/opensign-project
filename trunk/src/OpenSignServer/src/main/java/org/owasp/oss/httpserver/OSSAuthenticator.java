/**
 * 
 */
package org.owasp.oss.httpserver;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.owasp.oss.ca.UserManager;
import org.owasp.oss.httpserver.HttpResponse.ErrorType;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

/**
 * @author Phil
 *
 */
public class OSSAuthenticator extends Authenticator {
	private static Logger log = Logger.getLogger(OSSAuthenticator.class);

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.Authenticator#authenticate(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public Result authenticate(HttpExchange exchange) {
				
			log.info("http request received");

			try {
				HttpRequest req = HttpRequest.create(exchange);
				HttpResponse resp = HttpResponse.create(exchange);

				if (req.isGET()) {
					String sessionId = req.getParameterValue("sessionId");					
					if (sessionId != null) {
						HttpPrincipal principal = SessionManager.getInstance().getHttpPrincipal(sessionId);
						if (principal != null)
							return new Authenticator.Success(principal);
					}

						resp.sendErrorPage(ErrorType.FORBIDDEN, exchange);						
						return new Authenticator.Failure(-1);

				} else if (req.isPOST()) {
					
					String userName = req.getParameterValue("username");
					String password = req.getParameterValue("password");
										
					
					if (UserManager.getInstance().checkCredentials(userName, password)){
						
						String sessionId = SessionManager.generateSessionId();
						//TODO: set expire date
						resp.setCookie("sessionId=" + sessionId);
						HttpPrincipal principal = new HttpPrincipal(userName, "oss");
						SessionManager.getInstance().addSession(sessionId, principal);
						
						return new Authenticator.Success(principal);						
					}						
					
					resp.sendErrorPage(ErrorType.SERVICE_UNAVAILABLE, exchange);	
					return new Authenticator.Failure(-1);					
				}
			} catch (HttpHandlerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return new Authenticator.Failure(-1);
	}
}