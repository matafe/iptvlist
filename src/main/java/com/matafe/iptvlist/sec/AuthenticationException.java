package com.matafe.iptvlist.sec;

import com.matafe.iptvlist.ApplicationException;

/**
 * Authentication Exception
 * 
 * @author matafe@gmail.com
 */
public class AuthenticationException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public AuthenticationException(String message) {
	super(message);
    }

}
