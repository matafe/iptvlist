package com.matafe.iptvlist.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HTTP Utils
 * 
 * @author matafe@gmail.com
 */
public class HttpUtil {

    public InputStream getAsInputStream(String theUrl) {
	InputStream in = null;
	try {
	    URL url = new URL(theUrl);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    //conn.setRequestProperty("content-type", "video/mp4; charset=utf-8");
	    conn.setRequestProperty("Content-Type", "video/mp2t");
	    conn.setRequestProperty("User-Agent", "VLC/3.0.0-git LibVLC/3.0.0-git");
	    conn.setRequestProperty("Accept", "*/*");
	    
	    Map<String, List<String>> rp = conn.getRequestProperties();
	    Set<String> rpk = rp.keySet();
	    for (String k : rpk)
	      System.out.println("ReqKey: " + k + "  ReqValue: " + rp.get(k));
	    
	    Map<String, List<String>> hdrs = conn.getHeaderFields();
	    Set<String> hdrKeys = hdrs.keySet();
	    for (String k : hdrKeys)
	      System.out.println("Key: " + k + "  Value: " + hdrs.get(k));
	    
	    int state = conn.getResponseCode();
	    System.out.println("theUrl: " + theUrl + "  state: " + state);
	    
	    // if (state < 400) {
	    in = conn.getInputStream();
	    // } else {
	    // in = conn.getErrorStream();
	    // }
	    // in = new BufferedInputStream(in);
	    return in;
	} catch (Exception e) {
	    throw new RuntimeException("Failed to connect to url", e);
	}
    }

    public String get(String theUrl) {
	return convertStreamToString(getAsInputStream(theUrl));
    }

    private String convertStreamToString(InputStream is) {
	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	StringBuilder sb = new StringBuilder();

	String line;
	try {
	    while ((line = reader.readLine()) != null) {
		sb.append(line).append('\n');
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new RuntimeException("Failed convert the input stream to string", e);
	} finally {
	    try {
		is.close();
	    } catch (IOException e) {
		throw new RuntimeException("Failed to close the input stream", e);
	    }
	}

	return sb.toString();
    }
}