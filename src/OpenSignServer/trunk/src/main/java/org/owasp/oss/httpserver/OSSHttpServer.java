package org.owasp.oss.httpserver;

import java.util.Iterator;
import java.util.List;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.owasp.oss.ca.User;
import org.owasp.oss.ca.UserManager;

public class OSSHttpServer {

	private static final int DEFAULT_SERVER_PORT = 8080;

	private static OSSHttpServer _instance = new OSSHttpServer();

	private Server _server;

	public static OSSHttpServer getInstance() {
		return _instance;
	}

	public void start() throws Exception {

		_server = new Server(DEFAULT_SERVER_PORT);

		Context contextDoc = new Context(_server, "/", Context.SESSIONS);
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase("www");
		HandlerCollection hw = new HandlerCollection();
		hw.addHandler(resourceHandler);
		hw.addHandler(new DefaultHandler());
		contextDoc.setHandler(resourceHandler);

		Context contextServlets = new Context(_server, "/", Context.SESSIONS);
		contextServlets.addServlet(new ServletHolder(
				new OpenSignResourceServlet()), "/root");
		contextServlets.addServlet(new ServletHolder(new LoginServlet()),
				"/login");
		contextServlets.addServlet(new ServletHolder(new LogoutServlet()),
				"/logout");
		contextServlets.addServlet(new ServletHolder(new CsrServlet()),
		"/csr");
		
		List<User> userList = UserManager.getInstance().getAllUsers();
		Iterator<User> iter = userList.iterator();
		while (iter.hasNext())
			contextServlets.addServlet(new ServletHolder(
					new OpenSignResourceServlet()), "/root/" +iter.next().getUserName());

		// String loginConfFile =
		// Configuration.getInstance().getConfigurationFileAndPath
		// ("JAAS_CONF_FILE");
		// if (loginConfFile == null)
		// throw new Exception("No login conf file available");

		// System.setProperty("java.security.auth.login.config", loginConfFile);
		// //
		// SecurityHandler securityHandler = new SecurityHandler();
		//		
		// JAASUserRealm userRealm = new JAASUserRealm("xyzrealm");
		// userRealm.setLoginModuleName("xyz");
		//		
		// securityHandler.setUserRealm(userRealm);

		// PropertyFileLoginModule propertyFileLoginModule = new
		// PropertyFileLoginModule();
		// propertyFileLoginModule.loadProperties(Configuration.getInstance().
		// getConfigurationFileAndPath("LOGIN_FILE"));

		// _server.setUserRealms(new UserRealm[]{userRealm});

		// XmlConfiguration configuration = new XmlConfiguration(
		// new FileInputStream(Configuration.getInstance().getFilePath(
		// "JETTY_CONF_FILE")));
		// configuration.configure(server);

		// Context context = new Context(server,"/no",Context.SESSIONS);
		// context.addServlet(new ServletHolder(new HelloServlet("Ciao")),
		// "/*");

		// Server server = new Server(DEFAULT_SERVER_PORT);
		// Context contextDoc = new Context(server, "/doc",
		// Context.SESSIONS|Context.NO_SECURITY);
		//		
		// ResourceHandler resourceHandler = new ResourceHandler();
		// resourceHandler.setResourceBase("www");
		// contextDoc.setHandler(resourceHandler);
		//		
		// Context root = new Context(server,"/",Context.SESSIONS);
		// ServletHolder holder = new ServletHolder(new HelloServlet());
		// holder.setName("test");
		// root.addServlet(holder, "/ca");

		// HandlerCollection handlerCollection = new HandlerCollection();
		// ContextHandlerCollection contextHandlerCollection = new
		// ContextHandlerCollection();
		// //Context contextDoc = new Context(server, "/doc",
		// Context.SESSIONS|Context.NO_SECURITY);
		// ContextHandler contextHandler = new ContextHandler();
		// contextHandler.setContextPath("/*");
		// contextHandler.setResourceBase("www");
		// contextHandler.setHandler(new ResourceHandler());
		// contextHandlerCollection.setHandlers(new Handler[]{contextHandler});
		// handlerCollection.addHandler(contextHandlerCollection);
		// server.addHandler(handlerCollection);

		// context.setResourceBase("www");
		// ServletHandler servletHandler = new ServletHandler();
		// servletHandler.addServlet(holder)
		// context.addServlet(new ServletHolder(new HelloServlet("Ciao")),
		// "/*");
		//		
		// handlerCollection.setHandlers(new Handler[]{new ResourceHandler()});
		// context.addHandler(handlerCollection);

		// HandlerCollection handlerCollection = new HandlerCollection();
		// RequestLogHandler requestLogHandler = new RequestLogHandler();
		// DefaultHandler defaultHandler = new DefaultHandler();

		_server.start();
	}

	public void stop() throws Exception {
		_server.stop();
	}
}
