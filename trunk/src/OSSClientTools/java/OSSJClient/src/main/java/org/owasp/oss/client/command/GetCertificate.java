package org.owasp.oss.client.command;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.owasp.oss.client.RESTClient;

public class GetCertificate extends CommandBase implements CommandInterface {

	@Override
	public String getDescription() {
		return "Retrieval of an OpenSign certificate";
	}

	@Override
	public void execute(Map<String, String> parameters) {
		try {
			String certName = parameters.get("r");
			String format = parameters.get("f");
			String outPutMethod = parameters.get("o");
			
			if (certName == null) {
				this.printHelp();
				return;
			}

			if (format == null || format.equalsIgnoreCase("bin"))
				format = "bin";

			if (format.equalsIgnoreCase("PEM"))
				format = "PEM";

			RESTClient client = new RESTClient();
			URL url = new URL(OS_HOST + certName
					+ "?property=cert&responseFormat=" + format);
			byte[] cert = client.doGET(url);

			if (outPutMethod != null && outPutMethod.equals("console")) {
				System.out.println(new String(cert));
			} else {
				writeFile(certName.replaceAll("/", "_") + "."
						+ format.toLowerCase() + ".cer", cert);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printHelp() {
		System.out.println("Command getcert takes following parameter:");
		System.out.println("\tMandatory:");	
		System.out.println("\t\t-r [certificate resource name]\te.g \"root/user1/user2\"");		
		System.out.println("\tOptional:");
		System.out.println("\t\t-f [response format]\t\"bin\" or \"pem\" whereas \"pem\" is default");
		System.out.println("\t\t-o [out put method]\t\"console\" or \"file\"");
	}

}
