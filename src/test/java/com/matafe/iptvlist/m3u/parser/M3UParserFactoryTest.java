package com.matafe.iptvlist.m3u.parser;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.matafe.iptvlist.m3u.parser.IM3UParser;
import com.matafe.iptvlist.m3u.parser.M3UParserFactory;
import com.matafe.iptvlist.m3u.parser.M3UXmlParser;

import mockit.integration.junit4.JMockit;

/**
 * M3U Parser Factory Test
 * 
 * @author matafe@gmail.com
 */
@RunWith(JMockit.class)
@Ignore
//TODO
public class M3UParserFactoryTest {

    private M3UParserFactory parserFactory;

    @Before
    public void setUp() throws Exception {
	this.parserFactory = new M3UParserFactory();
    }

    @Test
    public void testGetXmlParser() {
	
	IM3UParser parser = parserFactory.getParserForFile(new File("test.xml"));
	assertThat(parser, notNullValue());
	assertThat(parser, instanceOf(M3UXmlParser.class));

    }

}
