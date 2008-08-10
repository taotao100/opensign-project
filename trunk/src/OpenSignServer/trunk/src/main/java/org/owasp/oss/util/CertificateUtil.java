package org.owasp.oss.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import org.bouncycastle.util.encoders.Base64;
import org.owasp.oss.ca.CertificationAuthorityException;

public class CertificateUtil {
	
	private static final int PEM_BREAK = 78;
	
	public static Certificate loadCertificateFromFile(String fileName)
			throws FileNotFoundException, IOException, CertificateException {

		FileInputStream certificateStream = new FileInputStream(fileName);
		CertificateFactory certificateFactory = CertificateFactory
				.getInstance("X.509");
		Certificate cert = certificateFactory
				.generateCertificate(certificateStream);
		certificateStream.close();
		return cert;

	}

	public static byte[] certificateToPEM(Certificate cert) throws CertificateEncodingException{

			StringBuffer respBody = new StringBuffer(new String(Base64
					.encode(cert.getEncoded())));

			for (int breakInsert = PEM_BREAK - 2; respBody.length() > breakInsert; breakInsert += PEM_BREAK)
				respBody.insert(breakInsert, "\r\n");

			respBody.insert(0, "-----BEGIN CERTIFICATE-----\r\n");
			respBody.append("\r\n-----END CERTIFICATE-----");

			return respBody.toString().getBytes();
	}
}
