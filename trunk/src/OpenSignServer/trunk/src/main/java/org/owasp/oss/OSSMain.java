package org.owasp.oss;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.owasp.oss.ca.CertificationAuthority;
import org.owasp.oss.ca.dao.DaoFactory;
import org.owasp.oss.ca.dao.UserDao;
import org.owasp.oss.ca.model.User;
import org.owasp.oss.crypto.Crypto;
import org.owasp.oss.httpserver.OSSHttpServer;

/**
 * OpenSign Server main class
 */
public class OSSMain {
	private static Logger log = Logger.getLogger(OSSMain.class);
	
	private static Configuration conf = Configuration.getInstance();
	
	private static final SessionFactory sessionFactory = new AnnotationConfiguration()
	.configure().buildSessionFactory();

	/**
	 * This method sets up the logger
	 */
	static public void configureLogger() {
		
		String logConfFile = conf.getConfigurationFileAndPath("LOG_CONF_FILE");
		if (new File(logConfFile).exists())
			PropertyConfigurator.configure(logConfFile);
		log.info("Logger configured");		
	}

	/**
	 * Debug method, which prints system properties to the console
	 */
	public void printSystemInformation() {
		System.out
				.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out
				.println("++++++++++++++++++++++++LOGGER NAMES++++++++++++++++++++++++++++++");
		System.out
				.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// LogManager log = LogManager.getLogManager();
		// Enumeration<String> en = log.getLoggerNames();
		// while (en.hasMoreElements())
		// System.out.println(en.nextElement());

		System.out
				.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out
				.println("+++++++++++++++++++++SECURITY PROVIDERS+++++++++++++++++++++++++++");
		System.out
				.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			Provider provider = providers[i];
			System.out.println("Provider name: " + provider.getName());
			System.out.println("Provider information: " + provider.getInfo());
			System.out.println("Provider version: " + provider.getVersion());
			Set entries = provider.entrySet();
			Iterator iterator = entries.iterator();
			while (iterator.hasNext()) {
				System.out
						.println("===========================================");
				System.out.println("Property entry: " + iterator.next());
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		boolean init = true;

		//OSSMain.configureLogger();		

		Security.addProvider(new BouncyCastleProvider());
		Crypto.getInstance();		

		OSSHttpServer openSignServer = OSSHttpServer.getInstance();

		try {
			openSignServer.start();
			CertificationAuthority.getInstance().createRoot();
			System.out.println("Press enter to quit server");
			new BufferedReader(new InputStreamReader(System.in)).readLine();
			openSignServer.stop();

		} catch (Exception e) {
			log.error("OpenSign server terminated in an unexpected manner", e);
		}

	}

}
