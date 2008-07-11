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
import org.owasp.oss.httpserver.HttpResponse.ErrorType;
import org.owasp.oss.httpserver.HttpResponse.MimeType;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * The FileHandler class processes requests for static html files
 */
public class FileHandler implements HttpHandler {
	
	private static Logger log = Logger.getLogger(FileHandler.class);

	private static final String STATIC_FILES_PATH = "www";

	private static final String DEFAULT_FILE = "/index.html";

	public void handle(HttpExchange exchange) throws IOException {
		
		try {
			
			log.info("http request received");

			HttpRequest req = HttpRequest.create(exchange);
			HttpResponse resp = HttpResponse.create(exchange);

			if (req.isGET()) {

				String filePath = null;

				if (req.isPathEmpty())
					filePath = STATIC_FILES_PATH + DEFAULT_FILE;
				else
					filePath = STATIC_FILES_PATH + req.getPath();

				File file = new File(filePath);

				if (file.exists()) {

					FileInputStream fis = new FileInputStream(file);

					if (filePath.indexOf(".css") > 0)
						resp.send(fis, MimeType.CSS);
					else if (filePath.indexOf(".html") > 0)
						resp.send(fis, MimeType.HTML);
					else
						resp.sendErrorPage(ErrorType.FORBIDDEN, exchange);

					return;
				}
			} else if (req.isPOST()) {

				// TESTCODE ONLY:

				Headers responseHeaders = exchange.getResponseHeaders();
				responseHeaders.set("Content-Type", "text/plain");
				// * response is OK (200)
				exchange.sendResponseHeaders(200, 0);

				// Get response body
				OutputStream responseBody = exchange.getResponseBody();
				 

				byte[] bytesToSign = req.getParameterValue("sign").getBytes();
				
				CertificationAuthority ca = CertificationAuthority.getInstance();
				Certificate cert = ca.processCsr(new ByteArrayInputStream(bytesToSign), "test");
				
				responseBody.write(ca.certificateToPEM(cert));

				// Close the responseBody
				responseBody.close();
				return;
			}
		} catch (HttpHandlerException e) {
			log.error("Http request could not be processed", e);			
			HttpResponse.sendErrorPage(ErrorType.SERVICE_UNAVAILABLE, exchange);
			return;
		} catch (Exception e){
			log.fatal("Http request could not be processed", e);			
			HttpResponse.sendErrorPage(ErrorType.SERVICE_UNAVAILABLE, exchange);
			return;
		} 

		HttpResponse.sendErrorPage(ErrorType.FORBIDDEN, exchange);
	}
}
