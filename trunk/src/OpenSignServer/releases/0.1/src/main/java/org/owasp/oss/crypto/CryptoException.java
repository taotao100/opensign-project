package org.owasp.oss.crypto;

public class CryptoException extends Exception {
	
	CryptoException(String message){
		super(message);
	}
    
	CryptoException(String message, Throwable cause){
		super(message, cause);
	}
	
	CryptoException(Throwable cause){
		super(cause);		
	}
}
