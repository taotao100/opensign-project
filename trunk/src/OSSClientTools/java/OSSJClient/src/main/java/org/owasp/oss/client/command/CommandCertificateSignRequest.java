package org.owasp.oss.client.command;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.owasp.oss.client.RESTClient;

public class CommandCertificateSignRequest extends CommandBase implements CommandInterface {
	
	@Override
	public String getDescription() {
		return "CommandCertificateSignRequest";
	}

	@Override
	public void execute(Map<String, String> parameters) {		
		try {
			String format = parameters.get("f");
			String outPutMethod = parameters.get("o");
			String issuer = parameters.get("i");
			String user_name = parameters.get("u");
			String password = parameters.get("p");
			String csr = parameters.get("c");
			
			if (format == null || format.equalsIgnoreCase("PEM"))
				format = "PEM";

			if (format.equalsIgnoreCase("binary"))
				format = "binary";

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
		System.out.println("-u [user name]");
		System.out.println("-p [password]");

	}

}
