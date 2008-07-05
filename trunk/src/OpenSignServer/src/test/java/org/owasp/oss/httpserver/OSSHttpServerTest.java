package org.owasp.oss.httpserver;

import java.net.URL;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.owasp.oss.Configuration;
import org.owasp.oss.TestBase;
import org.owasp.oss.client.RESTClient;
import org.owasp.oss.utils.Utils;

public class OSSHttpServerTest extends TestBase {

	private OSSHttpServer _server = OSSHttpServer.getInstance();
	protected void setUp() throws Exception {
		super.setUp();
		_server.start();		 
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		_server.stop();
	}

	public static Test suite() {
		return new TestSuite(OSSHttpServerTest.class);
	}
	
	public URL buildURL(String path) throws Exception {
		String port = Configuration.getInstance().getValue("OSS_PORT");
		return new URL("http://localhost:" + port + path);
	}
	
	public void testGetCARootCertificate() throws Exception {
		URL url = buildURL("/ca");
		byte[] resp = RESTClient.doGET(url);
		assertNotNull(resp);
		assertTrue(resp.length > 100);
		System.out.println(new String(resp));
	}

	public void testIssueCSR() throws Exception {
		URL url = buildURL("/ca/csr");
		byte[] bodyBytes = loadFileBytes(TEST_CSR);
		byte[] resp = RESTClient.doPost(url, bodyBytes);
		assertNotNull(resp);
		assertTrue(resp.length > 100);
		System.out.println(new String(resp));
	}		
}