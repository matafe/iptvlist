package com.matafe.iptvlist.business;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.matafe.iptvlist.business.sec.control.AuthenticationException;

/**
 * Application Exception Handler
 * 
 * @author matafe@gmail.com
 */
@Provider
public class AppExceptionHandler implements ExceptionMapper<ApplicationException> {

    private static final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

    @Override
    public Response toResponse(ApplicationException exception) {
	Response response = null;
	if (exception instanceof AuthenticationException) {
	    response = Response.status(Response.Status.NOT_FOUND).build();
	    //response = Response.ok(exception.getMessage(), MediaType.TEXT_PLAIN).build();
//	} else if (exception instanceof ApplicationException) {
//	    response = SERVER_ERROR;
	} else {
	    //response = SERVER_ERROR;
	    response = Response.status(Response.Status.NOT_FOUND).build();
	}
	
	return response;
    }

}
