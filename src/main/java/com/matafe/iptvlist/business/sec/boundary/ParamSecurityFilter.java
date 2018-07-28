package com.matafe.iptvlist.business.sec.boundary;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matafe.iptvlist.business.sec.control.SecurityAuthenticator;

/**
 * Security Filter for Path Parameter Credentials
 * 
 * @author matafe@gmail.com
 */
@Provider
@ParamSecured
@Priority(Priorities.AUTHENTICATION)
public class ParamSecurityFilter implements ContainerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).build();
    static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).build();
    static final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

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
		logger.error("Failed to validate the token: " + token, e);
		requestContext.abortWith(ACCESS_DENIED);
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
	ParamSecured secAnno = resourceInfo.getResourceClass().getAnnotation(ParamSecured.class);
	if (secAnno == null) {
	    secAnno = resourceInfo.getResourceMethod().getAnnotation(ParamSecured.class);
	}

	boolean isAdminRole = secAnno != null && secAnno.value() == ParamSecured.Role.ADMIN;

	return isAdminRole;
    }
}
