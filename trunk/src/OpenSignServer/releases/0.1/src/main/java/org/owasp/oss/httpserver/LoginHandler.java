package org.owasp.oss.httpserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.owasp.oss.httpserver.HttpResponse.MimeType;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpPrincipal;

public class LoginHandler implements HttpHandler {

	public void handle(HttpExchange exchange) throws IOException {

		try {
			HttpRequest req = HttpRequest.create(exchange);
			HttpResponse resp = HttpResponse.create(exchange);

			HttpPrincipal p = req.getHttpPrincipal();
			String userName = p.getUsername();

			resp.send(new ByteArrayInputStream(("User: <b>"
					+ userName + "</b> is logged in!").getBytes()),
					MimeType.HTML);

		} catch (HttpHandlerException e) {
			e.printStackTrace();
		}
	}

}
