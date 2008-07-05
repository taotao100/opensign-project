package org.owasp.oss.ca;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.Certificate;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.owasp.oss.httpserver.HttpHandlerException;
import org.owasp.oss.httpserver.HttpRequest;
import org.owasp.oss.httpserver.HttpResponse;
import org.owasp.oss.httpserver.OSSHttpServer;
import org.owasp.oss.httpserver.HttpResponse.ErrorType;
import org.owasp.oss.httpserver.HttpResponse.MimeType;
import org.owasp.oss.utils.Utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * This class handles Certificate Signing Requests. 
 */
public class CaHandler implements HttpHandler {
	
	private static Logger log = Logger.getLogger(CaHandler.class);

	public void handle(HttpExchange exchange) throws IOException {

		try {
			
			log.info("http request received");

			HttpRequest req = HttpRequest.create(exchange);
			HttpResponse resp = HttpResponse.create(exchange);

			if (req.isGET()) {
				CertificationAuthority ca = new CertificationAuthority();
				Certificate caCert = ca.getCertificate();
				resp.send(new ByteArrayInputStream(ca.certificateToPEM(caCert)), MimeType.TEXT);
			}
			else if (req.isPOST()) {
				log.error("CA does not support POST requests");
				HttpResponse.sendErrorPage(ErrorType.FORBIDDEN, exchange);
			} else {			
				log.error("HTTP method not known");
				HttpResponse.sendErrorPage(ErrorType.FORBIDDEN, exchange);
			}
			
		} catch (HttpHandlerException e) {
			log.error("Error during porcessing CSR", e);
			HttpResponse.sendErrorPage(ErrorType.SERVICE_UNAVAILABLE, exchange);
		} catch (Exception e) {
			log.error("Error during porcessing CSR", e);
			HttpResponse.sendErrorPage(ErrorType.SERVICE_UNAVAILABLE, exchange);
		}
	}
}
