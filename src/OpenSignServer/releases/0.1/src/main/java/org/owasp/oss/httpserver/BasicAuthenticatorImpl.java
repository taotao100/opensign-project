package org.owasp.oss.httpserver;

import org.owasp.oss.ca.User;
import org.owasp.oss.ca.UserManager;

import com.sun.net.httpserver.BasicAuthenticator;

/**
 * This class implements the credential validation once a user wants to
 * authenticate himself at the server.
 */
public class BasicAuthenticatorImpl extends BasicAuthenticator {

	public BasicAuthenticatorImpl(String realm) {
		super(realm);
	}

	@Override
	public boolean checkCredentials(String username, String password) {
		UserManager userManager = UserManager.getInstance();
		User user = userManager.getUser(username);		
		if (user != null &&user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

}
