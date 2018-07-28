package com.matafe.iptvlist.business.sec.control;

import com.matafe.iptvlist.business.ApplicationException;
import com.matafe.iptvlist.business.Message;

/**
 * User Not Found Exception
 * 
 * @author matafe@gmail.com
 */
public class UserNotFoundException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String username) {
	super(new Message.Builder().text("User ''{0}'' not found.").build(), username);
    }

}
