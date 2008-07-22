package org.oss.owasp.ca;

import java.io.File;
import java.security.cert.Certificate;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.owasp.oss.TestBase;
import org.owasp.oss.ca.CertificationAuthority;
import org.owasp.oss.ca.UserManager;
import org.owasp.oss.ca.model.User;

public class CertificateAuthorityTest extends TestBase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();

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

	public void testProcessCsr() throws Exception {

		CertificationAuthority ca = CertificationAuthority.getInstance();

		ca.createIssuer("test");		
		
		User user = new User();		
		user.setUserName("user1");
		user.setResourceName("test/user1");
		user.setResourcePath("test");
		Certificate cert = ca.processCsr(new String(loadFileBytes(TEST_CSR)),
				user);
		assertNotNull(cert);
		// writeFile("cert1.cer", cert.getEncoded());
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
