package org.owasp.oss.crypto;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.pkcs.CertificationRequestInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.PKCS10CertificationRequest;

public class Pkcs10 {

	PKCS10CertificationRequest _pkcs10Asn;
	PublicKey _subjectPublicKey;

	public Pkcs10(byte[] pkcs10Bytes) throws CryptoException {
		try {
			_pkcs10Asn = new PKCS10CertificationRequest(pkcs10Bytes);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
			_subjectPublicKey = keyFactory
					.generatePublic(new X509EncodedKeySpec(_pkcs10Asn
							.getCertificationRequestInfo()
							.getSubjectPublicKeyInfo().getEncoded()));
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

	public String getSubject() {
		return _pkcs10Asn.getCertificationRequestInfo().getSubject().toString();
	}

	public PublicKey getSubjectPublicKey() {
		return _subjectPublicKey;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		CertificationRequestInfo certificationRequestInfo = _pkcs10Asn
				.getCertificationRequestInfo();
		
		stringBuilder.append("PKCS10CertificationRequest:").append("\r\n");

		DERInteger versionDer = certificationRequestInfo.getVersion();
		BigInteger versionInt = versionDer.getValue();
		stringBuilder.append("Version: ").append(versionInt).append("\r\n");

		stringBuilder.append("Subject: ");
		X509Name subject = certificationRequestInfo.getSubject();
		stringBuilder.append(subject).append("\r\n");

		SubjectPublicKeyInfo subjectPublicKeyInfo = certificationRequestInfo
				.getSubjectPublicKeyInfo();
		AlgorithmIdentifier algorithmIdentifier = subjectPublicKeyInfo
				.getAlgorithmId();
		stringBuilder.append("AlgorithmIdentifier: ").append(
				algorithmIdentifier.getObjectId()).append("\r\n");

		stringBuilder.append(_subjectPublicKey);

		ASN1Set attributes = certificationRequestInfo.getAttributes();
		Enumeration e = attributes.getObjects();
		if (e.hasMoreElements())
			stringBuilder.append("Attributes:");
		while (e.hasMoreElements()) {
			stringBuilder.append("\t").append(e.nextElement()).append("\r\n");
		}
		return stringBuilder.toString();
	}

}
