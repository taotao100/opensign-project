package org.owasp.oss.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;

public class OSSKeyStore {

	private static OSSKeyStore _instance = null;

	private static final String KEYSTORE_NAME = "oss_keystore.p12";

	private static final String KEYSTORE_PASSWD = "pass";
	//private static final String KEYSTORE_PASSWD = "storepasswd";

	private KeyStore _keyStore = null;

	private OSSKeyStore() throws CryptoException {

		try {
			_keyStore = KeyStore.getInstance("PKCS12", "BC");
			File file = new File(KEYSTORE_NAME);
			
			if (!file.exists())
				_keyStore.load(null, null);
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
	
	public PrivateKey getKey(String alias) throws CryptoException {
		try {
			return (PrivateKey)_keyStore.getKey(alias, KEYSTORE_PASSWD.toCharArray());
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
	
	private void load() throws CryptoException {
		try {			
			FileOutputStream os = new FileOutputStream(KEYSTORE_NAME);
			_keyStore.load(null, null);
			_keyStore.store(os, KEYSTORE_PASSWD.toCharArray());
			os.close();
		} catch (Exception e) {			
			throw new CryptoException(e);
		}
	}
}
