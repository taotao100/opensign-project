package org.owasp.oss.httpserver;

public class HttpServerException extends Exception {
	
	HttpServerException(String message){
		super(message);
	}
    
	HttpServerException(String message, Throwable cause){
		super(message, cause);
	}
	
	HttpServerException(Throwable cause){
		super(cause);		
	}
}
