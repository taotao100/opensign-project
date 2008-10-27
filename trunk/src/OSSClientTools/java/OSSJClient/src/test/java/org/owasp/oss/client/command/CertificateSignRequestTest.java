package org.owasp.oss.client.command;

import org.owasp.oss.*;

/**
 * Requirements for this test case: - OpenSign server must run at localhost:8080
 * - A user named: "user1" having password "123456" must be registered with the
 * server
 */
public class CertificateSignRequestTest extends org.owasp.oss.TestBase {

	public void testSignRequest() throws Exception {
//		CommandInterface command = new CertificateSignRequest();
//		System.out.println(command.getDescription());
//		command.printHelp();
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("i", "root");
//		params.put("c", "csr1.pem");
//		params.put("p", "123456");
//		params.put("u", "user1");
//		// Writes certificate to a binary file named "user1.bin.cer" as default
//		command.execute(params);
//		InputStream ins = loadFile("user1.bin.cer");
//		assertNotNull(ins);
//		params.put("o", "console");
//		params.put("f", "pem");
//		// Prints certificate to the console
//		command.execute(params);
	}
}
