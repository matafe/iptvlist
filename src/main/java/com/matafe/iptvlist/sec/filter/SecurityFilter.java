package com.matafe.iptvlist.sec.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import com.matafe.iptvlist.sec.Secured;
import com.matafe.iptvlist.sec.SecurityAuthenticator;
import com.matafe.iptvlist.sec.Secured.Role;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).build();
    private static final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

    @Context
    ResourceInfo resourceInfo;

    @Context
    UriInfo uriInfo;

    @Inject
    SecurityAuthenticator authenticator;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

	String username;
	String password;

	if (isAdminRoleResouce()) {

	    // Get the HTTP Authorization header from the request
	    String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

	    // Check if the HTTP Authorization header is present and formatted correctly
	    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
		throw new NotAuthorizedException("Authorization header must be provided");
	    }

	    // Extract the token from the HTTP Authorization header
	    String token = authorizationHeader.substring("Bearer".length()).trim();

	    try {
		// Validate the token
		authenticator.validateToken(token);
	    } catch (Exception e) {
		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
	    }

	} else {
	    MultivaluedMap<String, String> pathParameters = uriInfo.getPathParameters();

	    username = pathParameters.getFirst("username");
	    password = pathParameters.getFirst("password");

	    // the url path param for the users are not encrypted.
	    this.authenticator.authenticate(username, password, true);
	}

    }

    private boolean isAdminRoleResouce() {
	Secured secAnno = resourceInfo.getResourceClass().getAnnotation(Secured.class);
	if (secAnno == null) {
	    secAnno = resourceInfo.getResourceMethod().getAnnotation(Secured.class);
	}

	boolean isAdminRole = secAnno != null && secAnno.value() == Secured.Role.ADMIN;

	return isAdminRole;
    }
}
