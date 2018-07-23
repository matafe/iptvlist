package com.matafe.iptvlist.sec;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Security Util Test
 * 
 * @author matafe@gmail.com
 */
public class SecurityUtilTest {

    private static final String PASSAWORD_PLAIN = "secret";

    private SecurityUtil securityUtil;

    @Before
    public void setUp() throws Exception {
	this.securityUtil = new SecurityUtil();
    }

    @Test
    public void testEncrypt() {
	String password = this.securityUtil.encrypt(PASSAWORD_PLAIN);
	System.out.println(password);
	assertThat(password, notNullValue());
	assertThat(password, not(equalTo(PASSAWORD_PLAIN)));
    }

}
