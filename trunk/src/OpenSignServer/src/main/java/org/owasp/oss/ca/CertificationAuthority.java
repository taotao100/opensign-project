package org.owasp.oss.ca;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.owasp.oss.crypto.OSSKeyStore;

import sun.security.pkcs.PKCS10;

/**
 * This class is responsible for Certification Authority (CA) related actions.
 */
public class CertificationAuthority {

	public Certificate processCsr(InputStream csrStream)
			throws CertificationAuthorityException {
		try {
			byte[] csrBytes = new byte[csrStream.available()];
			csrStream.read(csrBytes);
			String csrStr = new String(csrBytes);
			csrStr = csrStr.replaceFirst(
					"-----BEGIN NEW CERTIFICATE REQUEST-----", "")
					.replaceFirst("-----END NEW CERTIFICATE REQUEST-----", "");
			byte[] crsDer = Base64.decode(csrStr);

			FileOutputStream f = new FileOutputStream("csr.der");
			f.write(crsDer);
			f.close();

			PKCS10 p = new PKCS10(crsDer);
			System.out.println(p.toString());
		

			String subjectDN = p.getSubjectName().toString();
			X509Name subject = new X509Name(subjectDN);

			return makeCertificate(OSSKeyStore.getInstance().getPrivateKey(
					"signkey1"), p.getSubjectPublicKeyInfo(), subject);

		} catch (Exception e) {
			throw new CertificationAuthorityException(e);
		}
	}

	public static X509Certificate makeCertificate(PrivateKey issuerPrivateKey,
			PublicKey subjectPublicKey, X509Name subjectDN)
			throws CertificationAuthorityException {

		try {
			final X509Name issuerDN = new X509Name(
					"CN=OSS, OU=SummerOfCode, O=OWASP, L=London, ST=England, C=GB");

			final int daysTillExpiry = 10 * 365;

			final Calendar expiry = Calendar.getInstance();
			expiry.add(Calendar.DAY_OF_YEAR, daysTillExpiry);

			final X509V3CertificateGenerator certificateGenerator = new X509V3CertificateGenerator();

			certificateGenerator.setSerialNumber(java.math.BigInteger
					.valueOf(System.currentTimeMillis()));
			certificateGenerator.setIssuerDN(issuerDN);
			certificateGenerator.setSubjectDN(subjectDN);
			certificateGenerator.setPublicKey(subjectPublicKey);
			certificateGenerator.setNotBefore(new Date());
			certificateGenerator.setNotAfter(expiry.getTime());
			certificateGenerator.setSignatureAlgorithm("SHA1WithRSAEncryption");

			return certificateGenerator.generate(issuerPrivateKey);

		} catch (Exception e) {
			throw new CertificationAuthorityException(e);
		}

	}

	public static X509Certificate makeCertificate(PrivateKey issuerPrivateKey,
			PublicKey subjectPublicKey) throws CertificationAuthorityException {

		try {
			final X509Name issuerDN = new X509Name(
					"CN=OSS, OU=SummerOfCode, O=OWASP, L=London, ST=England, C=GB");
			final X509Name subjectDN = new X509Name(
					"CN=OSS, OU=SummerOfCode, O=OWASP, L=London, ST=England, C=GB");
			final int daysTillExpiry = 10 * 365;

			final Calendar expiry = Calendar.getInstance();
			expiry.add(Calendar.DAY_OF_YEAR, daysTillExpiry);

			final X509V3CertificateGenerator certificateGenerator = new X509V3CertificateGenerator();

			certificateGenerator.setSerialNumber(java.math.BigInteger
					.valueOf(System.currentTimeMillis()));
			certificateGenerator.setIssuerDN(issuerDN);
			certificateGenerator.setSubjectDN(subjectDN);
			certificateGenerator.setPublicKey(subjectPublicKey);
			certificateGenerator.setNotBefore(new Date());
			certificateGenerator.setNotAfter(expiry.getTime());
			certificateGenerator.setSignatureAlgorithm("SHA1WithRSAEncryption");

			return certificateGenerator.generate(issuerPrivateKey);

		} catch (Exception e) {
			throw new CertificationAuthorityException(e);
		}

	}

}
