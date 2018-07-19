package com.matafe.iptvlist.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("iptv")
public class IPTVResources {


  @GET
  public String getIptvList(@PathParam("username") String username,
      @PathParam("username") String password) {

    String url = "http://somelist.tv";

    if (is(username, "matafe") && is(password, "123456")) {
      url = "http://mylist.tv";
    }

    return url;
  }

  private boolean is(String str, String match) {
    return str != null && str.equals(match);
  }

}
