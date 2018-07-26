package com.matafe.iptvlist.ping.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.matafe.iptvlist.ping.control.PingAsyncWorker;

/**
 * Ping Resources
 * 
 * @author matafe@gmail.com
 */
@Path("ping")
public class PingResource {

    @Inject
    PingAsyncWorker pingAsyncWorker;

    @Context
    UriInfo uriInfo;

    @GET
    public String ping(@QueryParam("url") String url) {
	String callBack = url != null ? url : "https://dormenao.herokuapp.com/resources/ping";
	pingAsyncWorker.fireAsync(callBack);
	return "pong from: " + uriInfo.getBaseUri().toString();

    }

}
