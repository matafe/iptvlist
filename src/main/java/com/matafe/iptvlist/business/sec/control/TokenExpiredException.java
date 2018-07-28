package com.matafe.iptvlist.business.sec.control;

import com.matafe.iptvlist.business.ApplicationException;
import com.matafe.iptvlist.business.Message;

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
