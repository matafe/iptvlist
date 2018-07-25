package com.matafe.iptvlist.resources;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.matafe.iptvlist.sec.Credentials;
import com.matafe.iptvlist.sec.Secured;
import com.matafe.iptvlist.sec.SecurityAuthenticator;
import com.matafe.iptvlist.sec.SecurityManager;
import com.matafe.iptvlist.sec.User;
import com.matafe.iptvlist.util.DateUtil;

/**
 * Iptv Resources
 * 
 * @author matafe@gmail.com
 */
@Path("user")
public class UserResources {

    @Inject
    SecurityAuthenticator authenticator;

    @Inject
    private SecurityManager securityManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("login")
    public Response login(Credentials credentials) {

	try {

	    // Authenticate the user using the credentials provided
	    User user = authenticator.authenticate(credentials.getUsername(), credentials.getPassword());

	    // Generate a token for the user
	    String token = authenticator.generateToken(user);

	    // Save the token on the server side.
	    securityManager.addLogged(user, token);

	    // Return the token on the response
	    return Response.ok(token).build();

	} catch (Exception e) {
	    return Response.status(Response.Status.UNAUTHORIZED).build();
	}
    }

    @GET
    @Secured(Secured.Role.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findUsers() {
	List<User> users = securityManager.findAll();
	//Collections.sort(users, Comparator.comparing(User::getLastUpdated));
	return users;
    }

    @POST
    @Secured(Secured.Role.ADMIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUser(final User user) {
	securityManager.add(user);
	return Response.ok("User " + user.getUsername() + " added.").build();
    }

    @GET
    @Secured(Secured.Role.ADMIN)
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUser(@PathParam("username") String username) {
	return Response.ok(securityManager.find(username)).build();
    }

    @DELETE
    @Secured(Secured.Role.ADMIN)
    @Path("{username}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeUser(@PathParam("username") String username) {
	securityManager.remove(username);
	return Response.ok("User " + username + " removed.").build();
    }

    @PUT
    @Secured(Secured.Role.ADMIN)
    @Path("{username}/active/{year}/{month}/{day}/{hour}/{min}/{sec}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response activateUser(@PathParam("username") String username, @PathParam("year") Integer year,
	    @PathParam("month") Integer month, @PathParam("day") Integer day, @PathParam("hour") Integer hour,
	    @PathParam("min") Integer min, @PathParam("sec") Integer sec) {
	Calendar dateTime = DateUtil.parseCalendar(year, (month - 1), day, hour, min, sec);
	securityManager.activate(username, dateTime);
	return Response.ok("User " + username + " activated until " + DateUtil.prettyFormat(dateTime)).build();
    }

    @PUT
    @Secured(Secured.Role.ADMIN)
    @Path("{username}/inactive")
    @Produces(MediaType.TEXT_PLAIN)
    public Response inactivateUser(@PathParam("username") String username) {
	securityManager.inactivate(username);
	return Response.ok("User " + username + " inactivated.").build();
    }

}
