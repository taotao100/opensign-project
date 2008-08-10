package org.owasp.oss.ca;

import java.io.FileInputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
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
import org.owasp.oss.crypto.Pkcs10;

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

	private static final int CERT_DAYS_TILL_EXPIRY = 10 * 365;

	private static final String CERT_SIGNATURE_ALG = "SHA1WithRSAEncryption";

	private static final String CERT_GENERAL_DN = "C=GB,ST=England,L=London,O=OWASP,OU=Opensign,CN=";

	private OSSKeyStore _keyStore = null;

	private String _keyStoreFile = null;

	public static CertificationAuthority getInstance() {
		return _instance;
	}

	private CertificationAuthority() throws CertificationAuthorityException {

		init();
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
			String path = user.getResourcePath();
			String name = user.getResourceName();
			// building cert chain
			UserManager um = UserManager.getInstance();
			List<User> users = um.getAllUsersInPath(path);
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
			X509Name issuer = new X509Name(CERT_GENERAL_DN + path);
			PrivateKey issuerKey = _keyStore.getPrivateKey(path);

			X509Name subject = new X509Name(CERT_GENERAL_DN + name);

			certChain[0] = makeCertificate(issuer, issuerKey, subject, pair
					.getPublic());
			_keyStore.setKeyEntry(name, pair.getPrivate(), certChain);
			_keyStore.store();

		} catch (CryptoException e) {
			throw new CertificationAuthorityException(e);
		}
	}

	public void createRoot(String rootName)
			throws CertificationAuthorityException {
		try {
			KeyPair pair = Crypto.generateKeyPair();
			X509Name issuer = new X509Name(CERT_GENERAL_DN + rootName);
			X509Name subject = new X509Name(CERT_GENERAL_DN + rootName);
			Certificate[] certChain = new Certificate[1];
			certChain[0] = makeCertificate(issuer, pair.getPrivate(), subject, pair
					.getPublic());
			_keyStore.setKeyEntry(rootName, pair.getPrivate(), certChain);
			_keyStore.store();

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

			Pkcs10 csr = new Pkcs10(crsDer);

			log.info("CSR received:");
			log.info(csr);

			String subjectDN = csr.getSubject();
			// TODO: verify subject name

			X509Name subject = new X509Name(CERT_GENERAL_DN
					+ user.getResourceName());
			X509Name issuer = new X509Name(CERT_GENERAL_DN
					+ user.getResourcePath());

			Certificate cert = makeCertificate(issuer, _keyStore
					.getPrivateKey(user.getResourcePath()), subject, csr
					.getSubjectPublicKey());

			_keyStore.setCertificateEntry(user.getResourceName(), cert);
			_keyStore.store();

			return cert;

		} catch (Exception e) {
			throw new CertificationAuthorityException(e);
		}
	}

	public static X509Certificate makeCertificate(X509Name issuer,
			PrivateKey issuerPrivateKey, X509Name subject,
			PublicKey subjectPublicKey) throws CertificationAuthorityException {

		try {

			final Calendar expiry = Calendar.getInstance();
			expiry.add(Calendar.DAY_OF_YEAR, CERT_DAYS_TILL_EXPIRY);

			final X509V3CertificateGenerator certificateGenerator = new X509V3CertificateGenerator();

			certificateGenerator.setSerialNumber(java.math.BigInteger
					.valueOf(System.currentTimeMillis()));
			certificateGenerator.setIssuerDN(issuer);
			certificateGenerator.setSubjectDN(subject);
			certificateGenerator.setPublicKey(subjectPublicKey);
			certificateGenerator.setNotBefore(new Date());
			certificateGenerator.setNotAfter(expiry.getTime());
			certificateGenerator.setSignatureAlgorithm(CERT_SIGNATURE_ALG);

			return certificateGenerator.generate(issuerPrivateKey);

		} catch (Exception e) {
			throw new CertificationAuthorityException(e);
		}
	}

	public static X509Certificate makeDummyCertificate(
			PrivateKey issuerPrivateKey, PublicKey subjectPublicKey)
			throws CertificationAuthorityException {

		try {

			final Calendar expiry = Calendar.getInstance();
			expiry.add(Calendar.DAY_OF_YEAR, CERT_DAYS_TILL_EXPIRY);

			final X509V3CertificateGenerator certificateGenerator = new X509V3CertificateGenerator();

			certificateGenerator.setSerialNumber(java.math.BigInteger
					.valueOf(System.currentTimeMillis()));
			certificateGenerator
					.setIssuerDN(new X509Name(
							"C=GB,ST=England,L=London,O=OWASP,OU=Opensign - Test,CN=DummyIssuer"));
			certificateGenerator
					.setSubjectDN(new X509Name(
							"C=GB,ST=England,L=London,O=OWASP,OU=Opensign - Test,CN=DummySubject"));
			certificateGenerator.setPublicKey(subjectPublicKey);
			certificateGenerator.setNotBefore(new Date());
			certificateGenerator.setNotAfter(expiry.getTime());
			certificateGenerator.setSignatureAlgorithm(CERT_SIGNATURE_ALG);

			return certificateGenerator.generate(issuerPrivateKey);

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
