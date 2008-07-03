package org.owasp.oss.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.owasp.oss.ca.CsrHandler;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

/**
 * OpenSign http-server
 */
public class OSSHttpServer {
	private static Logger log = Logger.getLogger(OSSHttpServer.class);

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
		
		log.info("OpenSign Server started on port: " + SERVER_PORT);
		
		HttpContext httpContext = null;
		
		httpContext = _httpServer.createContext("/", new FileHandler());
		log.info("Resource added: /");		
		
		httpContext = _httpServer.createContext("/login", new LoginHandler());
		log.info("Resource added: /login");
		httpContext.setAuthenticator(new BasicAuthenticatorImpl("welcome"));
		
		httpContext = _httpServer.createContext("/ca/csr", new CsrHandler());
		log.info("Resource added: /ca/csr");				
		
		_httpServer.setExecutor(null);
		_httpServer.start();

	}

	public void stop() {		
		_httpServer.stop(0);
		log.info("OpenSign Server stopped");
	}
}
