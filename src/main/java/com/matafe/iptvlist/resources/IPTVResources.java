package com.matafe.iptvlist.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("iptv")
public class IPTVResources {


  @GET
  public String getIptvList(@PathParam("username") String username,
      @PathParam("username") String password) {
    return "http://mylist.tv";
  }

}
