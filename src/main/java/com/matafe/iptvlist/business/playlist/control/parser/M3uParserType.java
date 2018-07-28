package com.matafe.iptvlist.business.playlist.control.parser;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * M3U Parser Type Qualifier.
 * 
 * @author matafe@gmail.com
 */
@Qualifier
@Retention(RUNTIME)
@Target({ TYPE, METHOD, FIELD, PARAMETER, CONSTRUCTOR })
public @interface M3uParserType {

    Type value();

    enum Type {
	XML, M3U
    }

}