package com.matafe.iptvlist.m3u.parser;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.matafe.iptvlist.business.playlist.control.parser.IM3UParser;
import com.matafe.iptvlist.business.playlist.control.parser.M3UParser;
import com.matafe.iptvlist.business.playlist.entity.M3UPlaylist;

/**
 * M3U Parser Unit Test For New Lists
 * 
 * @author matafe@gmail.com
 */
public class M3UParserNewListsTest {

    IM3UParser parser;

    @Before
    public void setUp() throws Exception {
	this.parser = new M3UParser();
    }

    @Test
    @Ignore
    public void testReadRealFiles() {
	File m3ulFile = new File(getClass().getClassLoader().getResource("a.m3u").getFile());
	M3UPlaylist playlist = parser.read(m3ulFile);
	for (int i = 0; i < playlist.getItems().size(); i++) {
	    System.out.println(playlist.getItems().get(i).getNumber());
	    System.out.println(playlist.getItems().get(i).getTvgId());
	    System.out.println(playlist.getItems().get(i).getTvgName());
	    System.out.println(playlist.getItems().get(i).getTvgLogo());
	    System.out.println(playlist.getItems().get(i).getGroupTitle());
	    System.out.println(playlist.getItems().get(i).getUrl());
	}
	System.out.println("Total: " + playlist.getItems().size());
    }

}
