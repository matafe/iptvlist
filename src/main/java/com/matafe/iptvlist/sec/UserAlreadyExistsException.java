package com.matafe.iptvlist.sec;

import com.matafe.iptvlist.ApplicationException;
import com.matafe.iptvlist.Message;

/**
 * User User Already Exists Exception
 * 
 * @author matafe@gmail.com
 */
public class UserAlreadyExistsException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(String username) {
	super(new Message.Builder().text("User ''{0}'' already exists.").build(), username);
    }

}
