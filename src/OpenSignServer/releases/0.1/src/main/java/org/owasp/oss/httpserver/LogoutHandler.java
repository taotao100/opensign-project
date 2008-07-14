package org.owasp.oss.httpserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.owasp.oss.httpserver.HttpResponse.ErrorType;
import org.owasp.oss.httpserver.HttpResponse.MimeType;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpPrincipal;

public class LogoutHandler implements HttpHandler {

	public void handle(HttpExchange exchange) throws IOException {
		
		try {
			HttpRequest req = HttpRequest.create(exchange);
			HttpResponse resp = HttpResponse.create(exchange);
			
			String logoutPage = "<head><title>Logout</title></head><body><p><h1>Logout successfully</h1></p><hr></body></html>";
			String sessionId = req.getParameterValue("sessionId");
			if (sessionId != null) {
				SessionManager.getInstance().removeSession(sessionId);
				resp.setCookie("sessionId=" + sessionId + "; expires=Wednesday, 09-Nov-99 23:12:40 GMT");
			}
			
			resp.send(new ByteArrayInputStream(logoutPage.getBytes()), MimeType.HTML);			

		} catch (HttpHandlerException e) {
			e.printStackTrace();
			HttpResponse.sendErrorPage(ErrorType.SERVICE_UNAVAILABLE, exchange);
		}		
	}
}
