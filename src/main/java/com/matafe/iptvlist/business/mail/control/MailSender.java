package com.matafe.iptvlist.business.mail.control;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matafe.iptvlist.business.ApplicationException;
import com.matafe.iptvlist.business.util.StringUtil;

/**
 * Mail Sender
 * 
 * @author matafe@gmail.com
 */
@ApplicationScoped
public class MailSender {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String mailSmtpHost = null;
    private String mailSmtpPort = null;
    private String mailSmtpUsername = null;
    private String mailSmtpPassword = null;

    private Properties props;

    public MailSender() {
	this.mailSmtpHost = !StringUtil.isBlank(System.getenv("MAIL_SMTP_HOST")) ? System.getenv("MAIL_SMTP_HOST")
		: "smtp.gmail.com";
	this.mailSmtpPort = !StringUtil.isBlank(System.getenv("MAIL_SMTP_PORT")) ? System.getenv("MAIL_SMTP_PORT")
		: "587";
	this.mailSmtpUsername = !StringUtil.isBlank(System.getenv("MAIL_SMTP_USERNAME"))
		? System.getenv("MAIL_SMTP_USERNAME")
		: "matafeiptvapp";
	this.mailSmtpPassword = System.getenv("MAIL_SMTP_PASSWORD");

	this.props = new Properties();
	this.props.put("mail.smtp.host", mailSmtpHost);
	this.props.put("mail.smtp.port", mailSmtpPort);
	this.props.put("mail.smtp.auth", "true");
	this.props.put("mail.smtp.starttls.enable", "true");

    }

    public void send(String fromEmail, String toEmail, String subject, String htmlContent) {

	Authenticator authenticator = new SMTPAuthenticator(getMailSmtpUsername(), getMailSmtpPassword());

	Session session = Session.getDefaultInstance(props, authenticator);

	try (Transport transport = session.getTransport()) {

	    MimeMessage message = new MimeMessage(session);
	    Multipart multipart = new MimeMultipart("alternative");

	    BodyPart bodyPart = new MimeBodyPart();
	    bodyPart.setContent(htmlContent, "text/html");
	    multipart.addBodyPart(bodyPart);

	    message.setContent(multipart);
	    InternetAddress addressFrom = new InternetAddress(fromEmail);
	    message.setFrom(addressFrom);
	    message.setReplyTo(new InternetAddress[] { addressFrom });
	    // message.setHeader("XPriority", "1");
	    message.setSubject(subject);
	    message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

	    transport.connect();
	    transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
	    System.out.println();
	    logger.info("Mail has been sent successfully from {}", fromEmail);
	} catch (Exception e) {
	    com.matafe.iptvlist.business.Message message = new com.matafe.iptvlist.business.Message.Builder()
		    .text("Failed to send the e-mail.").build();
	    logger.error(message.getText(), e);
	    throw new ApplicationException(message, e);
	}

    }

    private static class SMTPAuthenticator extends Authenticator {

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

    public String getMailSmtpHost() {
	return mailSmtpHost;
    }

    public String getMailSmtpPort() {
	return mailSmtpPort;
    }

    public String getMailSmtpUsername() {
	return mailSmtpUsername;
    }

    public String getMailSmtpPassword() {
	return mailSmtpPassword;
    }

    public static void main(String[] args) throws Exception {
	String fromEmail = "eu@domail.com";
	String toEmail = "matafet@gmail.com";
	String subject = "Ola";
	String htmlContent = "<h1>OLA3<h1>";

	new MailSender().send(fromEmail, toEmail, subject, htmlContent);

    }
}
