package com.matafe.iptvlist.m3u;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.matafe.iptvlist.m3u.parser.IM3UParser;
import com.matafe.iptvlist.m3u.parser.M3UParserFactory;

/**
 * M3U Item Cache
 * 
 * @author matafe@gmail.com
 */
// TODO
@Singleton
public class M3UItemCache {

    private Map<String, M3UItem> itemMapperCached = new HashMap<>();

    @Inject
    private M3UParserFactory parserFactory;

    @PostConstruct
    public void initialize() {

	File sourceFile = getSourceFile();

	IM3UParser parser = parserFactory.getParserForFile(sourceFile);

	M3UPlaylist playlist = parser.read(sourceFile);

	for (M3UItem item : playlist.getItems()) {
	    itemMapperCached.put(item.getTvgName(), item);
	}

    }

    private File getSourceFile() {
	File sourceFile = null;
	String property = System.getProperty("IPTV_SOURCE_FILE_PATH");
	if (property != null) {
	    sourceFile = new File(property);
	} else {
	    sourceFile = new File(getClass().getClassLoader().getResource("source-playlist.m3u").getFile());
	}

	return sourceFile;
    }

    public M3UItem getM3UItem(String tvgName) {
	return itemMapperCached.get(tvgName);
    }
}
