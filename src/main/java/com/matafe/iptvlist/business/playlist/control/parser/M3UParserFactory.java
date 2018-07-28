package com.matafe.iptvlist.business.playlist.control.parser;

import java.io.File;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * M3U Parser Factory
 * 
 * @author matafe@gmail.com
 */
public class M3UParserFactory {

    @Any
    @Inject
    private Instance<IM3UParser> parsers;

    public IM3UParser getParserForFile(File file) {
	M3uParserType.Type type = file.getName().toUpperCase().endsWith("XML") ? M3uParserType.Type.XML
		: M3uParserType.Type.M3U;
	return getParser(type);
    }

    private IM3UParser getParser(M3uParserType.Type type) {
	return parsers.select(M3UParserTypeAnnotationLiteral.type(type)).get();
    }

}
