package org.owasp.oss;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.owasp.oss.crypto.CryptoTest;
import org.owasp.oss.crypto.KeyStoreTest;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.owasp.oss");
		//$JUnit-BEGIN$
		suite.addTest(MainTest.suite());
		suite.addTest(CryptoTest.suite());
		suite.addTest(KeyStoreTest.suite());		
		//$JUnit-END$
		return suite;
	}

}
