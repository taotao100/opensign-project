package org.owasp.oss;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.oss.owasp.ca.CertificateAuthorityTest;
import org.oss.owasp.ca.dao.DaoTest;
import org.oss.owasp.crypto.CryptoTest;
import org.oss.owasp.crypto.KeyStoreTest;
import org.oss.owasp.httpserver.OSSHttpServerTest;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.owasp.oss");		
		//$JUnit-BEGIN$
		suite.addTest(MainTest.suite());
		suite.addTest(CryptoTest.suite());		
		suite.addTest(KeyStoreTest.suite());
		suite.addTest(DaoTest.suite());
		suite.addTest(CertificateAuthorityTest.suite());
		suite.addTest(OSSHttpServerTest.suite());
		//$JUnit-END$
		return suite;
	}

}
