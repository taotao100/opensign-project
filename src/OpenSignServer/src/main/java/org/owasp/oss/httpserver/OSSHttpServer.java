package org.owasp.oss.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

import org.owasp.oss.ca.CsrHandler;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

/**
 * OpenSign http-server
 */
public class OSSHttpServer {
	//private static Logger _log = Logger.getLogger(OSSHttpServer.class);

	private static final int SERVER_PORT = 8080;

	private static final int MAX_CONNECTIONS = 10;

	private static OSSHttpServer _ossHttpServer = new OSSHttpServer();

	private HttpServer _httpServer = null;

	private OSSHttpServer() {

	}

	static public OSSHttpServer getInstance() {
		return _ossHttpServer;
	}

	public void start() throws IOException {
		_httpServer = HttpServer.create(new InetSocketAddress(SERVER_PORT),
				MAX_CONNECTIONS);
		
		HttpContext httpContext = null;
		httpContext = _httpServer.createContext("/login", new LoginHandler());
		httpContext.setAuthenticator(new BasicAuthenticatorImpl("welcome"));
		
		httpContext = _httpServer.createContext("/ca/csr", new CsrHandler());		
		httpContext = _httpServer.createContext("/", new FileHandler());						
		_httpServer.setExecutor(null);
		_httpServer.start();

	}

	public void stop() {
		_httpServer.stop(0);
	}
}
