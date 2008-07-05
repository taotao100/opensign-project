package org.owasp.oss.ca;

import java.io.File;
import java.io.InputStream;
import java.security.cert.Certificate;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.owasp.oss.TestBase;

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
	
	public void testCreateKeyStore() throws Exception {		
		CertificationAuthority ca = new CertificationAuthority();
		File storeFile = new File(ca.getKeyStoreFile());
		assertTrue(storeFile.exists());
		storeFile.delete();
	}	
	
	public void testProcessCsr() throws Exception {
		InputStream csrStream = loadFile(TEST_CSR);
		CertificationAuthority ca = new CertificationAuthority();
		Certificate cert = ca.processCsr(csrStream);
		assertNotNull(cert);
		//writeFile("cert1.cer", cert.getEncoded());
	}
	
	
	
//	public void testBuildCsr() throws Exception {
//		// Hashtable<DERObjectIdentifier, String> attrs = new
//		// Hashtable<DERObjectIdentifier, String>();
//
//		// CN=test, OU=test, O=test, L=test, ST=test, C=AT
//		// attrs.put(X509Principal.CN, "OpenSign");
//		// attrs.put(X509Principal.OU, "SummerOfCode");
//		// attrs.put(X509Principal.O, "OWASP");
//		// attrs.put(X509Principal.L, "Austria");
//		// attrs.put(X509Principal.ST, "Graz");
//		// attrs.put(X509Principal.C, "AT");
//		// attrs.put(X509Principal.EmailAddress, "test@test.org");
//		// Vector<DERObjectIdentifier> v = new Vector<DERObjectIdentifier>();
//		// v.add(X509Principal.CN);
//		// v.add(X509Principal.OU);
//		// v.add(X509Principal.O);
//		// v.add(X509Principal.L);
//		// v.add(X509Principal.ST);
//		// v.add(X509Principal.C);
//		// v.add(X509Principal.EmailAddress);
//
//		// X509Name subject = new X509Name(v, attrs);
//
//		PrivateKey privKey = OSSKeyStore.getInstance().getPrivateKey("signkey1");
//		PublicKey pubKey = OSSKeyStore.getInstance().getPublicKey("signkey1");
//
//		PKCS10CertificationRequest req1 = new PKCS10CertificationRequest(
//				"SHA1withRSA", new X509Principal(
//						"CN=CodeSigner1, OU=SummerOfCode, O=OWASP, C=GB"),
//				pubKey, null, privKey);
//		assertNotNull(req1);
//	}
	


	
}
