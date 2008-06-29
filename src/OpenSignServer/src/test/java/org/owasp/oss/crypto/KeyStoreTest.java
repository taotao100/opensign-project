package org.owasp.oss.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.owasp.oss.TestBase;

// Keytool commands:
// 
// Generation of private key and keystore:
// keytool -genkey -keyalg RSA -keysize 1024 -alias alias -keystore test_keystore
//
// Export of certificate:
// keytool -exportcert -alias test -file test.cer
// 
public class KeyStoreTest extends TestBase {
	
	

	protected void setUp() throws Exception {
		super.setUp();
		if (Security.getProvider("BC") == null)
			Security.addProvider(new BouncyCastleProvider());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public static Test suite() {
		return new TestSuite(KeyStoreTest.class);
	}
	
	public void testLoadCertificateFromFile() throws Exception {	
		
		OSSKeyStore store = OSSKeyStore.getInstance();
		Certificate cert = store.loadCertificateFromFile(TEST_RESOURCES_PATH + TEST_CERTIFICATE);
		assertNotNull(cert);
		FileInputStream fileStream = new FileInputStream(TEST_RESOURCES_PATH + TEST_CERTIFICATE);
		byte[] fileBytes = new byte[fileStream.available()];
		fileStream.read(fileBytes);
		byte[] certBytes = cert.getEncoded();		
		
		for (int i = 0; i < certBytes.length; ++i)
			if (certBytes[i] != fileBytes[i])
				assertTrue(false);				
	}

	public void testCreateKeystore() throws Exception {
			
		OSSKeyStore store = OSSKeyStore.getInstance();
		
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
		kpg.initialize(1024);
		KeyPair keyPair = kpg.generateKeyPair();
	
		
		Certificate[] certChain = new Certificate[1];
		certChain[0] = store.loadCertificateFromFile(TEST_RESOURCES_PATH + TEST_CERTIFICATE);
		
//		KeyStore _keyStore = KeyStore.getInstance("PKCS12", "BC");
//		_keyStore.load(null, null);
//		FileOutputStream os = new FileOutputStream("KEYSTORE_NAME");
//		_keyStore.setKeyEntry("test", keyPair.getPrivate(),"pass".toCharArray(), certChain);
//		_keyStore.store(os, "pass".toCharArray());
//		os.close();				
		
		store.setKeyEntry("test1", keyPair.getPrivate(), certChain);
		store.store();
		PrivateKey privKey = store.getKey("test1");
		assertEquals(privKey, keyPair.getPrivate());
	}
}
