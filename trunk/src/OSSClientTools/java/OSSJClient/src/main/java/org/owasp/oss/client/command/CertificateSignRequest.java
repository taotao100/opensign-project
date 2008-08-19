package org.owasp.oss.client.command;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.owasp.oss.client.RESTClient;

public class CertificateSignRequest extends CommandBase implements CommandInterface {
	
	@Override
	public String getDescription() {
		return "CommandCertificateSignRequest";
	}

	@Override
	public void execute(Map<String, String> parameters) {		
		try {
			
			String csr = parameters.get("c");
			String issuer = parameters.get("i");			
			String password = parameters.get("p");
			String user_name = parameters.get("u");
			
			String format = parameters.get("f");
			String outPutMethod = parameters.get("o");
			
			if (format == null || format.equalsIgnoreCase("BIN"))
				format = "BIN";
			
			if ( format.equalsIgnoreCase("PEM"))
				format = "PEM";

			RESTClient client = new RESTClient();
			URL url = new URL(OS_HOST + issuer 
					+ "?user_name=" + user_name 
					+ "&password=" + password				
					+ "&responseFormat=" + format);
			byte[] bodyBytes = readFile(csr);
			byte[] cert = new RESTClient().doPost(url, bodyBytes);
			if (outPutMethod != null && outPutMethod.equals("console")) {
				System.out.println(new String(cert));
			} else {
				writeFile(user_name.replaceAll("/", "_") + "."
						+ format.toLowerCase() + ".cer", cert);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}	

	@Override
	public void printHelp() {
		System.out.println("Mandatory:");
		System.out.println("\t-i [issuer]\te.g \"root/user1/user2\"");		
		System.out.println("\t-c [csr file]\tpath and name of csr file (must be in binary PKCS#10 formatted)");		
		System.out.println("\t-p [password]");
		System.out.println("\t-u [user name]");
		System.out.println("Optional:");
		System.out.println("\t-f [format]\t\"bin\" or \"pem\" whereas \"pem\" is default");
		System.out.println("\t-o [out put method]\t\"console\" or \"file\"");
	}

}
