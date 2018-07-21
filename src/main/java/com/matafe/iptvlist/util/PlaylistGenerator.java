package com.matafe.iptvlist.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.matafe.iptvlist.m3u.M3UItem;
import com.matafe.iptvlist.m3u.M3UParser;
import com.matafe.iptvlist.m3u.M3UPlaylist;

public class PlaylistGenerator {

    public void generate(String username, String password, File xmlSourceFile, File m3uTargetFile, String resourceUri) {

	String template = readTemplate();
	M3UPlaylist targetPlaylist = createTargetPlaylist(username, xmlSourceFile);

	String[] lines = template.split("\n");

	StringBuilder content = new StringBuilder();
	content.append(lines[0]);
	content.append("\n");
	for (M3UItem item : targetPlaylist.getItems()) {
	    String line1 = lines[1].replaceAll("\\$\\{channelname\\}", item.getTvgName());
	    content.append(line1);
	    content.append("\n");
	    String line2 = lines[2].replaceAll("\\$\\{uri\\}", resourceUri);
	    line2 = line2.replaceAll("\\$\\{username\\}", username);
	    line2 = line2.replaceAll("\\$\\{password\\}", password);
	    line2 = line2.replaceAll("\\$\\{channelname\\}", item.getTvgName());
	    content.append(line2);
	    content.append("\n");
	}

	try {
	    Files.write(m3uTargetFile.toPath(), content.toString().getBytes(StandardCharsets.UTF_8));
	    System.out.println("File generated: " + m3uTargetFile.toPath());
	} catch (IOException e) {
	    throw new RuntimeException("Failed to create the target file", e);
	}

    }

    private M3UPlaylist createTargetPlaylist(String playlistName, File xmlSourceFile) {
	M3UParser parser = new M3UParser();
	M3UPlaylist sourcePlaylist = parser.unmarshall(xmlSourceFile);

	M3UPlaylist targetPlaylist = new M3UPlaylist();
	targetPlaylist.setName(playlistName);

	for (M3UItem sourceItem : sourcePlaylist.getItems()) {
	    M3UItem targetItem = new M3UItem();
	    targetItem.setTvgId(sourceItem.getTvgId());
	    targetItem.setTvgName(sourceItem.getTvgName());
	    targetItem.setTvgLogo(sourceItem.getTvgLogo());
	    targetItem.setGroupTitle(sourceItem.getGroupTitle());
	    targetItem.setUrl(sourceItem.getUrl());

	    targetPlaylist.addItem(targetItem);
	}

	return targetPlaylist;
    }

    public static void main(String[] args) {
	PlaylistGenerator g = new PlaylistGenerator();

	String username = "matafe";
	String password = "123456";
	String xmlSourceFile = "/Users/matafe/workspace/iptvlist/src/main/resources/source-playlist.xml";
	String m3uTargetFile = "target/" + username + ".m3u";
	String resourceUri = "http://localhost:8080/iptvlist/resources/iptv";

	g.generate(username, password, new File(xmlSourceFile), new File(m3uTargetFile), resourceUri);
    }

    public String readTemplate() {
	File file = new File(getClass().getClassLoader().getResource("list-template.m3u").getFile());
	Path template = file.toPath();

	try {
	    BufferedReader reader = Files.newBufferedReader(template, StandardCharsets.UTF_8);
	    StringBuilder content = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		content.append(line).append("\n");
	    }

	    return content.toString();

	} catch (IOException e) {
	    throw new RuntimeException("Failed to read the template file", e);
	}
    }

}
