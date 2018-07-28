package com.matafe.iptvlist.business.mail.control;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class SMTPAuthenticator extends Authenticator {

    private final String username;
    private final String password;

    public SMTPAuthenticator(final String username, final String password) {
	this.username = username;
	this.password = password;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
	return new PasswordAuthentication(username, password);
    }
}
