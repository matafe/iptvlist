package com.matafe.iptvlist.sec;

/**
 * Security Authenticator
 * 
 * @author matafe@gmail.com
 */
public class SecurityAuthenticator {

    public void authenticate(String username, String password) {
	//TODO
	boolean isAuth = is(username, "matafe") && is(password, "123456");

	if (!isAuth) {
	    throw new AuthenticationException("Ops! Invalid Credentials!");
	}
    }

    private boolean is(String str, String match) {
	return str != null && str.equals(match);
    }
}
