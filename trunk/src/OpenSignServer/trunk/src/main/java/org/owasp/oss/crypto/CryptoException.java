package org.owasp.oss.crypto;

public class CryptoException extends Exception {
	
	public CryptoException(String message){
		super(message);
	}
    
	public CryptoException(String message, Throwable cause){
		super(message, cause);
	}
	
	public CryptoException(Throwable cause){
		super(cause);		
	}
}
