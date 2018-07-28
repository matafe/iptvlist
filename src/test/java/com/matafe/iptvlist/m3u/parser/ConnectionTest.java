package com.matafe.iptvlist.m3u.parser;

import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.matafe.iptvlist.business.ApplicationException;
import com.matafe.iptvlist.business.util.HttpUtil;

public class ConnectionTest {

    private HttpUtil httpUtil;

    @Before
    public void setUp() throws Exception {
	httpUtil = new HttpUtil();
    }

    @Test
    public void testConnection() {
	String channel = System.getenv("IPTV_CHANNEL");
	PrintStream output = System.out;
	try {
	    InputStream inputStream = httpUtil.getAsInputStream(channel);
	    byte[] buffer = new byte[1024];
	    int length;
	    while ((length = inputStream.read(buffer)) != -1) {
		output.write(buffer, 0, length);
	    }
	    output.flush();
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new ApplicationException("Could not load the channel: " + channel);
	}
    }

}
