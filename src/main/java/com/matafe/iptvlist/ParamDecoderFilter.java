package com.matafe.iptvlist;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * Decode Param Filter. By any reason some playerslike VNC put a plus sign in
 * the space value of a path parameter.
 * 
 * @author matafe@gmail.com
 */
@Provider
@ParamDecoder
public class ParamDecoderFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Context
    private UriInfo uriInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
	MultivaluedMap<String, String> pathParameters = uriInfo.getPathParameters();
	String key = "channel";
	List<String> list = pathParameters.get(key);
	if (list != null && !list.isEmpty()) {
	    String channelValue = list.get(0);
	    String newValue = channelValue.replaceAll("\\+", " ");
	    pathParameters.put(key, Arrays.asList(new String[] { newValue }));
	}
    }

}
