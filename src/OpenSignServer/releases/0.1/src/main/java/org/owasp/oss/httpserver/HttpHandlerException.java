package org.owasp.oss.httpserver;

public class HttpHandlerException extends Exception {
	
	public HttpHandlerException(String message){
		super(message);
	}
    
	public HttpHandlerException(String message, Throwable cause){
		super(message, cause);
	}
	
	public HttpHandlerException(Throwable cause){
		super(cause);		
	}
}
