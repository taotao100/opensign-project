package org.owasp.oss.client.command;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.owasp.oss.TestBase;

/**
 * Requirements for this test class: 
 * - OpenSign server must run at localhost:8080
 */
public class VerifyChainTest extends TestBase {

	/**
	 * Requiremtns for this test case: 
	 * - Certificate "user1.bin.cer" must be in
	 * the root directory, which is archived if test class
	 * "CertificateSignRequest" is executed successfully
	 */
	public void testVerifyCertificateDepthOne() throws Exception {
//		CommandInterface command = new VerifyChain();
//		System.out.println(command.getDescription());
//		command.printHelp();
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("c", "user1.bin.cer");
//		command.execute(params);
	}

	/**
	 * Requiremtns for this test case: 
	 * - Certificate "userX.bin.cer" must be manually placed
	 *   the root directory
	 * - To obtain this certificate register a random number of issuers with the server and "userX" as a regular user
	 * - Log in with userX and issuer a CSR, in order to generate a certificate, with response format binary.
	 * - Place the certificate in the root directory of this test framework.
	 */	
	public void testVerifyCertificateDepthWhatever() throws Exception {
//		CommandInterface command = new VerifyChain();
//		System.out.println(command.getDescription());
//		command.printHelp();
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("c", "userX.bin.cer");
//		command.execute(params);
	}
}
