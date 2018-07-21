package com.matafe.iptvlist;

/**
 * Application Exception
 * 
 * @author matafe@gmail.com
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ApplicationException(String message) {
	super(message);
    }

}
