package com.matafe.iptvlist.sec;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.ws.rs.NameBinding;

/**
 * Secured Annotation
 * 
 * @author matafe@gmail.com
 */
@Inherited
@NameBinding
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
public @interface Secured {

    @Nonbinding
    Role value() default Role.USER;

    enum Role {
	USER, ADMIN
    }

}
