package com.matafe.iptvlist.sec;

import com.matafe.iptvlist.ApplicationException;
import com.matafe.iptvlist.Message;

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

    public AuthenticationException(Message message) {
	super(message);
    }

}
