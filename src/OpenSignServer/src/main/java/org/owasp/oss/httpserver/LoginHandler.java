package org.owasp.oss.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.Headers;
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
