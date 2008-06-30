package org.owasp.oss;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Security;

import junit.framework.TestCase;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

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
	
	protected InputStream loadFile(String fileName) throws Exception {
		return new FileInputStream(TEST_RESOURCES_PATH + fileName);
	}
	
	protected void writeFile(String fileName, byte[] data) throws Exception {
		FileOutputStream os = new FileOutputStream(TEST_RESOURCES_PATH + fileName);
		os.write(data);
	}

}
