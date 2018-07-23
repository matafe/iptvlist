package com.matafe.iptvlist.m3u.parser;

import java.io.File;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;

import com.matafe.iptvlist.m3u.M3UPlaylist;

/**
 * M3U Parser Interface
 * 
 * @author matafe@gmail.com
 */
public interface IM3UParser {

    M3UPlaylist read(InputStream fromInputStream);

    M3UPlaylist read(File fromFile);

    void write(M3UPlaylist playlist, File outputFile);

    void write(M3UPlaylist playlist, File outputFile, Map<String, Object> configs);

    void write(M3UPlaylist playlist, Writer writer, Map<String, Object> configs);
}
