package com.matafe.iptvlist.m3u;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * M3U Parser
 * 
 * @author matafe@gmail.com
 */
public class M3UParser {

    public M3UPlaylist unmarshall(File xmlFile) {

	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(M3UPlaylist.class, M3UItem.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    M3UPlaylist playlist = (M3UPlaylist) jaxbUnmarshaller.unmarshal(xmlFile);
	    return playlist;
	} catch (JAXBException e) {
	    throw new RuntimeException("Failed to unmarshall the file", e);
	}
    }

    public void marshall(M3UPlaylist playlist, File outputFile, boolean format) {

	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(M3UPlaylist.class, M3UItem.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    if (format) {
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    }
	    jaxbMarshaller.marshal(playlist, outputFile);
	} catch (JAXBException e) {
	    throw new RuntimeException("Failed to marshall to file", e);
	}
    }

}