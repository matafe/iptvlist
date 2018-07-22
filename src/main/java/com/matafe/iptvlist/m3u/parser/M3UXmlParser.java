package com.matafe.iptvlist.m3u.parser;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.matafe.iptvlist.m3u.M3UItem;
import com.matafe.iptvlist.m3u.M3UPlaylist;

/**
 * M3U XML Parser
 * 
 * @author matafe@gmail.com
 */
@M3uParserType(M3uParserType.Type.XML)
public class M3UXmlParser implements IM3UParser {

    @Override
    public M3UPlaylist read(File xmlFile) {
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(M3UPlaylist.class, M3UItem.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    M3UPlaylist playlist = (M3UPlaylist) jaxbUnmarshaller.unmarshal(xmlFile);
	    return playlist;
	} catch (JAXBException e) {
	    throw new RuntimeException("Failed to unmarshall the file", e);
	}
    }

    @Override
    public void write(M3UPlaylist playlist, File outputFile) {
	this.write(playlist, outputFile, Collections.emptyMap());
    }

    @Override
    public void write(M3UPlaylist playlist, File outputFile, Map<String, Object> configs) {
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(M3UPlaylist.class, M3UItem.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	    for (Entry<String, Object> entry : configs.entrySet()) {
		// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(entry.getKey(), entry.getValue());
	    }
	    jaxbMarshaller.marshal(playlist, outputFile);
	} catch (

	JAXBException e) {
	    throw new RuntimeException("Failed to marshall to file", e);
	}
    }

}
