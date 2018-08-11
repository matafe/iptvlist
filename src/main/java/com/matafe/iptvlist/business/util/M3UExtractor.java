package com.matafe.iptvlist.business.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * M3U Net Extractor
 * 
 * @author matafe@gmail.com
 */
public class M3UExtractor {

    public void generateLocalFileFromWeb(String site, String fileName) throws IOException {
	String content = new HttpUtil().get(site);
	saveToLocalFile(content, fileName);
    }

    public void saveToLocalFile(final String content, final String fileName) throws IOException {
	Files.write(Paths.get(fileName), content.getBytes());
    }

    public String readLocalFile(final String fileName) throws IOException {
	String content = new String(Files.readAllBytes(Paths.get(fileName)));
	return content;
    }

    public String extractLocalFile(String fileName) throws IOException {

	String content = readLocalFile(fileName);
	String lineSep = System.getProperty("line.separator");
	String[] lines = content.split(lineSep);

	List<String> channelList = getChannelList();
	List<String> qualitList = getQualitList();
	List<String> ignoreList = getIgnoreList();

	final String prefix = "#EXTINF:-1,";

	StringBuilder sb = new StringBuilder();
	sb.append("#EXTM3U").append(lineSep);

	String group = " group-title=\"CANAIS\"";

	for (String ch : channelList) {
	    boolean chFound = false;
	    q: for (String qlt : qualitList) {
		String channel = (ch + " " + qlt).toUpperCase().trim();
		for (int i = 0; i < lines.length; i++) {
		    String line = lines[i];
		    if (line.startsWith(prefix)) {
			String curChannel = line.substring(prefix.length(), line.length());
			if (curChannel.toUpperCase().equals(channel) && !ignoreList.contains(channel)) {
			    String nl = prefix.substring(0, prefix.length() - 1) + group + "," + channel;
			    sb.append(nl.toUpperCase());
			    sb.append(lineSep);
			    String url = lines[i + 1];
			    sb.append(url);
			    sb.append(lineSep);
			    chFound = true;
			    break q;
			}
		    }
		}
	    }
	    if (!chFound) {
		System.out.println(ch + " not found!");
	    }
	}

	return sb.toString();

    }

    public List<String> getQualitList() {
	List<String> qualit = new ArrayList<>(2);
	qualit.add("FHD");
	qualit.add("HD");
	return qualit;

    }

    public List<String> getIgnoreList() {
	List<String> ch = new ArrayList<>();
	ch.add("COMEDY CENTRAL FHD");
	return ch;
    }

    public List<String> getChannelList() {
	List<String> ch = new ArrayList<>();
	ch.add("A&E");
	ch.add("AMC");
	ch.add("ANIMAL PLANET");
	ch.add("AXN");
	ch.add("BAND");
	ch.add("BAND NEWS");
	ch.add("Band Sports");
	ch.add("BIS");
	ch.add("BOOMERANG");
	ch.add("Cartoon Network");
	ch.add("CINEMAX");
	ch.add("COMBATE");
	ch.add("COMEDY CENTRAL");
	ch.add("DISCOVERY CHANNEL");
	ch.add("Discovery Kids");
	ch.add("DISCOVERY TURBO");
	ch.add("DISCOVERY WORLD");
	ch.add("DISNEY CHANNEL");
	ch.add("DISNEY JR");
	ch.add("ESPN BRASIL");
	ch.add("ESPN");
	ch.add("ESPORTE INTERATIVO 2");
	ch.add("ESPORTE INTERATIVO BR");
	ch.add("ESPORTE INTERATIVO");
	ch.add("FOX");
	ch.add("FOX PREMIUM 2");
	ch.add("FOX SPORTS 2");
	ch.add("GLOBO MINAS");
	ch.add("GLOBO NEWS");
	ch.add("GLOBO RJ");
	ch.add("GLOBO SP");
	ch.add("GLOOB");
	ch.add("GNT");
	ch.add("HBO");
	ch.add("HBO 2");
	ch.add("HBO Family");
	ch.add("HBO Plus");
	ch.add("HBO Signature");
	ch.add("MAX");
	ch.add("MAX UP");
	ch.add("Megapix");
	ch.add("MTV");
	ch.add("Multishow");
	ch.add("NatGeo");
	ch.add("NatGeo Kids");
	ch.add("NATGEO WILD");
	ch.add("Nick JR");
	ch.add("NICKELODEON");
	ch.add("OFF");
	ch.add("RECORD");
	ch.add("RECORD NEWS");
	ch.add("Rede TV");
	ch.add("SBT");
	ch.add("SPORTV");
	ch.add("SPORTV 2");
	ch.add("SPORTV 3");
	ch.add("STUDIO UNIVERSAL");
	ch.add("TELECINE ACTION");
	ch.add("TELECINE FUN");
	ch.add("TELECINE PIPOCA");
	ch.add("TELECINE PREMIUM");
	ch.add("TELECINE TOUCH");
	ch.add("TNT");
	ch.add("WARNER");
	return ch;
    }

    public static void main(String[] args) throws IOException {
	M3UExtractor ext = new M3UExtractor();

	String fileName = "/tmp/fromNet.m3u";
	ext.generateLocalFileFromWeb(args[0], fileName);

	 String newFileContent = ext.extractLocalFile(fileName);
	 String newFileName = "/tmp/newList.m3u";
	 ext.saveToLocalFile(newFileContent, newFileName);
	 System.out.println(newFileName + " created.");

    }

}
