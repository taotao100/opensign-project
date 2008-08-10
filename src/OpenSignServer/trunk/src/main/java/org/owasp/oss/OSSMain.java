package org.owasp.oss;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.owasp.oss.ca.CertificationAuthority;
import org.owasp.oss.crypto.Crypto;
import org.owasp.oss.httpserver.OSSHttpServer;

/**
 * OpenSign Server main class
 */
public class OSSMain {
	private static Logger log = Logger.getLogger(OSSMain.class);
	
	private static Configuration conf = Configuration.getInstance();
	
//	private static final SessionFactory sessionFactory = new AnnotationConfiguration()
//	.configure().buildSessionFactory();
	
	public static void main(String[] args) {

		boolean init = true;		

		Security.addProvider(new BouncyCastleProvider());
		Crypto.getInstance();		

		OSSHttpServer openSignServer = OSSHttpServer.getInstance();

		try {
			openSignServer.start();

			if (conf.getValue("STORAGE_TYPE").equals("MEMORY"));
				new File(CertificationAuthority.getInstance().getKeyStoreFile()).deleteOnExit();

			System.out.println("Press enter to quit server");
			new BufferedReader(new InputStreamReader(System.in)).readLine();
			openSignServer.stop();			
		} catch (Exception e) {
			log.error("OpenSign server terminated in an unexpected manner", e);
		}

	}

}
