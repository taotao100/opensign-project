package org.owasp.oss.client.command;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.security.auth.x500.X500Principal;
import javax.security.cert.Certificate;

import org.bouncycastle.jce.X509Principal;
import org.owasp.oss.client.RESTClient;

public class VerifyChain extends CommandBase implements CommandInterface {

	X509Certificate buildCertificate(byte[] certBytes) throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509",
				"BC");
		return (X509Certificate) factory
				.generateCertificate(new ByteArrayInputStream(certBytes));
	}

	X509Certificate getCertificate(String certName) {
		try {
			RESTClient client = new RESTClient();
			URL url = new URL(OS_HOST + certName
					+ "?property=cert&responseFormat=binary");
			byte[] certBytes = client.doGET(url);
			return buildCertificate(certBytes);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getIssuerName(X509Certificate cert) {
		X500Principal principal = cert.getIssuerX500Principal();
		String name = principal.getName("RFC1779");
		int beginIndex = name.indexOf("CN=");
		int endIndex = name.indexOf(',', beginIndex);
		return name.substring(beginIndex + 3, endIndex);
	}

	@Override
	public String getDescription() {
		return "Verification of a certificate and corresponding chain";
	}

	@Override
	public void execute(Map<String, String> parameters) {
		try {
			String certFileName = parameters.get("c");
			
			if (certFileName == null) {
				this.printHelp();
				return;
			}

			byte[] certBytes = readFile(certFileName);
			X509Certificate certToVerify = buildCertificate(certBytes);
			String issuerName = getIssuerName(certToVerify);
			
			while (true) {				
				X509Certificate issuer = getCertificate(issuerName);
				System.out.println("Certificate to verfify:");
				System.out.println(certToVerify);
				
				try {
					certToVerify.verify(issuer.getPublicKey());
				} catch (Exception e) {
					System.out.println("Certificate could not be verified");
					break;
				}
				System.out.println("Certificate successfully verified\n");
				String nextIssuerName = getIssuerName(issuer);
				if (certToVerify.equals(issuer))
					break;
				certToVerify = issuer;
				issuerName = nextIssuerName;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printHelp() {
		System.out.println("Command verifycert takes following parameter:");
		System.out.println("\tMandatory:");
		System.out.println("\t\t-c [certificate file]\tpath and name of certificate to verfiy (must be binary formatted)");
	}
}
