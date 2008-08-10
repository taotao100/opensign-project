package org.owasp.oss.httpserver;

import java.util.List;

import org.apache.log4j.Logger;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.servlet.ServletHolder;
import org.owasp.oss.ca.UserManager;
import org.owasp.oss.ca.model.User;
import org.owasp.oss.util.JettyLogger;

public class OSSHttpServer {
	
	private static Logger log = Logger.getLogger(OSSHttpServer.class);

	private static final int DEFAULT_SERVER_PORT = 8080;

	private static OSSHttpServer _instance = new OSSHttpServer();

	private Server _server;
	private Context _contextServlets;

	public static OSSHttpServer getInstance() {
		return _instance;
	}
	
	public void registerOsResource(String resourceName) {		
		log.info("Registering OpenSign resource: " + resourceName);
		_contextServlets.addServlet(new ServletHolder(
				new OpenSignResourceServlet()), "/" + resourceName);	
	}

	public void start() throws Exception {
		log.info("OPENSIGN SERVER starting ...");

		// Redirect Jetty logging:
		System.setProperty("org.mortbay.log.class","org.owasp.oss.util.JettyLogger");		
		org.mortbay.log.Log.setLog(new JettyLogger());
		
		_server = new Server(DEFAULT_SERVER_PORT);
		
		_contextServlets = new Context(_server, "/", Context.SESSIONS);
		_contextServlets.setResourceBase("www");
		_contextServlets.addServlet(new ServletHolder(
				new DefaultServlet()), "/style.css");
		_contextServlets.addServlet(new ServletHolder(
				new HomeServlet()), "/");
		_contextServlets.addServlet(new ServletHolder(
				new OpenSignResourceServlet()), "/root");
		_contextServlets.addServlet(new ServletHolder(new LoginServlet()),
				"/login");
		_contextServlets.addServlet(new ServletHolder(new LogoutServlet()),
				"/logout");
		_contextServlets.addServlet(new ServletHolder(new CsrServlet()),
		"/csr");
		_contextServlets.addServlet(new ServletHolder(new RegisterServlet()),
		"/register");
		_contextServlets.addServlet(new ServletHolder(new EditUserProfileServlet()),
		"/edit_profile");		
		
		
		// During creation of the UserManager all resources are registered
		List<User> userList = UserManager.getInstance().getAllUsers();

		_server.start();
	}

	public void stop() throws Exception {
		log.info("OPENSIGN SERVER stopping...");
		_server.stop();
	}
}
