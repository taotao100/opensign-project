package org.owasp.oss.client.command;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.owasp.oss.TestBase;

public class GetCertificateTest extends TestBase {
	
	public void testGetCertificate() throws Exception {
		CommandGetCertificate command = new CommandGetCertificate();
		System.out.println(command.getDescription());
		command.printHelp();
		Map<String, String> params = new HashMap<String, String>();
		params.put("c", "root");
		// Writes certificate to a binary file named "root.bin.cer" as default
		command.execute(params);
		InputStream ins = loadFile("root.bin.cer");
		assertNotNull(ins);		
		params.put("o", "console");
		params.put("f", "pem");
		// Prints certificate to the console
		command.execute(params);
	}
}
