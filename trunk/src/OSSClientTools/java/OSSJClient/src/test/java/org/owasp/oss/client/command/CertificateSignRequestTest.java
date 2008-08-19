package org.owasp.oss.client.command;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.owasp.oss.TestBase;

public class CertificateSignRequestTest extends TestBase {
	
	public void testSignRequest() throws Exception {
		CommandInterface command = new CertificateSignRequest();
		System.out.println(command.getDescription());
		command.printHelp();
		Map<String, String> params = new HashMap<String, String>();
		params.put("i", "root");
		params.put("c", "csr1.pem");
		params.put("p", "123");
		params.put("u", "user1");		
		// Writes certificate to a binary file named "user1.bin.cer" as default
		command.execute(params);
		InputStream ins = loadFile("user1.bin.cer");
		assertNotNull(ins);		
		params.put("o", "console");
		params.put("f", "pem");
		// Prints certificate to the console
		command.execute(params);
	}
}
