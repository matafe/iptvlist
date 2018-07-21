package com.matafe.iptvlist.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.matafe.iptvlist.ParamDecoder;
import com.matafe.iptvlist.sec.Secured;
import com.matafe.iptvlist.util.HttpUtil;
import com.matafe.iptvlist.util.URLLocator;

/**
 * Iptv Resources
 * 
 * @author matafe@gmail.com
 */
@Path("iptv")
public class IptvResources {


    @Inject
    HttpUtil httpUtil;

    @Inject
    URLLocator urlLocator;

    @GET
    @Path("{username}/{password}/{channel}")
    @ParamDecoder
    @Secured    
    public Response getIptvList(@PathParam("username") final String username,
	    @PathParam("password") final String password, @PathParam("channel") final String channel) {
	return Response.ok(doStreaming(channel), MediaType.APPLICATION_OCTET_STREAM).build();
    }

    private StreamingOutput doStreaming(String channel) {
	return new StreamingOutput() {
	    @Override
	    public void write(OutputStream output) throws IOException, WebApplicationException {
		InputStream inputStream = httpUtil.getAsInputStream(urlLocator.locate(channel));
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
		    output.write(buffer, 0, length);
		}
		output.flush();
	    }
	};
    }

}
