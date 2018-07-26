package com.matafe.iptvlist.ping.control;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ping Worker
 * 
 * @author matafe@gmail.com
 */
@Stateless
public class PingAsyncWorker {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Long TIME = 10000L;

    @Asynchronous
    public void fireAsync(String theUrl) {
	try {
	    Thread.sleep(TIME);
	    int responseCode = getResponseCode(theUrl);
	    logger.info("Executed. result: " + responseCode);
	} catch (InterruptedException e) {
	    logger.error("Could not sleep for " + TIME + " millis");
	    e.printStackTrace();
	} catch (Exception e) {
	    logger.error("Could not connect to " + theUrl);
	    e.printStackTrace();
	}
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
