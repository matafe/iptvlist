package com.matafe.iptvlist.business.playlist.boundary;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import com.matafe.iptvlist.business.ApplicationException;
import com.matafe.iptvlist.business.playlist.control.M3UItemStore;
import com.matafe.iptvlist.business.playlist.control.PlaylistGenerator;
import com.matafe.iptvlist.business.playlist.control.parser.IM3UParser;
import com.matafe.iptvlist.business.playlist.control.parser.M3uParserType;
import com.matafe.iptvlist.business.playlist.entity.M3UItem;
import com.matafe.iptvlist.business.playlist.entity.M3UPlaylist;
import com.matafe.iptvlist.business.sec.boundary.ParamDecoder;
import com.matafe.iptvlist.business.sec.boundary.Secured;
import com.matafe.iptvlist.business.util.HttpUtil;
import com.matafe.iptvlist.business.util.URLLocator;

/**
 * Iptv Resources
 * 
 * @author matafe@gmail.com
 */
@Path("playlist")
public class PlaylistResources {

    @Inject
    HttpUtil httpUtil;

    @Inject
    URLLocator urlLocator;

    @Inject
    M3UItemStore itemStore;

    @Inject
    @M3uParserType(M3uParserType.Type.M3U)
    IM3UParser m3uParser;

    @Inject
    PlaylistGenerator playlistGenerator;

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{username}/{password}")
    @Secured
    public Response getIptvList(@PathParam("username") final String username,
	    @PathParam("password") final String password) {

	M3UPlaylist playlist = playlistGenerator.createTargetPlaylist(username, itemStore.getSourcePlaylist());

	String resourceUri = uriInfo.getBaseUri().toString().concat("playlist");

	String m3uFileContent = playlistGenerator.generateM3uFileContent(username, password, playlist, resourceUri);

	StreamingOutput stream = new StreamingOutput() {
	    @Override
	    public void write(OutputStream os) throws IOException, WebApplicationException {
		Writer writer = new BufferedWriter(new OutputStreamWriter(os));
		writer.write(m3uFileContent);
		writer.flush();
	    }
	};

	return Response.ok(stream).build();
    }

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
		try {
		    InputStream inputStream = httpUtil.getAsInputStream(urlLocator.locate(channel));
		    byte[] buffer = new byte[1024];
		    int length;
		    while ((length = inputStream.read(buffer)) != -1) {
			output.write(buffer, 0, length);
		    }
		    output.flush();
		} catch (Exception e) {
		    e.printStackTrace();
		    throw new ApplicationException("Could not load the channel: " + channel);
		}
	    }
	};
    }

    @GET
    @Path("count")
    public Response countItens() {
	return Response.ok(MessageFormat.format("There are {0} items on the list", itemStore.count())).build();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @Path("sourcelist")
    @Secured(Secured.Role.ADMIN)
    public Response getItems() {
	Collection<M3UItem> items = itemStore.getItems();
	return Response.ok(items).build();
    }

    @GET
    @Path("clear")
    @Secured(Secured.Role.ADMIN)
    public Response clear() {
	itemStore.clear();
	return Response.ok("Original items from cache cleared.").build();
    }

    @GET
    @Path("reload")
    @Secured(Secured.Role.ADMIN)
    public Response reload() {
	return Response.ok(MessageFormat.format("Original list reloaded. {0} items cached", itemStore.realod()))
		.build();
    }

    @POST
    // @Secured(Secured.Role.ADMIN)//TODO
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("updateChannel")
    public Response updateChannel(final M3UItem item) {
	itemStore.updateUrl(item);
	return Response.ok(MessageFormat.format("Channel {0} updated to {1}", item.getName(), item.getUrl())).build();
    }

    // The upload service is done by servlet.
}
