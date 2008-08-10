package org.owasp.oss.client.command;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.owasp.oss.client.RESTClient;

public class CommandGetCertificate extends CommandBase implements CommandInterface {

	@Override
	public String getDescription() {
		return "CommandGetCertificate";
	}

	@Override
	public void execute(Map<String, String> parameters) {
		try {
			String certName = parameters.get("c");
			String format = parameters.get("f");
			String outPutMethod = parameters.get("o");

			if (format == null || format.equalsIgnoreCase("PEM"))
				format = "PEM";

			if (format.equalsIgnoreCase("binary"))
				format = "binary";

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
	}

}
