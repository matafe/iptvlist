package com.matafe.iptvlist.business;

import static java.text.MessageFormat.format;

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

    public ApplicationException(Message message) {
	super(format(message.getText(), new Object[0]));
    }

    public ApplicationException(Message message, Object... args) {
	super(format(message.getText(), args));
    }

    public ApplicationException(Message message, Exception exception, Object... args) {
	super(format(message.getText(), args), exception);
    }
}
