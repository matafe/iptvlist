package com.matafe.iptvlist.util;

/**
 * String Util
 * 
 * @author matafe@gmail.com
 */
public class StringUtil {

    public static boolean isBlank(String str) {
	return str == null || str.trim().isEmpty();
    }

}
