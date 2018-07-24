package com.matafe.iptvlist.sec;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;

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

    @Inject
    private SecurityStore securityStore;

    @Inject
    private SecurityUtil securityUtil;

    public User authenticate(String username, String password) {
	return this.authenticate(username, password, false);
    }

    public User authenticate(String username, String password, boolean byPassEncryption) {

	User authenticatedUser = null;

	if (!isBlank(username) && password != null) {
	    try {
		User found = securityStore.find(username);
		if (!found.isActive()) {
		    throw new UserNotFoundException(username);
		}
		String encryptedPassword = byPassEncryption ? password : securityUtil.encrypt(password);
		if (isEq(encryptedPassword, found.getPassword())) {
		    authenticatedUser = found;
		}
	    } catch (UserNotFoundException e) {
		// Ok. Not Authenticated.
	    }
	}
	if (authenticatedUser == null) {
	    throw new AuthenticationException(new Message.Builder().text("Incorrect username and password.").build());
	}
	return authenticatedUser;
    }

    private boolean isEq(String str, String match) {
	return str != null && str.equals(match);
    }

    private boolean isBlank(String str) {
	return str == null || str.isEmpty();
    }

    public String generateToken(User user) {
	// JWT
	String tokenKey = user.getUsername().concat(user.getPassword())
		.concat(String.valueOf(Calendar.getInstance().getTimeInMillis()));

	return Base64.getEncoder().encodeToString(tokenKey.getBytes(StandardCharsets.UTF_8));
    }

    public void validateToken(String token) {
	securityStore.getAndUpdateLogged(token);
    }
}
