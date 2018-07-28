package com.matafe.iptvlist.business.sec.control;

import com.matafe.iptvlist.business.ApplicationException;
import com.matafe.iptvlist.business.Message;

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
