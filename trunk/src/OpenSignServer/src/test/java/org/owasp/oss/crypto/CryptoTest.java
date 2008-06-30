package org.owasp.oss.crypto;

import java.security.KeyPair;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.owasp.oss.TestBase;

public class CryptoTest extends TestBase {

	protected void setUp() throws Exception {
		super.setUp();	
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
    public static Test suite()
    {
        return new TestSuite( CryptoTest.class );
    }	
	
	// dummy signature test
	public void testSign() throws Exception {
		Crypto c = Crypto.getInstance();
		KeyPair keyPair = Crypto.generateKeyPair();
		byte[] signature = c.sign(keyPair.getPrivate(), "test".getBytes());
		assertNotNull(signature);
		assertTrue(signature.length > 1);
	}

}
