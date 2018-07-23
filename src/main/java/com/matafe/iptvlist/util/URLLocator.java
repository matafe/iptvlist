package com.matafe.iptvlist.util;

import javax.inject.Inject;

import com.matafe.iptvlist.m3u.M3UItem;
import com.matafe.iptvlist.m3u.M3UItemStore;

/**
 * URL Locator
 * 
 * @author matafe@gmail.com
 */
public class URLLocator {

    private static final String URL_NOT_FOUND = "NOT_FOUND";

    @Inject
    private M3UItemStore itemStore;

    public String locate(String channelName) {
	M3UItem item = itemStore.getM3UItem(channelName);
	String url = item != null ? item.getUrl() : URL_NOT_FOUND;
	return url;
    }
}
