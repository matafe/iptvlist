package com.matafe.iptvlist.heartbeat.ejb;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class HeartBeatAsyncBean {

    private static final Long TIME = 10000L;

    private static final String UNKNOW = "UNKNOW";

    @Asynchronous
    public String fireAsync(String theUrl) {
	try {
	    Thread.sleep(TIME);
	    return String.valueOf(getResponseCode(theUrl));
	} catch (InterruptedException e) {
	    System.out.println("Could not sleep for " + TIME + " millis");
	    e.printStackTrace();
	} catch (Exception e) {
	    System.out.println("Could not connect to " + theUrl);
	    e.printStackTrace();
	}
	return UNKNOW;
    }

    private int getResponseCode(String theUrl) {
	int responseCode = 0;
	try {
	    URL url = new URL(theUrl);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    responseCode = conn.getResponseCode();
	} catch (Exception e) {
	    throw new RuntimeException("Failed to connect to url", e);
	}
	return responseCode;
    }
}
