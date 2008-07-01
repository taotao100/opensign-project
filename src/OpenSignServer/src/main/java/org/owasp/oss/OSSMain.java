package org.owasp.oss;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Provider;
import java.security.Security;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.owasp.oss.crypto.Crypto;
import org.owasp.oss.crypto.OSSKeyStore;
import org.owasp.oss.httpserver.OSSHttpServer;

public class OSSMain {
	private static Logger log = Logger.getLogger("org.owasp.oss");

	static public void configureLogger() {
		try {
			Handler fh = new FileHandler(ConfigurationManager.getInstance().getValue("LOG_FILE"));
			fh.setFormatter(new SimpleFormatter());
			log.addHandler(fh);
			log.setLevel(Level.FINEST);
			log.fine("Logger configured");
		} catch (SecurityException e) {
			log.log(Level.WARNING, "Error during configuration", e);
			System.exit(-1);
		} catch (IOException e) {
			log.log(Level.WARNING, "Error during configuration", e);
			System.exit(-1);
		} 
	}

	public void printSystemInformation() {
		System.out
				.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out
				.println("++++++++++++++++++++++++LOGGER NAMES++++++++++++++++++++++++++++++");
		System.out
				.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		LogManager log = LogManager.getLogManager();
		Enumeration<String> en = log.getLoggerNames();
		while (en.hasMoreElements())
			System.out.println(en.nextElement());

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
			log.log(Level.WARNING, "Server error", e);
		}

	}

}
