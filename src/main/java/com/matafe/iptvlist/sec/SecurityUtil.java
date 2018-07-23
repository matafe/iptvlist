package com.matafe.iptvlist.sec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Security Util
 * 
 * @author matafe@gmail.com
 */
public class SecurityUtil {

    public String encrypt(final String text) {
	try {
	    final MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(text.getBytes(StandardCharsets.UTF_8));
	    return new String(Base64.getEncoder().encodeToString(md.digest())).trim();
	} catch (final Exception e) {
	    throw new RuntimeException("Failed to encrypt a password!", e);
	}
    }
}
