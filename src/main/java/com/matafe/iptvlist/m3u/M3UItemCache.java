package com.matafe.iptvlist.m3u;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * M3U Item Cache
 * 
 * @author matafe@gmail.com
 */
//TODO
@Singleton
public class M3UItemCache {

    private Map<String, M3UItem> itemMapperCached = new HashMap<>();

    @Inject
    private M3UParser parser;

    @PostConstruct
    public void initialize() {

	File xmlSourceFile = getXmlSourceFile();

	M3UPlaylist playlist = parser.unmarshall(xmlSourceFile);

	for (M3UItem item : playlist.getItems()) {
	    itemMapperCached.put(item.getTvgName(), item);
	}

    }

    private File getXmlSourceFile() {
	File xmlFile = null;
	String property = System.getProperty("IPTV_SOURCE_XML_FILE_PATH");
	if (property != null) {
	    xmlFile = new File(property);
	} else {
	    xmlFile = new File(getClass().getClassLoader().getResource("source-playlist.xml").getFile());
	}

	return xmlFile;
    }

    public M3UItem getM3UItem(String tvgName) {
	return itemMapperCached.get(tvgName);
    }
}
