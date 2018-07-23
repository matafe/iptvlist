package com.matafe.iptvlist.sec;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.matafe.iptvlist.ApplicationException;
import com.matafe.iptvlist.Message;

/**
 * Security Check Interceptor
 * 
 * @author matafe@gmail.com
 */
@Secured
@Interceptor
public class SecurityCheckInterceptor {

    @Inject
    SecurityAuthenticator authenticator;

    @AroundInvoke
    public Object checkSecurity(InvocationContext context) throws Exception {

	Object[] params = context.getParameters();

	if (params == null || params.length < 2) {
	    throw new ApplicationException(
		    new Message.Builder().text("Invalid arguments for a security method.").build());
	}

	String username = (String) params[0];
	String password = (String) params[1];

	if (isAdminRoleResouce(context)) {
	    this.authenticator.authenticate(username, password);
	} else {
	    // the url path param for the users are not encrypted.
	    this.authenticator.authenticate(username, password, true);
	}

	Object ret = context.proceed();

	return ret;
    }

    private boolean isAdminRoleResouce(InvocationContext context) {
	return context.getMethod().getAnnotation(Secured.class).value() == Secured.Role.ADMIN;
    }
}
