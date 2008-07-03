package org.owasp.oss.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.owasp.oss.ca.CertificationAuthority;
import org.owasp.oss.httpserver.OSSHttpServer;

/**
 * This class manages a key store file.
 */
public class OSSKeyStore {
	
	private static Logger log = Logger.getLogger(OSSHttpServer.class);

	private static OSSKeyStore _instance = null;

	private static final String KEYSTORE_NAME = "resources/live/oss_keystore.bks";

	private static final String KEYSTORE_PASSWD = "pass";

	private KeyStore _keyStore = null;

	private OSSKeyStore() throws CryptoException {

		try {
			_keyStore = KeyStore.getInstance("BKS", "BC");
			File file = new File(KEYSTORE_NAME);
			
			if (!file.exists())
				create();
			else			
				load();
			
		} catch (Exception e) {
			throw new CryptoException(e);
		}

	}

	public static OSSKeyStore getInstance() throws CryptoException {
		if (_instance == null)
			_instance = new OSSKeyStore();
		return _instance;
	}

	public Certificate loadCertificateFromFile(String fileName)
			throws CryptoException {
		try {
			FileInputStream certificateStream = new FileInputStream(fileName);
			CertificateFactory certificateFactory = CertificateFactory
					.getInstance("X.509");
			Certificate cert = certificateFactory
					.generateCertificate(certificateStream);
			certificateStream.close();
			return cert;
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}
	
	public PrivateKey getPrivateKey(String alias) throws CryptoException {
		try {
			if (!_keyStore.isKeyEntry(alias))
				throw new CryptoException("Key not in store");			
			return (PrivateKey)_keyStore.getKey(alias, KEYSTORE_PASSWD.toCharArray());
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}	
	
	public PublicKey getPublicKey(String alias) throws CryptoException {
		try {
			//if (!_keyStore.isCertificateEntry(alias))
			//	throw new CryptoException("Certificate not in store");
			Certificate[] certificates = _keyStore.getCertificateChain(alias);
			return certificates[0].getPublicKey();
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}		

	public void setKeyEntry(String alias, PrivateKey privKey,
			Certificate[] chain) throws CryptoException {
		try {
			_keyStore.setKeyEntry(alias, privKey, KEYSTORE_PASSWD.toCharArray(), chain);
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}		

	public void store() throws CryptoException {
		try {
			FileOutputStream os = new FileOutputStream(KEYSTORE_NAME);			
			_keyStore.store(os, KEYSTORE_PASSWD.toCharArray());
			os.close();
		} catch (Exception e) {			
			throw new CryptoException(e);
		}
	}
	
	public void create() throws CryptoException {
		
		log.info("Creating new key store: " + KEYSTORE_NAME);
		
		try {			
			KeyPair pair = Crypto.generateKeyPair();
			_keyStore.load(null, null);
		
			Certificate[] certChain = new Certificate[1];
			CertificationAuthority ca = new CertificationAuthority();
			certChain[0] = ca.makeCertificate(pair.getPrivate(), pair.getPublic());
			
			setKeyEntry("signkey1", pair.getPrivate(), certChain);
			store();
			
			if (!_keyStore.isKeyEntry("signkey1"))
				throw new CryptoException("Key not in store");				

		} catch (Exception e) {			
			throw new CryptoException(e);
		}				
	}
	
	private void load() throws CryptoException {
		try {			
			FileInputStream is = new FileInputStream(KEYSTORE_NAME);
			_keyStore.load(is, KEYSTORE_PASSWD.toCharArray());
			Enumeration<String> e = _keyStore.aliases();
			
			log.info("Key store " + KEYSTORE_NAME + " loaded");
			
			log.info("Following keys available:");
			while(e.hasMoreElements())
				log.info("\t " + e.nextElement());
					
		} catch (Exception e) {			
			throw new CryptoException(e);
		}
	}
}
