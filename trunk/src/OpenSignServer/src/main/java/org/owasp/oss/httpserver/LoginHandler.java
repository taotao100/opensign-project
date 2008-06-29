package org.owasp.oss.httpserver;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class LoginHandler implements HttpHandler {

	public void handle(HttpExchange exchange) throws IOException {

		exchange.getResponseHeaders().add("Content-Type", "text/html");
		String errorPage = "<head><title>Error</title></head><body><p><h1>Login successfully</h1></p><hr></body></html>";
		exchange.sendResponseHeaders(100, errorPage.length());
		exchange.getResponseBody().write(errorPage.getBytes());
		exchange.getResponseBody().close();

	}

}
