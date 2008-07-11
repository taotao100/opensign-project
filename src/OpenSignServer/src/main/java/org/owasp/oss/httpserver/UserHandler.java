package org.owasp.oss.httpserver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.Certificate;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.owasp.oss.ca.CertificationAuthority;
import org.owasp.oss.ca.CertificationAuthorityException;
import org.owasp.oss.crypto.OSSKeyStore;
import org.owasp.oss.httpserver.HttpResponse.ErrorType;
import org.owasp.oss.httpserver.HttpResponse.MimeType;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * The UserHandler class processes requests for user accounts
 */
public class UserHandler implements HttpHandler {
	
	private static Logger log = Logger.getLogger(UserHandler.class);

	private static final String STATIC_FILES_PATH = "www";

	private static final String DEFAULT_FILE = "/index.html";

	public void handle(HttpExchange exchange) throws IOException {
		
		try {
			
			log.info("http request received");

			HttpRequest req = HttpRequest.create(exchange);
			HttpResponse resp = HttpResponse.create(exchange);
			
			if (req.isGET()) {
				String path = req.getPath();
				Certificate cert = CertificationAuthority.getInstance().getCertificate(path);
				if (cert != null) {
					resp.send(new ByteArrayInputStream(cert.getEncoded()), MimeType.TEXT);
				}
			}
			
			resp.sendDebugPage(exchange);
			return;

		} catch (HttpHandlerException e) {
			log.error("Http request could not be processed", e);			
			HttpResponse.sendErrorPage(ErrorType.SERVICE_UNAVAILABLE, exchange);
			return;
		} catch (Exception e){
			log.fatal("Http request could not be processed", e);			
			HttpResponse.sendErrorPage(ErrorType.SERVICE_UNAVAILABLE, exchange);
			return;
		} 
	}
}
