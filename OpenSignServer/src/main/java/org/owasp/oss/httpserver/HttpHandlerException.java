package org.owasp.oss.httpserver;

public class HttpHandlerException extends Exception {
	
	HttpHandlerException(String message){
		super(message);
	}
    
	HttpHandlerException(String message, Throwable cause){
		super(message, cause);
	}
	
	HttpHandlerException(Throwable cause){
		super(cause);		
	}
}
