package org.owasp.oss;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import junit.framework.TestCase;

public class TestBase extends TestCase {

	static {

		Security.addProvider(new BouncyCastleProvider());
	}
	
	protected static final String TEST_RESOURCES_PATH = "resources/test/";
	protected static final String TEST_CERTIFICATE = "test.cer";
	
	public TestBase(){
		
	}

	public TestBase(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
