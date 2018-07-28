package com.matafe.iptvlist.business.playlist.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.matafe.iptvlist.business.playlist.control.parser.IM3UParser;
import com.matafe.iptvlist.business.playlist.control.parser.M3UParser;
import com.matafe.iptvlist.business.playlist.control.parser.M3UXmlParser;
import com.matafe.iptvlist.business.playlist.control.parser.M3uParserType;
import com.matafe.iptvlist.business.playlist.entity.M3UItem;
import com.matafe.iptvlist.business.playlist.entity.M3UPlaylist;

public class PlaylistGenerator {

    private static final String SIMPLE_TEMPLATE = "source-template-simple.m3u";

    private static final String COMPLEX_TEMPLATE = "source-template-complex.m3u";

    public void generateM3uFile(String username, String password, File sourceFile, File m3uTargetFile,
	    String resourceUri) {

	M3UPlaylist targetPlaylist = createTargetPlaylist(username, sourceFile);

	String content = generateM3uFileContent(username, password, targetPlaylist, resourceUri);

	try {
	    Files.write(m3uTargetFile.toPath(), content.toString().getBytes(StandardCharsets.UTF_8));
	    System.out.println(
		    "File generated: " + m3uTargetFile.toPath() + " from source file: " + sourceFile.getName());
	} catch (IOException e) {
	    throw new RuntimeException("Failed to create the target file", e);
	}

    }

    public String generateM3uFileContent(String username, String password, M3UPlaylist playlist, String resourceUri) {

	String template = readTemplate(SIMPLE_TEMPLATE);

	String[] lines = template.split("\n");

	StringBuilder content = new StringBuilder();
	content.append(lines[0]);
	content.append("\n");
	for (M3UItem item : playlist.getItems()) {
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

	return content.toString();

    }

    // TODO
    public void generateComplexList(String username, String password, File sourceFile, File m3uTargetFile,
	    String resourceUri) {

	String template = readTemplate(COMPLEX_TEMPLATE);
	M3UPlaylist targetPlaylist = createTargetPlaylist(username, sourceFile);

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
	    System.out.println(
		    "File generated: " + m3uTargetFile.toPath() + " from source file: " + sourceFile.getName());
	} catch (IOException e) {
	    throw new RuntimeException("Failed to create the target file", e);
	}

    }

    public M3UPlaylist createTargetPlaylist(String playlistName, M3UPlaylist sourcePlaylist) {

	M3UPlaylist targetPlaylist = new M3UPlaylist();
	targetPlaylist.setName(playlistName);

	for (M3UItem sourceItem : sourcePlaylist.getItems()) {
	    M3UItem targetItem = new M3UItem();
	    targetItem.setTvgId(sourceItem.getTvgId());
	    targetItem.setTvgName(sourceItem.getTvgName());
	    targetItem.setTvgLogo(sourceItem.getTvgLogo());
	    targetItem.setGroupTitle(sourceItem.getGroupTitle());
	    targetItem.setName(sourceItem.getName());
	    targetItem.setUrl(sourceItem.getUrl());

	    targetPlaylist.addItem(targetItem);
	}

	return targetPlaylist;
    }

    private M3UPlaylist createTargetPlaylist(String playlistName, File sourceFile) {

	M3uParserType.Type type = sourceFile.getName().toUpperCase().endsWith("XML") ? M3uParserType.Type.XML
		: M3uParserType.Type.M3U;
	IM3UParser parser = null;

	if (type.equals(M3uParserType.Type.XML)) {
	    parser = new M3UXmlParser();
	} else {
	    parser = new M3UParser();
	}

	M3UPlaylist sourcePlaylist = parser.read(sourceFile);

	M3UPlaylist targetPlaylist = createTargetPlaylist(playlistName, sourcePlaylist);

	return targetPlaylist;
    }

    public static void main(String[] args) {
	PlaylistGenerator g = new PlaylistGenerator();

	String username = "test";
	String password = "123456";
	// String sourceFile =
	// "/Users/matafe/workspace/iptvlist/src/main/resources/source-playlist.xml";
	String sourceFile = "/Users/matafe/workspace/iptvlist/src/main/resources/source-playlist.m3u";
	String m3uTargetFile = "target/" + username + ".m3u";
	String resourceUri = "http://localhost:8080/iptvlist/resources/iptv";
	// String resourceUri =
	// "http://iptvlistapp-iptvlist.193b.starter-ca-central-1.openshiftapps.com/";

	g.generateM3uFile(username, password, new File(sourceFile), new File(m3uTargetFile), resourceUri);
    }

    public String readTemplate(String templateFileName) {
	URL resource = getClass().getClassLoader().getResource(templateFileName);

	try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
	    StringBuilder content = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
		content.append(line).append("\n");
	    }
	    return content.toString();

	} catch (IOException e) {
	    throw new RuntimeException("Failed to read the template file", e);
	}
    }

}
