package org.oss.owasp.ca;

import java.io.File;
import java.io.FileInputStream;
import java.security.cert.Certificate;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.owasp.oss.TestBase;
import org.owasp.oss.ca.CertificationAuthority;
import org.owasp.oss.ca.UserManager;
import org.owasp.oss.ca.model.User;
import org.owasp.oss.httpserver.OSSHttpServer;
import org.owasp.oss.util.CertificateUtil;

public class CertificateAuthorityTest extends TestBase {

	// Note that all test cases in this test class are depending on each other

	protected void setUp() throws Exception {
		super.setUp();
		OSSHttpServer.getInstance().start();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		OSSHttpServer.getInstance().stop();
	}

	public static Test suite() {
		return new TestSuite(CertificateAuthorityTest.class);
	}

	/**
	 * This testcase sets up an empty key store for the CA. This includes the
	 * creation of a root key pair and the corresponding certificate. The key
	 * store file is stored in an encrypted manner.
	 * 
	 * @throws Exception
	 */
	public void testCreateKeyStore() throws Exception {
		CertificationAuthority ca = CertificationAuthority.getInstance();
		File storeFile = new File(ca.getKeyStoreFile());
		assertTrue(storeFile.exists());
		storeFile.delete();
	}

	public void testCreateIssuer() throws Exception {
		CertificationAuthority ca = CertificationAuthority.getInstance();

		User user1 = new User();
		user1.setUserName("user1");
		user1.setResourceName("root/user1");
		user1.setResourcePath("root");
		user1.setIssuer(true);
		UserManager.getInstance().storeUser(user1);
		ca.createIssuer(user1);

		User user2 = new User();
		user2.setUserName("user2");
		user2.setResourceName("root/user1/user2");
		user2.setResourcePath("root/user1");
		user2.setIssuer(true);
		UserManager.getInstance().storeUser(user2);
		ca.createIssuer(user2);

		Certificate cert2 = ca.getCertificate("root/user1/user2");
		assertNotNull(cert2);
		Certificate[] chain = ca.getCertificateChain("root/user1/user2");
		assertEquals(3, chain.length);
	}

	public void testProcessCsr() throws Exception {

		CertificationAuthority ca = CertificationAuthority.getInstance();

		User user = new User();
		user.setUserName("user3");
		user.setResourceName("root/user1/user2/user3");
		user.setResourcePath("root/user1/user2");
		UserManager.getInstance().registerUser(user);
		Certificate cert = ca.processCsr(new String(loadFileBytes(TEST_CSR)),
				user);
		assertNotNull(cert);
		writeFile("cert1.cer", cert.getEncoded());
	}

	public void testVerifyCertChain() throws Exception {
		CertificationAuthority ca = CertificationAuthority.getInstance();

		Certificate cert = CertificateUtil
				.loadCertificateFromFile(_testResourcePath + "cert1.cer");

		Certificate[] chain = ca.getCertificateChain("root/user1/user2");
		assertNotNull(chain);
		cert.verify(chain[0].getPublicKey());
		for (int i = 0; i < chain.length - 1; ++i) {
			chain[i].verify(chain[i + 1].getPublicKey());
		}
		// Verifying root certificate
		chain[chain.length - 1].verify(chain[chain.length - 1].getPublicKey());
	}

	public void testLoadCertificateFromFile() throws Exception {
		CertificationAuthority ca = CertificationAuthority.getInstance();
		Certificate cert = CertificateUtil
				.loadCertificateFromFile(_testResourcePath + TEST_CERTIFICATE);
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

	// public void testBuildCsr() throws Exception {
	// // Hashtable<DERObjectIdentifier, String> attrs = new
	// // Hashtable<DERObjectIdentifier, String>();
	//
	// // CN=test, OU=test, O=test, L=test, ST=test, C=AT
	// // attrs.put(X509Principal.CN, "OpenSign");
	// // attrs.put(X509Principal.OU, "SummerOfCode");
	// // attrs.put(X509Principal.O, "OWASP");
	// // attrs.put(X509Principal.L, "Austria");
	// // attrs.put(X509Principal.ST, "Graz");
	// // attrs.put(X509Principal.C, "AT");
	// // attrs.put(X509Principal.EmailAddress, "test@test.org");
	// // Vector<DERObjectIdentifier> v = new Vector<DERObjectIdentifier>();
	// // v.add(X509Principal.CN);
	// // v.add(X509Principal.OU);
	// // v.add(X509Principal.O);
	// // v.add(X509Principal.L);
	// // v.add(X509Principal.ST);
	// // v.add(X509Principal.C);
	// // v.add(X509Principal.EmailAddress);
	//
	// // X509Name subject = new X509Name(v, attrs);
	//
	// PrivateKey privKey = OSSKeyStore.getInstance().getPrivateKey("signkey1");
	// PublicKey pubKey = OSSKeyStore.getInstance().getPublicKey("signkey1");
	//
	// PKCS10CertificationRequest req1 = new PKCS10CertificationRequest(
	// "SHA1withRSA", new X509Principal(
	// "CN=CodeSigner1, OU=SummerOfCode, O=OWASP, C=GB"),
	// pubKey, null, privKey);
	// assertNotNull(req1);
	// }

}
