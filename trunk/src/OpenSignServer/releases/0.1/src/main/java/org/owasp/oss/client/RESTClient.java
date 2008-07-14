package org.owasp.oss.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Class providing functionality to communicate with a RESTful server architecture
 */
public class RESTClient {

	/**
	 * Retrieve a resource by issuing a GET request.
	 * 
	 * @param url
	 *            The URL of the resource to retrieve
	 * @return An array of bytes of the request contents or null
	 * @throws IOException
	 */
	public static byte[] doGET(URL url) {
		byte[] response = null;
		HttpURLConnection conn = null;

		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			conn.setRequestProperty("Content-Transfer-Encoding", "binary");
			conn.setRequestProperty("accept", "application/octet-stream");

			conn.connect();

			if (conn.getResponseCode() == 200) {
				System.out.println("Response code: " + conn.getResponseCode() + " (" + conn.getResponseMessage() + ")");

				response = getRequestBytes(conn.getInputStream());
			} else {
				System.out.println("Response code: " + conn.getResponseCode() + " (" + conn.getResponseMessage() + ")");
			}
		} catch (IOException e) {
			System.out.println("GET failed!" + e);
		} finally {
			if (conn != null)
				conn.disconnect();
		}

		return response;
	}

	/**
	 * Sending and retrieve a resource by issuing a POST request.
	 * 
	 * @param url
	 *            The URL of the resource to retrieve
	 * @param body Content which is sent to URL           
	 * @return An array of bytes of the request contents or null
	 * @throws IOException
	 */	
	public static byte[] doPost(URL url, byte[] body) {
		byte[] response = null;

		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			conn.setRequestProperty("Content-Transfer-Encoding", "binary");
			conn.setRequestProperty("accept", "application/octet-stream");

			conn.connect();

			OutputStream post_data = conn.getOutputStream();
			post_data.write(body);
			post_data.close();

			if (conn.getResponseCode() == 200) {
				System.out.println("Response code: " + conn.getResponseCode() + " (" + conn.getResponseMessage() + ")");
				response = getRequestBytes(conn.getInputStream());
			} else {
				System.out.println("Response code: " + conn.getResponseCode() + " (" + conn.getResponseMessage() + ")");
			}
		} catch (IOException e) {
			System.out.println("Error while sending POST " + e);
		} finally {
			if (null != conn)
				conn.disconnect();
		}

		return response;
	}

	/**
	 * Get request contents (e.g. a POST request contents) into a byte array.
	 * 
	 * @param request_stream
	 *            InputStream of request contents
	 * @return An array of bytes of the request contents or null
	 * @throws IOException
	 */
	public static byte[] getRequestBytes(InputStream request_stream) throws IOException {
		if (request_stream == null) {
			return null;
		}

		int buffer_size = 1024;
		byte[] byte_buffer = new byte[buffer_size];

		int bytes_read = 0;

		ByteArrayOutputStream byte_array_stream = new ByteArrayOutputStream(buffer_size * 2);

		try {
			while ((bytes_read = request_stream.read(byte_buffer)) != -1) {
				byte_array_stream.write(byte_buffer, 0, bytes_read);
			}
		} catch (Throwable e) {
			e.printStackTrace(System.out);
		}

		return byte_array_stream.toByteArray();
	}
}
