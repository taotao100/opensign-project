package org.owasp.oss.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.owasp.oss.ca.CertificationAuthority;

/**
 * This class manages a key store file.
 */
public class OSSKeyStore {

	private static Logger log = Logger.getLogger(OSSKeyStore.class);

	private static int MIN_PASSWORD_LENGTH = 6;

	private String _name = null;

	private String _password = null;

	private KeyStore _keyStore = null;

	/**
	 * Constructor of OpenSign Server key store
	 * 
	 * @param name
	 *            The name of key store, which also will be the file name
	 *            (including path)
	 * @param password
	 *            Password which protects key-store file, which must be at least
	 *            MIN_PASSWORD_LENGTH in length
	 * @throws CryptoException
	 */
	public OSSKeyStore(String name, String password) throws CryptoException {

		if (name == null || name.length() < 1)
			throw new CryptoException(
					"Can't create OSSKeyStore, name has to be defined");
		if (name == null || name.length() < MIN_PASSWORD_LENGTH)
			throw new CryptoException(
					"Can't create OSSKeyStore, password is to short");

		_name = name;
		_password = password;

		try {
			_keyStore = KeyStore.getInstance("JCEKS", "SunJCE");
			File file = new File(name);

			if (!file.exists()) {
				_keyStore.load(null, null);
			} else
				load();

		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

	public boolean containsAlias(String alias) throws CryptoException {
		try {
			return _keyStore.containsAlias(alias);
		} catch (KeyStoreException e) {
			throw new CryptoException(e);
		}
	}

	public Certificate loadCertificateFromFile(String fileName)
			throws CryptoException {
		try {
			FileInputStream certificateStream = new FileInputStream(fileName);
			CertificateFactory certificateFactory = CertificateFactory
					.getInstance("X.509");
			Certificate cert = certificateFactory
					.generateCertificate(certificateStream);
			certificateStream.close();
			return cert;
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

	public PrivateKey getPrivateKey(String alias) throws CryptoException {
		try {
			if (!_keyStore.isKeyEntry(alias))
				throw new CryptoException("Key not in store");
			return (PrivateKey) _keyStore
					.getKey(alias, _password.toCharArray());
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

	public PublicKey getPublicKey(String alias) throws CryptoException {
		return getCertificate(alias).getPublicKey();
	}

	public Certificate getCertificate(String alias) throws CryptoException {
		try {
			Certificate[] certificates = _keyStore.getCertificateChain(alias);
			if (certificates == null)
				throw new CryptoException("Certificate not in store");
			if (certificates.length < 1)
				throw new CryptoException("Certificate not in store");
			return certificates[0];
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

	public void setKeyEntry(String alias, PrivateKey privKey,
			Certificate[] chain) throws CryptoException {
		try {

			if (alias != null && alias.length() > 1 && privKey != null
					&& chain != null && chain[0] != null)
				_keyStore.setKeyEntry(alias, privKey, _password.toCharArray(),
						chain);
			else
				throw new CryptoException(
						"Can't store key as parameters are missing");

		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

	public void store() throws CryptoException {
		try {
			FileOutputStream os = new FileOutputStream(_name);
			_keyStore.store(os, _password.toCharArray());
			os.close();
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

	private void load() throws CryptoException {
		try {
			FileInputStream is = new FileInputStream(_name);
			_keyStore.load(is, _password.toCharArray());
			Enumeration<String> e = _keyStore.aliases();

			log.info("Key store " + _name + " loaded");

			log.info("Following keys available:");
			while (e.hasMoreElements())
				log.info("\t " + e.nextElement());

		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}
}
