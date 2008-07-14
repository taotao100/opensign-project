package org.oss.owasp.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.owasp.oss.TestBase;
import org.owasp.oss.ca.CertificationAuthority;
import org.owasp.oss.crypto.Crypto;
import org.owasp.oss.crypto.CryptoException;
import org.owasp.oss.crypto.OSSKeyStore;

// Keytool commands:
// 
// Generation of private key and keystore:
// keytool -genkey -keyalg RSA -keysize 1024 -alias alias -keystore test_keystore
//
// Export of certificate:
// keytool -exportcert -alias test -file test.cer
// 
// Generation of a CSR
// keytool -certreq -alias sign1 -file csr1.pem -keypass 123456 -keystore csr_keystore -storepass 123456
public class KeyStoreTest extends TestBase {

	OSSKeyStore _store = null;
	
	public KeyStoreTest() {
		try {
			_store = new OSSKeyStore(_testResourcePath + "testStore.bks",
					"testpass");
		} catch (CryptoException e) {
			e.printStackTrace();
		}
	}

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

		Certificate cert = _store.loadCertificateFromFile(_testResourcePath
				+ TEST_CERTIFICATE);
		assertNotNull(cert);
		FileInputStream fileStream = new FileInputStream(_testResourcePath
				+ TEST_CERTIFICATE);
		byte[] fileBytes = new byte[fileStream.available()];
		fileStream.read(fileBytes);
		byte[] certBytes = cert.getEncoded();

		for (int i = 0; i < certBytes.length; ++i)
			if (certBytes[i] != fileBytes[i])
				assertTrue(false);
	}

	public void testCreateKeystore() throws Exception {

		KeyPair keyPair = Crypto.generateKeyPair();

		Certificate[] certChain = new Certificate[1];
		certChain[0] = CertificationAuthority.makeCertificate(keyPair
				.getPrivate(), keyPair.getPublic());

		_store.setKeyEntry("test1", keyPair.getPrivate(), certChain);
		_store.store();
		PrivateKey privKey = _store.getPrivateKey("test1");
		assertEquals(privKey, keyPair.getPrivate());
	}
	
	
	public void genericKeyStoreTest(KeyStore store, String name, KeyPair kp,
			Certificate[] certChain, String keyPass, String storePass)
			throws Exception {

		store.load(null, null);
		FileOutputStream os = new FileOutputStream(name);
		store.setKeyEntry("test", kp.getPrivate(), keyPass.toCharArray(),
				certChain);
		store.store(os, storePass.toCharArray());
		os.close();

		FileInputStream is = new FileInputStream(name);
		store.load(is, storePass.toCharArray());
		if (store.containsAlias("test")) {
			assertEquals(store.getKey("test", keyPass.toCharArray()), kp
					.getPrivate());
		}
		is.close();
		new File(name).delete();
	}

	// This test is only for testing key store implementations and is not relevant for the system
	public void testKeyStoreFormat() {
//		try {
//			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
//			kpg.initialize(1024);
//			KeyPair kp = kpg.generateKeyPair();
//			// creating a self-signed certificate
//			Certificate[] certChain = new Certificate[1];
//			certChain[0] = CertificationAuthority.makeCertificate(kp
//					.getPrivate(), kp.getPublic());
//
//			KeyStore store = KeyStore.getInstance("JKS", "SUN");
//			genericKeyStoreTest(store, "keystore.jks", kp, certChain,
//					"keypass", "storepass");
//			store = KeyStore.getInstance("JCEKS", "SunJCE");
//			genericKeyStoreTest(store, "keystore.jceks", kp, certChain,
//					"keypass", "storepass");
//			store = KeyStore.getInstance("PKCS12", "SunJSSE");
//			genericKeyStoreTest(store, "keystore.sun.p12", kp, certChain,
//					"keypass", "storepass");
//			store = KeyStore.getInstance("BKS", "BC");
//			genericKeyStoreTest(store, "keystore.bks", kp, certChain,
//					"keypass", "storepass");
//
//			// don't support a different key/key-store password:
//			store = KeyStore.getInstance("PKCS12", "BC");
//			genericKeyStoreTest(store, "keystore.bc.p12", kp, certChain,
//					"pass", "pass");
//			store = KeyStore.getInstance("UBER");
//			genericKeyStoreTest(store, "keystore.uber", kp, certChain, "pass",
//					"pass");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}	
}
