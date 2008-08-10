package org.oss.owasp.httpserver;

import java.net.URL;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.owasp.oss.Configuration;
import org.owasp.oss.TestBase;
import org.owasp.oss.ca.CertificationAuthority;
import org.owasp.oss.ca.UserManager;
import org.owasp.oss.ca.model.User;
import org.owasp.oss.client.RESTClient;
import org.owasp.oss.httpserver.OSSHttpServer;

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
	
	public void testGetCAProfileCertificate() throws Exception {
		URL url = buildURL("/root");
		byte[] resp = new RESTClient().doGET(url);
		assertNotNull(resp);
		assertTrue(resp.length > 1);
		System.out.println(new String(resp));
	}	
	
	public void testGetCARootCertificate() throws Exception {
		URL url = buildURL("/root?property=cert&responseFormat=PEM");
		byte[] resp = new RESTClient().doGET(url);
		assertNotNull(resp);
		assertTrue(resp.length > 100);
		System.out.println(new String(resp));
	}
	
	public void testLogin() throws Exception {
		URL url = buildURL("/login?user_name=root&password=123");
		RESTClient client = new RESTClient();
		byte[] resp = client.doGET(url);
		url = buildURL("/edit_profile");
		resp = client.doGET(url);
		assertNotNull(resp);
		assertTrue(client.isSessionOpen());		
	}

	public void testIssueCSR() throws Exception {
		
		UserManager um = UserManager.getInstance();
		User user4 = new User();
		user4.setUserName("user4");
		user4.setPassword("123");
		user4.setResourceName("root/user4");
		user4.setResourcePath("root");
		um.storeUser(user4);
		
//		URL url = buildURL("/login?user_name=user1&password=123");
//		RESTClient client = new RESTClient();
//		byte[] resp = client.doGET(url);
//		System.out.println(new String(resp));
//		assertTrue(client.isSessionOpen());
		
		URL url = buildURL("/root?user_name=user4&password=123&responseFormat=PEM");
		byte[] bodyBytes = loadFileBytes(TEST_CSR);
		byte[] resp = new RESTClient().doPost(url, bodyBytes);
		assertNotNull(resp);
		System.out.println(new String(resp));
		this.writeFile("cert2.cer", resp);
		assertTrue(new String(resp).contains("BEGIN CERTIFICATE"));	
	}		
}