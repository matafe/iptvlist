package com.matafe.iptvlist.business.playlist.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matafe.iptvlist.business.ApplicationException;
import com.matafe.iptvlist.business.Message;
import com.matafe.iptvlist.business.playlist.control.parser.IM3UParser;
import com.matafe.iptvlist.business.playlist.control.parser.M3UParserFactory;
import com.matafe.iptvlist.business.playlist.entity.M3UItem;
import com.matafe.iptvlist.business.playlist.entity.M3UPlaylist;
import com.matafe.iptvlist.business.util.StringUtil;

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

    public void initialize() {
	load(getSourceFileName(), getSourceInputStream());
    }

    private String getSourceFileName() {
	return isSourceFileProvided() ? System.getProperty("IPTV_SOURCE_FILE_PATH") : "source-playlist.m3u";
    }

    // public void load(File sourceFile) {
    // IM3UParser parser = parserFactory.getParserForFile(sourceFile);
    // M3UPlaylist playlist = parser.read(sourceFile);
    // for (M3UItem item : playlist.getItems()) {
    // cache.put(item.getName(), item);
    // }
    // }

    public void load(String filename, InputStream is) {
	if (is != null) {
	    IM3UParser parser = parserFactory.getParserForFile(new File(filename));
	    M3UPlaylist playlist = parser.read(is);
	    for (M3UItem item : playlist.getItems()) {
		cache.put(item.getName(), item);
	    }
	}
    }

    public void clear() {
	cache.clear();
    }

    public int realod() {
	cache.clear();
	try {
	    load(getSourceFileName(), getSourceInputStream());
	} catch (Exception e) {
	    logger.error("Failed to reload the source playlist", e);
	}

	return count();
    }

    // private File getSourceFile() {
    // File sourceFile = null;
    // String property = System.getProperty("IPTV_SOURCE_FILE_PATH");
    // if (property != null) {
    // sourceFile = new File(property);
    // } else {
    // // not shipped!
    // URL resource =
    // getClass().getClassLoader().getResource("source-playlist.m3u");
    // if (resource != null) {
    // sourceFile = new File(resource.getFile());
    // }
    // }
    //
    // return sourceFile;
    // }

    private boolean isSourceFileProvided() {
	return !StringUtil.isBlank(System.getProperty("IPTV_SOURCE_FILE_PATH"));
    }

    private InputStream getSourceInputStream() {
	String property = System.getProperty("IPTV_SOURCE_FILE_PATH");
	InputStream is = null;
	try {
	    if (property != null) {
		is = new FileInputStream(new File(property));
	    } else {
		// if not shipped!
		String fileName = "source-playlist.m3u";
		is = M3UItemStore.class.getResourceAsStream(fileName);
		if (is == null) {
		    is = getClass().getClassLoader().getResourceAsStream(fileName);
		    if (is == null) {
			is = ClassLoader.getSystemResourceAsStream(fileName);
		    }
		}
	    }
	} catch (IOException e) {
	    logger.error("Failed to load the source playlist", e);
	}

	if (is == null) {
	    logger.error("Could not find any source playlist to load!");
	}

	return is;
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

    public M3UItem getItem(String channelName) {
	M3UItem item = cache.get(channelName);
	if (item == null) {
	    throw new ApplicationException(new Message.Builder().text("Item {0} not found!").build(), channelName);
	}
	return item;

    }

    public M3UPlaylist getSourcePlaylist() {
	M3UPlaylist playlist = new M3UPlaylist();
	playlist.setName("Source");
	playlist.setItems(getItems());
	return playlist;
    }

    public void updateUrl(M3UItem item) {
	M3UItem found = getItem(item.getName());
	found.setUrl(item.getUrl());
	cache.replace(item.getName(), item);
    }

}
