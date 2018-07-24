package com.matafe.iptvlist.sec;

import com.matafe.iptvlist.ApplicationException;
import com.matafe.iptvlist.Message;

/**
 * Token Expired Exception
 * 
 * @author matafe@gmail.com
 */
public class TokenExpiredException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public TokenExpiredException(String username, String time) {
	super(new Message.Builder().text("The {0}'s token was expired {1} min ago").build(), username, time);
    }

}
