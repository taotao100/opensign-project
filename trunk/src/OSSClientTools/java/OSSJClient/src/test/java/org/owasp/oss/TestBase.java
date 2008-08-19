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
		
	protected static final String TEST_CERTIFICATE = "test.cer";	
	protected static final String TEST_CSR = "csr1.pem";
	
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
		return new FileInputStream(fileName);
	}
	
	protected byte[] loadFileBytes(String fileName) throws Exception {
		InputStream fileStream = loadFile(fileName);
		byte[] fileBytes = new byte[fileStream.available()];
		fileStream.read(fileBytes);
		return fileBytes;
	}
	
	protected void writeFile(String fileName, byte[] data) throws Exception {
		FileOutputStream os = new FileOutputStream(fileName);
		os.write(data);
	}
	
	// For getting Maven tests to work
	public void testDummy(){
		assertTrue(true);
	}

}
