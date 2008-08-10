package org.owasp.oss.client.command;

public class CertificationException extends Exception {
	
	CertificationException(String message){
		super(message);
	}
    
	CertificationException(String message, Throwable cause){
		super(message, cause);
	}
	
	CertificationException(Throwable cause){
		super(cause);		
	}
}
