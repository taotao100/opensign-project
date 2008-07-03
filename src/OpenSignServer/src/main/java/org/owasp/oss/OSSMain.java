package org.owasp.oss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Provider;
import java.security.Security;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.owasp.oss.crypto.Crypto;
import org.owasp.oss.crypto.OSSKeyStore;
import org.owasp.oss.httpserver.OSSHttpServer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * OpenSign Server main class
 */
public class OSSMain {
	private static Logger log = Logger.getLogger(OSSMain.class);
	private static Configuration conf = null;

	/**
	 * This method sets up the logger
	 */
	static public void configureLogger() {

		//BasicConfigurator.configure();
		PropertyConfigurator.configure("log4j.properties");
		log.info("config logger");
		log.debug("ASDF");
		log.error("ASDFASDF");
		// try {
		// conf = Configuration.getInstance();
		// FileHandler fh = new
		// FileHandler(Configuration.getInstance().getValue("LOG_FILE"));
		// fh.setFormatter(new SimpleFormatter());
		// log.addHandler(fh);
		//			
		// String logLevel = conf.getValue("LOG_LEVEL");
		// if (logLevel.equals("FINEST")){
		// log.setLevel(Level.FINEST);
		// } else {
		// log.setLevel(Level.WARNING);
		// }
		//			
		// log.fine("Logger configured");
		// } catch (SecurityException e) {
		// log.log(Level.WARNING, "Error during configuration", e);
		// System.exit(-1);
		// } catch (IOException e) {
		// log.log(Level.WARNING, "Error during configuration", e);
		// System.exit(-1);
		// }
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

		OSSMain.configureLogger();

		log.info("OpenSign Server started");

		Security.addProvider(new BouncyCastleProvider());
		Crypto crypto = Crypto.getInstance();

		OSSHttpServer openSignServer = OSSHttpServer.getInstance();

		try {
			if (init) {
				OSSKeyStore.getInstance().create();
			}
			openSignServer.start();
			System.out.println("Press enter to quit server");
			new BufferedReader(new InputStreamReader(System.in)).readLine();
			openSignServer.stop();

		} catch (Exception e) {
			// log.log(Level.WARNING, "Server error", e);
		}

	}

}
