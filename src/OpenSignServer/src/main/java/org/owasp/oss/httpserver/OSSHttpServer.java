package org.owasp.oss.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.owasp.oss.Configuration;
import org.owasp.oss.ca.CaHandler;
import org.owasp.oss.ca.CsrHandler;
import org.owasp.oss.ca.User;
import org.owasp.oss.ca.UserManager;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

/**
 * OpenSign http-server
 */
public class OSSHttpServer {
	private static Logger log = Logger.getLogger(OSSHttpServer.class);

	private static final String DEFAULT_SERVER_PORT = "8080";

	private static final int MAX_CONNECTIONS = 10;

	private static OSSHttpServer _ossHttpServer = new OSSHttpServer();

	private HttpServer _httpServer = null;

	private OSSHttpServer() {

	}

	static public OSSHttpServer getInstance() {
		return _ossHttpServer;
	}

	public void start() throws IOException {
		
		String serverPort = Configuration.getInstance().getValue("OSS_PORT");
		if (serverPort == null)
			serverPort = DEFAULT_SERVER_PORT;
		
		int port = Integer.parseInt(serverPort);
		
		_httpServer = HttpServer.create(new InetSocketAddress(port),
				MAX_CONNECTIONS);
		
		log.info("OpenSign Server started on port: " + serverPort);
		
		HttpContext httpContext = null;
		
		httpContext = _httpServer.createContext("/", new FileHandler());
		log.info("Resource added: /");		
		
		httpContext = _httpServer.createContext("/login", new LoginHandler());
		log.info("Resource added: /login");		
		//httpContext.setAuthenticator(new BasicAuthenticatorImpl("welcome"));	
		httpContext.setAuthenticator(new OSSAuthenticator());
		
		httpContext = _httpServer.createContext("/logout", new LogoutHandler());
		log.info("Resource added: /logout");			
		
		httpContext = _httpServer.createContext("/ca", new CaHandler());
		log.info("Resource added: /ca");			
		
		httpContext = _httpServer.createContext("/ca/csr", new CsrHandler());
		log.info("Resource added: /ca/csr");	
		
		httpContext = _httpServer.createContext("/debug", new DebugHandler());
		log.info("Resource added: /debug");		
		
		// add user-resources
		
		List<User> ul = UserManager.getInstance().getAllUsers();
		Iterator<User> i = ul.iterator();
		User u = null;
		while (i.hasNext()) {
			u = i.next();
			_httpServer.createContext("/" + u.getUserName(), new UserHandler());	
			log.info("Resource added: /" + u.getUserName());
		}		
		
		_httpServer.setExecutor(null);
		_httpServer.start();

	}

	public void stop() {		
		_httpServer.stop(0);
		log.info("OpenSign Server stopped");
	}
}
