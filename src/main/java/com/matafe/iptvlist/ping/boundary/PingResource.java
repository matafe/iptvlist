package com.matafe.iptvlist.ping.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.matafe.iptvlist.ping.control.PingAsyncWorker;

@Path("ping")
public class PingResource {

    @Inject
    PingAsyncWorker pingAsyncWorker;

    @Context
    UriInfo uriInfo;

    @GET
    public String ping(@QueryParam("url") String callBackUrl) {
	pingAsyncWorker.fireAsync(callBackUrl);
	return "pong from: " + uriInfo.getBaseUri().toString();
    }

}
