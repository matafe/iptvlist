package com.matafe.iptvlist.sec;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.matafe.iptvlist.Message;

/**
 * Security Authenticator
 * 
 * @author matafe@gmail.com
 */
@Singleton
public class SecurityAuthenticator {

    private static final String ADMIN_PASSWORD = "31OpjXYGJQxxzbOxsedW/g==";

    @Inject
    private SecurityStore securityStore;

    @Inject
    private SecurityUtil securityUtil;

    public void authenticate(String username, String password) {
	this.authenticate(username, password, false);
    }

    public void authenticate(String username, String password, boolean byPassEncryption) {

	boolean isAuthenticated = false;

	if (!isBlank(username) && password != null) {
	    try {
		User found = securityStore.find(username);
		String encryptedPassword = byPassEncryption ? password : securityUtil.encrypt(password);
		isAuthenticated = isEq(encryptedPassword, found.getPassword());
	    } catch (UserNotFoundException e) {
		// Ok. Not Authenticated.
	    }
	}
	if (!isAuthenticated) {
	    throw new AuthenticationException(new Message.Builder().text("Incorrect username and password.").build());
	}
    }

    private boolean isEq(String str, String match) {
	return str != null && str.equals(match);
    }

    private boolean isBlank(String str) {
	return str == null || str.isEmpty();
    }
}
