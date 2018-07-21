package com.matafe.iptvlist.sec;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.matafe.iptvlist.ApplicationException;

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
	    throw new ApplicationException("Invalid arguments for a security method!!!");
	}

	String username = (String) params[0];
	String password = (String) params[1];

	this.authenticator.authenticate(username, password);

	Object ret = context.proceed();

	return ret;
    }
}
