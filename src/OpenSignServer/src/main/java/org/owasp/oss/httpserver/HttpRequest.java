package org.owasp.oss.httpserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class HttpRequest {

	private static enum Method {
		GET, POST, PUT, DELETE
	}

	private Method _method = null;

	private String _path = null;
	
	private byte[] _bodyBytes = null;
	
	Map<String, String> _parameters = null;

	private HttpRequest(Method method, String path, byte[] bodyBytes, Map<String, String> parameters) {
		_method = method;
		_path = path;
		_bodyBytes = bodyBytes;
		_parameters = parameters;
	}

	static public HttpRequest create(HttpExchange exchange)
			throws org.owasp.oss.httpserver.HttpHandlerException, IOException {

		// Determining method
		Method method;

		if (exchange.getRequestMethod().equals("GET"))
			method = Method.GET;
		else if (exchange.getRequestMethod().equals("POST"))
			method = Method.POST;
		else if (exchange.getRequestMethod().equals("PUT"))
			method = Method.PUT;
		else if (exchange.getRequestMethod().equals("DELETE"))
			method = Method.DELETE;
		else
			throw new org.owasp.oss.httpserver.HttpHandlerException(
					"Could not create HttpRequest, no vaild method");

		Headers headers = exchange.getRequestHeaders();

		// Parsing body
		InputStream body = exchange.getRequestBody();
		
		Map<String, String> parameters = null;

		String bodyLenStr = headers.getFirst("Content-Length");
		
		byte[] bodyBytes = null;
		if (bodyLenStr != null){			
			
			int bodyLen = Integer.parseInt(bodyLenStr);
			bodyBytes = new byte[bodyLen];
			ByteArrayOutputStream os = new ByteArrayOutputStream(bodyLen);
			int readByteNum = 0;
			while ((readByteNum = body.read(bodyBytes)) > 0){
				os.write(bodyBytes, 0, readByteNum);									
			}
			bodyBytes = os.toByteArray();
			parameters = HttpRequest.parseParameter(new ByteArrayInputStream(bodyBytes), bodyBytes.length);
		}
		
		// Getting requested-path information
		URI uri = exchange.getRequestURI();
		//String query = uri.getQuery();
		String path = uri.getPath();

		return new HttpRequest(method, path, bodyBytes, parameters);

	}

	static private Map<String, String> parseParameter(InputStream body,
			int bodyLength) throws HttpHandlerException, IOException {

		Map<String, String> parameters = new HashMap<String, String>();
		
		try {
						
			byte[] bodyBytes = new byte[bodyLength];
			body.read(bodyBytes);
			String bodyStr = new String(bodyBytes);
			bodyStr = bodyStr.replace("+", " ");
			StringTokenizer st = new StringTokenizer(bodyStr, "&");
			
			while (st.hasMoreElements()) {
				String current = st.nextToken();
				int index = current.indexOf("=");
				if (index > 0) {
					String key = URLDecoder.decode(current.substring(0, index),
							"UTF-8");
					String value = URLDecoder.decode(current.substring(index + 1),
							"UTF-8");
					parameters.put(key, value);
				}
			}
		} catch (UnsupportedEncodingException e) {
			throw new HttpHandlerException(e);
		} 
		
		return parameters;
	}
	
	public String getParameterValue(String key){
		return _parameters.get(key);
	}
	
	public byte[] getBodyBytes() {
		return _bodyBytes;
	}

	public String getPath() {
		return _path;
	}

	public boolean isPathEmpty() {
		return (_path.length() <= 1);
	}

	public boolean isGET() {
		return (_method == Method.GET);
	}

	public boolean isPOST() {
		return (_method == Method.POST);
	}

	public boolean isPUT() {
		return (_method == Method.PUT);
	}

	public boolean isDELETE() {
		return (_method == Method.DELETE);
	}
}
