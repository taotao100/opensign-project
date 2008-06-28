package org.owasp.oss.crypto;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;



public class Crypto {
	
	private KeyPair _keyPair = null;
	
	private static Crypto _crypto = new Crypto();
	
	private Crypto(){
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
			kpg.initialize(1024);
			_keyPair = kpg.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Crypto getInstance(){
		return _crypto;
	}
		
	// dummy sign method
	public byte[] sign(byte[] data){
		
		try {
			Signature sig = Signature.getInstance("SHA1WithRSA");
			sig.initSign(_keyPair.getPrivate());
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
