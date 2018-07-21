package com.matafe.iptvlist.m3u;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * M3U Parser Unit Test
 * 
 * @author matafe@gmail.com
 */
public class M3UParserTest {

    M3UParser parser;

    @Before
    public void setUp() throws Exception {
	this.parser = new M3UParser();
    }

    @Test
    public void testUnmarshall() {
	File xmlFile = new File(getClass().getClassLoader().getResource("test-playlist.xml").getFile());
	M3UPlaylist playlist = parser.unmarshall(xmlFile);
	assertThat(playlist.getName(), equalTo("MyPlaylist"));
	assertThat(playlist.getItems().size(), equalTo(2));
	for (int i = 0; i < playlist.getItems().size(); i++) {
	    assertThat(playlist.getItems().get(i).getTvgId(), equalTo("TVG-ID-" + i));
	    assertThat(playlist.getItems().get(i).getTvgName(), equalTo("TVG-NAME-" + i));
	    assertThat(playlist.getItems().get(i).getTvgLogo(), equalTo("TVG-LOGO-" + i));
	    assertThat(playlist.getItems().get(i).getGroupTitle(), equalTo("GROUP-TITLE-" + i));
	    assertThat(playlist.getItems().get(i).getUrl(), equalTo("URL-" + i));
	}
    }

    @Test
    public void testMarshall() throws Exception {
	File xmlFile = new File("target/playlistTest.xml");
	if (xmlFile.exists()) {
	    xmlFile.delete();
	}

	M3UPlaylist playlist = new M3UPlaylist();
	playlist.setName("MyPlaylist");
	List<M3UItem> items = new ArrayList<>();
	playlist.setItems(items);

	for (int i = 0; i < 10; i++) {
	    M3UItem item = new M3UItem();
	    item.setTvgId("TVG-ID-" + i);
	    item.setTvgName("TVG-NAME-" + i);
	    item.setTvgLogo("TVG-LOGO-" + i);
	    item.setGroupTitle("GROUP-TITLE-" + i);
	    item.setUrl("URL-" + i);
	    items.add(item);
	}

	this.parser.marshall(playlist, xmlFile, true);

	assertThat(xmlFile.exists(), is(true));

	M3UPlaylist playlist2 = this.parser.unmarshall(xmlFile);

	assertThat(playlist2.getName(), equalTo(playlist.getName()));
	assertThat(playlist2.getItems().size(), equalTo(playlist.getItems().size()));
	assertThat(playlist2.getItems().get(0).getTvgId(), equalTo(playlist.getItems().get(0).getTvgId()));
	assertThat(playlist2.getItems().get(0).getTvgName(), equalTo(playlist.getItems().get(0).getTvgName()));
	assertThat(playlist2.getItems().get(0).getTvgLogo(), equalTo(playlist.getItems().get(0).getTvgLogo()));
	assertThat(playlist2.getItems().get(0).getGroupTitle(), equalTo(playlist.getItems().get(0).getGroupTitle()));
	assertThat(playlist2.getItems().get(0).getUrl(), equalTo(playlist.getItems().get(0).getUrl()));

    }

    @Test
    public void testUnmarshallSourcePlaylistIfPresent() {
	URL resource = getClass().getClassLoader().getResource("source-playlist.xml");
	if (resource != null) {
	    File xmlFile = new File(resource.getFile());
	    M3UPlaylist playlist = parser.unmarshall(xmlFile);
	    assertThat(playlist.getName(), equalTo("SourcePlaylist"));
	    assertThat(playlist.getItems().size() > 0, is(true));
	}
    }

}
