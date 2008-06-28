package org.owasp.oss.crypto;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import junit.framework.TestCase;

public class CryptoTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		Security.addProvider(new BouncyCastleProvider());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	// dummy signature test
	public void testSign(){
		Crypto c = Crypto.getInstance();
		byte[] signature = c.sign("test".getBytes());
		assertNotNull(signature);
		assertTrue(signature.length > 1);
	}

}
