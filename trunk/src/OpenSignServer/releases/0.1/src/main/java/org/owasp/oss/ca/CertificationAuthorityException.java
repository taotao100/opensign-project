package org.owasp.oss.ca;

public class CertificationAuthorityException extends Exception {
	
	CertificationAuthorityException(String message){
		super(message);
	}
    
	CertificationAuthorityException(String message, Throwable cause){
		super(message, cause);
	}
	
	CertificationAuthorityException(Throwable cause){
		super(cause);		
	}
}
