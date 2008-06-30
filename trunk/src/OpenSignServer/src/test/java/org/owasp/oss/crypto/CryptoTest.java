package org.owasp.oss.crypto;

import java.security.Security;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
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
	public void testSign(){
		Crypto c = Crypto.getInstance();
		byte[] signature = c.sign("test".getBytes());
		assertNotNull(signature);
		assertTrue(signature.length > 1);
	}

}
