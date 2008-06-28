package org.owasp.oss.httpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.bouncycastle.util.encoders.Base64;
import org.owasp.oss.crypto.Crypto;
import org.owasp.oss.httpserver.HttpResponse.ErrorType;
import org.owasp.oss.httpserver.HttpResponse.MimeType;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class StaticFileHandler implements HttpHandler {

	private static final String STATIC_FILES_PATH = "www";

	private static final String DEFAULT_FILE = "index.html";

	public void handle(HttpExchange exchange) throws IOException {

		OutputStream rs;
		try {

			HttpRequest req = HttpRequest.create(exchange);
			HttpResponse resp = HttpResponse.create(exchange);

			if (req.isGET()) {

				String filePath = null;

				if (req.isPathEmpty())
					filePath = STATIC_FILES_PATH + req.getPath() + DEFAULT_FILE;
				else
					filePath = STATIC_FILES_PATH + req.getPath();

				File file = new File(filePath);

				if (file.exists()) {

					FileInputStream fis = new FileInputStream(file);

					if (req.getPath().indexOf(".css") > 0)
						resp.setMimeType(MimeType.CSS);
					if (req.getPath().indexOf(".html") > 0)
						resp.setMimeType(MimeType.HTML);

					resp.send(fis);

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

				Crypto c = Crypto.getInstance();
				responseBody
						.write("--------------------SIGNATURE BEGIN--------------------\r\n"
								.getBytes());
				responseBody.write(Base64.encode(c.sign(bytesToSign)));
				responseBody
						.write("\r\n---------------------SIGNATURE END---------------------"
								.getBytes());

				// Close the responseBody
				responseBody.close();
			}
		} catch (HttpHandlerException e) {
			HttpResponse.sendErrorPage(ErrorType.SERVICE_UNAVAILABLE, exchange);
		}

		HttpResponse.sendErrorPage(ErrorType.FORBIDDEN, exchange);
	}
}
