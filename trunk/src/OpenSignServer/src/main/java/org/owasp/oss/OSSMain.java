package org.owasp.oss;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import java.util.Set;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.owasp.oss.crypto.Crypto;
import org.owasp.oss.httpserver.OSSHttpServer;

public class OSSMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Security.addProvider(new BouncyCastleProvider());
		Crypto crypto = Crypto.getInstance();
//		
//		Provider[] providers = Security.getProviders();
//        for (int i = 0; i < providers.length; i++) {
//            Provider provider = providers[i];
//            System.out.println("Provider name: " + provider.getName());
//            System.out.println("Provider information: " + provider.getInfo());
//            System.out.println("Provider version: " + provider.getVersion());
//            Set entries = provider.entrySet();
//            Iterator iterator = entries.iterator();
//            while (iterator.hasNext()) {
//                System.out.println("===========================================");
//                System.out.println("Property entry: " + iterator.next());
//            }
//        }
            

		OSSHttpServer openSignServer = OSSHttpServer.getOSSHttpServer();

		try {
			openSignServer.start();
			System.out.println("Press enter to quit server");
			new BufferedReader(new InputStreamReader(System.in)).readLine();
			openSignServer.stop();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
