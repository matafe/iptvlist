package com.matafe.iptvlist.heartbeat.ejb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matafe.iptvlist.util.HttpUtil;

@Singleton
public class HeartBeatTimerBean {

    private Logger logger = LoggerFactory.getLogger(HeartBeatTimerBean.class);

    @Inject
    private HttpUtil httpUtil;

    @Schedule(second = "*", minute = "*/1", hour = "*", persistent = false, info = "Every 1 min timer")
    public void tumtum() {
	
	logger.info("tum tum...");

	String result = "";
	
	String theUrl = "https://dormenao.herokuapp.com/resources/ping";

	try (InputStream is = httpUtil.getAsInputStream(theUrl)) {
	    result = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
	} catch (Exception e) {
	    logger.error("Erro ao conectar remote: ", e);
	}

	logger.info("ping - " + result);
    }

}
