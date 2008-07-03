package org.owasp.oss.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

/**
 * This class is used to build and send the http-response
 */
public class HttpResponse {

	protected HttpExchange _exchange;

	protected Headers _headers;

	protected int _status = 200;

	public static enum MimeType {
		HTML, CSS, TEXT
	}

	public static enum ErrorType {
		FORBIDDEN, SERVICE_UNAVAILABLE
	}

	public static HttpResponse create(HttpExchange exchange) {
		return new HttpResponse(exchange);
	}

	public static void sendErrorPage(ErrorType errorType, HttpExchange exchange)
			throws IOException {

		String errorPage = null;

		if (errorType == ErrorType.FORBIDDEN) {
			errorPage = "<head><title>Error</title></head><body><p><h1>Forbidden</h1></p><hr></body></html>";
			exchange.sendResponseHeaders(403, errorPage.length());
		} else if (errorType == ErrorType.SERVICE_UNAVAILABLE) {
			errorPage = "<head><title>Error</title></head><body><p><h1>Service Unavailable</h1></p><hr></body></html>";
			exchange.sendResponseHeaders(503, errorPage.length());
		} else {
			errorPage = "<head><title>Error</title></head><body><p><h1> Internal Server Error</h1></p><hr></body></html>";
			exchange.sendResponseHeaders(500, errorPage.length());
		}
		exchange.getResponseBody().write(errorPage.getBytes());
		exchange.getResponseBody().close();
	}

	public static void sendDebugPage(HttpExchange exchange) throws IOException {
		
		// only plain text sent
		Headers responseHeaders = exchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "text/plain");

		// response is OK (200)
		exchange.sendResponseHeaders(200, 0);

		OutputStream responseBody = exchange.getResponseBody();

		// Print all request headers to HTTP response
		Headers requestHeaders = exchange.getRequestHeaders();
		Set<String> keySet = requestHeaders.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			List values = requestHeaders.get(key);
			String s = key + " = " + values.toString() + "\n";
			responseBody.write(s.getBytes());
		}

		Map<String, Object> as = exchange.getHttpContext().getAttributes();
		keySet = as.keySet();
		iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			String s = key + " = " + as.get(key) + "\n";
			responseBody.write(s.getBytes());
		}

		responseBody.close();

	}

	private HttpResponse(HttpExchange exchange) {
		_headers = exchange.getResponseHeaders();
		_exchange = exchange;
	}

	public void setMimeType(MimeType type) {
		if (type == MimeType.HTML)
			_headers.add("Content-Type", "text/html");
		else if (type == MimeType.CSS)
			_headers.add("Content-Type", "text/css");
		else if (type == MimeType.TEXT)
			_headers.add("Content-Type", "text/plain");
	}

	public void send(InputStream body) throws IOException {

		long bodyLength = body.available();
		_exchange.sendResponseHeaders(_status, bodyLength);

		OutputStream bodyStream = _exchange.getResponseBody();
		while (body.available() > 0)
			bodyStream.write(body.read());
		bodyStream.close();
	}
}
