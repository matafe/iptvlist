package com.matafe.iptvlist.heartbeat.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.matafe.iptvlist.heartbeat.ejb.HeartBeatAsyncBean;

@Path("heartbeat")
public class HeartBeatResource {

    @Inject
    HeartBeatAsyncBean heartBeatAsyncBean;

    @GET
    public String ping(@QueryParam("url") String url) {
	return heartBeatAsyncBean.fireAsync(url);
    }

}
