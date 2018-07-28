package com.matafe.iptvlist.business.sec.boundary;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.ws.rs.NameBinding;

/**
 * Secured Annotation for Path Parameter
 * 
 * @author matafe@gmail.com
 */
@Inherited
@NameBinding
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
public @interface ParamSecured {

    @Nonbinding
    Role value() default Role.USER;

    enum Role {
	USER, ADMIN
    }

}
