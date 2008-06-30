package org.owasp.oss.crypto;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;



public class Crypto {
	
	private KeyPair _keyPair = null;
	
	private static Crypto _crypto = new Crypto();
	
	private Crypto(){		
	}
	
	public static KeyPair generateKeyPair() throws CryptoException {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
			kpg.initialize(1024);
			return kpg.generateKeyPair();
		} catch (Exception e) {			
			throw new CryptoException(e);
		}
	}
	
	public static Crypto getInstance(){
		return _crypto;
	}
		
	// dummy sign method
	public byte[] sign(PrivateKey privkey, byte[] data){
		
		try {
			Signature sig = Signature.getInstance("SHA1WithRSA");
			sig.initSign(privkey);
			sig.update(data);
			return sig.sign();			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
