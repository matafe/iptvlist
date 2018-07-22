package com.matafe.iptvlist.m3u.parser;

import java.io.File;
import java.util.Map;

import com.matafe.iptvlist.m3u.M3UPlaylist;

/**
 * M3U Parser Interface
 * 
 * @author matafe@gmail.com
 */
public interface IM3UParser {

    M3UPlaylist read(File fromFile);

    void write(M3UPlaylist playlist, File outputFile);

    void write(M3UPlaylist playlist, File outputFile, Map<String, Object> configs);
}
