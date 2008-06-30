package org.owasp.oss.ca;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.Certificate;

import org.bouncycastle.util.encoders.Base64;
import org.owasp.oss.httpserver.HttpHandlerException;
import org.owasp.oss.httpserver.HttpRequest;
import org.owasp.oss.httpserver.HttpResponse;
import org.owasp.oss.httpserver.HttpResponse.ErrorType;
import org.owasp.oss.httpserver.HttpResponse.MimeType;
import org.owasp.oss.utils.Utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CsrHandler implements HttpHandler {

	private static final String STATIC_FILES_PATH = "www";

	private static final String DEFAULT_FILE = "index.html";

	public void handle(HttpExchange exchange) throws IOException {

		try {

			HttpRequest req = HttpRequest.create(exchange);
			HttpResponse resp = HttpResponse.create(exchange);

			if (req.isPOST()) {
				resp.setMimeType(MimeType.TEXT);

				byte[] bytesToSign = req.getBodyBytes();

				CertificationAuthority ca = new CertificationAuthority();
				Certificate cert = ca.processCsr(new ByteArrayInputStream(
						bytesToSign));

				byte[] respBytes = Utils.concatenate(
						"-------BEGIN NEW CERTIFICATE-------\r\n".getBytes(),
						Base64.encode(cert.getEncoded()));
				respBytes = Utils.concatenate(respBytes,
						"\r\n-------END NEW CERTIFICATE-------".getBytes());

				resp.send(new ByteArrayInputStream(respBytes));
			}
		} catch (HttpHandlerException e) {
			e.printStackTrace();
			HttpResponse.sendErrorPage(ErrorType.SERVICE_UNAVAILABLE, exchange);
		} catch (Exception e) {
			e.printStackTrace();
			HttpResponse.sendErrorPage(ErrorType.SERVICE_UNAVAILABLE, exchange);
		}

		HttpResponse.sendErrorPage(ErrorType.FORBIDDEN, exchange);
	}
}
