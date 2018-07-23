package com.matafe.iptvlist.m3u.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.matafe.iptvlist.m3u.M3UItem;
import com.matafe.iptvlist.m3u.M3UPlaylist;

/**
 * M3U Parser
 * 
 * @author matafe@gmail.com
 */
@M3uParserType(M3uParserType.Type.M3U)
public class M3UParser implements IM3UParser {

    private static final String EXTM3U = "#EXTM3U";
    private static final String EXTINF = "#EXTINF";

    private static final String TVG_PREFIX = "TVG-";
    private static final String ATT_TVG_ID = "TVG-ID";
    private static final String ATT_TVG_NAME = "TVG-NAME";
    private static final String ATT_TVG_LOGO = "TVG-LOGO";
    private static final String ATT_GROUP_TITLE = "GROUP-TITLE";

    private static final String HTTP = "HTTP";

    @Override
    public M3UPlaylist read(InputStream fromInputStream) {

	int lineCount = 0;
	try (BufferedReader reader = new BufferedReader(new InputStreamReader(fromInputStream));) {
	    String line = null;

	    M3UPlaylist playlist = new M3UPlaylist();
	    playlist.setName(UUID.randomUUID().toString());

	    M3UItem item = null;

	    while ((line = reader.readLine()) != null) {
		++lineCount;
		// is not header
		if (!(line.equalsIgnoreCase(EXTM3U) || line.trim().equals(""))) {
		    // is a item start?
		    if (line.trim().toUpperCase().startsWith(EXTINF)) {
			item = new M3UItem();
			String data = line.split(":", 2)[1];
			String[] metadataAndName = data.split(",");
			String metadata = metadataAndName[0].trim();
			String[] numberAndMetadata = splitLine(metadata);
			String number = numberAndMetadata[0].trim();
			item.setNumber(Integer.parseInt(number));
			// simple m3u files
			String name = metadataAndName[1].trim();
			item.setName(name);
			item.setTvgId(name);
			item.setTvgName(name);
			if (numberAndMetadata.length > 1) {
			    // complex m3u files with tvg tags
			    for (int i = 1; i < numberAndMetadata.length; i++) {
				String nAndM = numberAndMetadata[i].trim();
				String nAndMNext = numberAndMetadata[++i].trim();
				boolean isTvg = nAndM.toUpperCase().contains(TVG_PREFIX);
				String attKey = nAndM.toUpperCase().trim();
				String attValue = nAndMNext.toUpperCase().trim();
				if (isTvg) {
				    if (attKey.startsWith(ATT_TVG_ID)) {
					item.setTvgId(attValue);
				    } else if (attKey.startsWith(ATT_TVG_NAME)) {
					item.setTvgName(attValue);
				    } else if (attKey.startsWith(ATT_TVG_LOGO)) {
					item.setTvgLogo(attValue);
				    }
				} else {
				    if (attKey.startsWith(ATT_GROUP_TITLE)) {
					item.setGroupTitle(attValue);
				    }
				}
			    }
			}
		    } else if (line.trim().toUpperCase().startsWith(HTTP)) {
			item.setUrl(line.trim());
			playlist.addItem(item);
		    }
		}

	    }

	    return playlist;
	} catch (Exception e) {
	    throw new RuntimeException("Failed to read the file on line: " + lineCount, e);
	}

    }

    @Override
    public M3UPlaylist read(File fromFile) {
	try {
	    return read(new FileInputStream(fromFile));
	} catch (FileNotFoundException e) {
	    throw new RuntimeException("Failed to read the file: " + fromFile.getName(), e);
	}
    }

    private String[] splitLine(String subjectString) {
	List<String> matchList = new ArrayList<>();
	Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
	Matcher regexMatcher = regex.matcher(subjectString);
	while (regexMatcher.find()) {
	    if (regexMatcher.group(1) != null) {
		// Add double-quoted string without the quotes
		matchList.add(regexMatcher.group(1));
	    } else if (regexMatcher.group(2) != null) {
		// Add single-quoted string without the quotes
		matchList.add(regexMatcher.group(2));
	    } else {
		// Add unquoted word
		matchList.add(regexMatcher.group());
	    }
	}
	return matchList.toArray(new String[matchList.size()]);
    }

    @Override
    public void write(M3UPlaylist playlist, File outputFile) {
	this.write(playlist, outputFile, Collections.emptyMap());
    }

    private StringBuilder appendAttribute(StringBuilder sb, String key, String value) {
	sb.append(key).append("=\"").append(value != null ? value : "").append("\"");
	return sb;
    }

    public String convertToString(M3UPlaylist playlist) {
	StringBuilder content = new StringBuilder();
	content.append(EXTM3U).append("\n");

	int i = 1;
	int size = playlist.getItems().size();

	for (M3UItem item : playlist.getItems()) {
	    content.append(EXTINF).append(":").append(item.getNumber());

	    if (item.getTvgId() != null) {
		content.append(" ");
		appendAttribute(content, ATT_TVG_ID, item.getTvgId());
	    }
	    // for simple m3y files. No attribute, only name
	    if (item.getTvgName() != null && item.countNonNullMetadataAttributes() > 1) {
		content.append(" ");
		appendAttribute(content, ATT_TVG_NAME, item.getTvgName());
	    }
	    if (item.getTvgLogo() != null) {
		content.append(" ");
		appendAttribute(content, ATT_TVG_LOGO, item.getTvgLogo());
	    }
	    if (item.getGroupTitle() != null) {
		content.append(" ");
		appendAttribute(content, ATT_GROUP_TITLE, item.getGroupTitle());
	    }
	    content.append(",");
	    content.append(item.getTvgName()).append("\n");
	    content.append(item.getUrl());
	    if (i != size) {
		content.append("\n");
	    }
	    i++;
	}

	return content.toString();

    }

    @Override
    public void write(M3UPlaylist playlist, File outputFile, Map<String, Object> configs) {

	String content = convertToString(playlist);

	try {
	    Files.write(outputFile.toPath(), content.getBytes());
	} catch (IOException e) {
	    throw new RuntimeException("Failed to write to file: " + outputFile.getName(), e);
	}
    }

    @Override
    public void write(M3UPlaylist playlist, Writer writer, Map<String, Object> configs) {
	try {
	    writer.write(convertToString(playlist));
	    writer.flush();
	} catch (IOException e) {
	    throw new RuntimeException("Failed to write to writer", e);
	}

    }

}
