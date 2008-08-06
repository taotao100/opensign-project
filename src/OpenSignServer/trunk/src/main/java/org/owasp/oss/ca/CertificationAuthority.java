package org.owasp.oss.ca;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.owasp.oss.Configuration;
import org.owasp.oss.ca.model.User;
import org.owasp.oss.crypto.Crypto;
import org.owasp.oss.crypto.CryptoException;
import org.owasp.oss.crypto.OSSKeyStore;

import sun.security.pkcs.PKCS10;

/**
 * This class is responsible for Certification Authority (CA) related actions.
 */
public class CertificationAuthority {

	private static Logger log = Logger.getLogger(CertificationAuthority.class);

	private static CertificationAuthority _instance = null;

	static {
		try {
			_instance = new CertificationAuthority();
		} catch (CertificationAuthorityException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static final String DEFAULT_KEY_STORE_FILE = "oss_keystore.jceks";

	private static final String KEY_STORE_PASSWD = "password";

	private static final String ROOT_KEY_NAME = "root";

	private OSSKeyStore _keyStore = null;

	private String _keyStoreFile = null;

	private static final int PEM_BREAK = 78;

	public static CertificationAuthority getInstance() {
		return _instance;
	}

	private CertificationAuthority() throws CertificationAuthorityException {

		init();
	}

	public void createRoot() {
		UserManager um = UserManager.getInstance();
		if (um.getUser(ROOT_KEY_NAME) == null) {
			User user = new User();
			user.setUserName(ROOT_KEY_NAME);
			user.setResourceName(ROOT_KEY_NAME);
			user.setIssuer(true);
			user.setResourcePath("");
			UserManager.getInstance().registerUser(user);
		}

	}

	private void init() throws CertificationAuthorityException {
		try {
			_keyStoreFile = Configuration.getInstance().getResourceFileAndPath(
					"CA_KEY_STORE_NAME");

			if (_keyStoreFile == null || _keyStoreFile.length() < 1)
				_keyStoreFile = DEFAULT_KEY_STORE_FILE;

			_keyStore = new OSSKeyStore(_keyStoreFile, KEY_STORE_PASSWD);

		} catch (CryptoException e) {
			throw new CertificationAuthorityException(e);
		}
	}

	public void createIssuer(User user) throws CertificationAuthorityException {
		try {
			// building cert chain
			UserManager um = UserManager.getInstance();
			List<User> users = um.getAllUsersInPath(user.getResourcePath());
			Iterator<User> iter = users.iterator();
			int chainSize = users.size() + 1;
			Certificate[] certChain = new Certificate[chainSize];
			for (int i = chainSize - 1; iter.hasNext(); --i) {
				User currentUser = iter.next();
				certChain[i] = _keyStore.getCertificate(currentUser
						.getResourceName());
			}

			// generation of issuer
			KeyPair pair = Crypto.generateKeyPair();
			certChain[0] = makeCertificate(pair.getPrivate(), pair.getPublic());
			_keyStore.setKeyEntry(user.getResourceName(), pair.getPrivate(),
					certChain);
			_keyStore.store();
			um.storeUser(user);

		} catch (CryptoException e) {
			throw new CertificationAuthorityException(e);
		}
	}

	public Certificate processCsr(String csrStr, User user)
			throws CertificationAuthorityException {

		try {
			csrStr = csrStr.replaceFirst(
					"-----BEGIN NEW CERTIFICATE REQUEST-----", "")
					.replaceFirst("-----END NEW CERTIFICATE REQUEST-----", "");
			byte[] crsDer = Base64.decode(csrStr);

			// FileOutputStream f = new FileOutputStream("csr.der");
			// f.write(crsDer);
			// f.close();

			PKCS10 p = new PKCS10(crsDer);

			log.info("CSR received:");
			log.info(p);

			String subjectDN = p.getSubjectName().toString();
			X509Name subject = new X509Name(subjectDN);

			Certificate cert = makeCertificate(_keyStore.getPrivateKey(user
					.getResourcePath()), p.getSubjectPublicKeyInfo(), subject);

			_keyStore.setCertificateEntry(user.getResourceName(), cert);
			_keyStore.store();

			return cert;

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

	public byte[] certificateToPEM(Certificate cert)
			throws CertificationAuthorityException {
		try {
			StringBuffer respBody = new StringBuffer(new String(Base64
					.encode(cert.getEncoded())));

			for (int breakInsert = PEM_BREAK - 2; respBody.length() > breakInsert; breakInsert += PEM_BREAK)
				respBody.insert(breakInsert, "\r\n");

			respBody.insert(0, "-----BEGIN CERTIFICATE-----\r\n");
			respBody.append("\r\n-----END CERTIFICATE-----");

			return respBody.toString().getBytes();

		} catch (Exception e) {
			throw new CertificationAuthorityException(e);
		}
	}

	public String getKeyStoreFile() {
		return _keyStoreFile;
	}

	/**
	 * Returns the root certificate from the CA
	 * 
	 * @return root certificate
	 * @throws CertificationAuthorityException
	 */
	public Certificate getCertificate() throws CertificationAuthorityException {
		try {
			return _keyStore.getCertificate(ROOT_KEY_NAME);
		} catch (Exception e) {
			log.error("Error loading certificate", e);
			throw new CertificationAuthorityException("No root key defined");
		}
	}

	public Certificate getCertificate(String alias)
			throws CertificationAuthorityException {
		try {
			return _keyStore.getCertificate(alias);
		} catch (Exception e) {
			log.error("No certificate with this alias in key store", e);
			throw new CertificationAuthorityException("Certificate not found");
		}
	}

	public Certificate[] getCertificateChain(String alias)
			throws CertificationAuthorityException {
		try {
			return _keyStore.getCertificateChain(alias);
		} catch (Exception e) {
			log.error("No certificate with this alias in key store", e);
			throw new CertificationAuthorityException("Certificate not found");
		}
	}

}
