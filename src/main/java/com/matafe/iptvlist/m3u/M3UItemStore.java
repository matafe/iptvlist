package com.matafe.iptvlist.m3u;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matafe.iptvlist.m3u.parser.IM3UParser;
import com.matafe.iptvlist.m3u.parser.M3UParserFactory;

/**
 * M3U Item Cache
 * 
 * @author matafe@gmail.com
 */
// TODO
@Singleton
public class M3UItemStore {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<String, M3UItem> cache = new LinkedHashMap<>();

    @Inject
    private M3UParserFactory parserFactory;

    @PostConstruct
    public void initialize() {
	try {
	    load(getSourceFile());
	} catch (Exception e) {
	    logger.error("Failed to load the source playlist", e);
	}

    }

    public void load(File sourceFile) {
	IM3UParser parser = parserFactory.getParserForFile(sourceFile);
	M3UPlaylist playlist = parser.read(sourceFile);
	for (M3UItem item : playlist.getItems()) {
	    cache.put(item.getName(), item);
	}
    }

    public void load(String filename, InputStream is) {
	IM3UParser parser = parserFactory.getParserForFile(new File(filename));
	M3UPlaylist playlist = parser.read(is);
	for (M3UItem item : playlist.getItems()) {
	    cache.put(item.getName(), item);
	}
    }

    public void clear() {
	cache.clear();
    }

    public int realod() {
	cache.clear();
	try {
	    load(getSourceFile());
	} catch (Exception e) {
	    logger.error("Failed to load the source playlist", e);
	}

	return count();
    }

    private File getSourceFile() {
	File sourceFile = null;
	String property = System.getProperty("IPTV_SOURCE_FILE_PATH");
	if (property != null) {
	    sourceFile = new File(property);
	} else {
	    // not shipped!
	    URL resource = getClass().getClassLoader().getResource("source-playlist.m3u");
	    if (resource != null) {
		sourceFile = new File(resource.getFile());
	    }
	}

	return sourceFile;
    }

    public M3UItem getM3UItem(String name) {
	return cache.get(name);
    }

    public int count() {
	return cache.size();
    }

    public List<M3UItem> getItems() {
	return new ArrayList<>(cache.values());
    }

    public M3UPlaylist getSourcePlaylist() {
	M3UPlaylist playlist = new M3UPlaylist();
	playlist.setName("Source");
	playlist.setItems(getItems());
	return playlist;
    }

}
