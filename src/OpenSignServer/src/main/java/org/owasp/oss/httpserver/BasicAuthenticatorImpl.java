package org.owasp.oss.httpserver;

import com.sun.net.httpserver.BasicAuthenticator;

public class BasicAuthenticatorImpl extends BasicAuthenticator {

	public BasicAuthenticatorImpl(String realm) {
		super(realm);
	}

	@Override
	public boolean checkCredentials(String username, String password) {
		if (username.equals("user") && password.equals("1234")) {
			return true;
		}
		return false;
	}

}
