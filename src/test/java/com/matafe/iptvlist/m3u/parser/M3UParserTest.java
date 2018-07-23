package com.matafe.iptvlist.m3u.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.matafe.iptvlist.m3u.M3UItem;
import com.matafe.iptvlist.m3u.M3UPlaylist;

/**
 * M3U Parser Unit Test
 * 
 * @author matafe@gmail.com
 */
public class M3UParserTest {

    IM3UParser parser;

    @Before
    public void setUp() throws Exception {
	this.parser = new M3UParser();
    }

    @Test
    public void testReadSimple() {
	File m3uFile = new File(getClass().getClassLoader().getResource("test-playlist-simple.m3u").getFile());
	M3UPlaylist playlist = parser.read(m3uFile);
	assertThat(playlist.getName(), notNullValue());
	assertThat(playlist.getItems().size(), equalTo(3));
	for (int i = 0; i < playlist.getItems().size(); i++) {
	    assertThat(playlist.getItems().get(i).getNumber(), equalTo(-1));
	    assertThat(playlist.getItems().get(i).getTvgId(), equalTo(playlist.getItems().get(i).getTvgName()));
	    assertThat(playlist.getItems().get(i).getTvgName(), equalTo("TVG-NAME-" + i));
	    assertThat(playlist.getItems().get(i).getUrl(), equalTo("HTTP://TEST.TS-" + i));
	}
    }

    @Test
    public void testReadComplex01() {
	File m3ulFile = new File(getClass().getClassLoader().getResource("test-playlist-complex-1.m3u").getFile());
	M3UPlaylist playlist = parser.read(m3ulFile);
	assertThat(playlist.getName(), notNullValue());
	assertThat(playlist.getItems().size(), equalTo(3));
	for (int i = 0; i < playlist.getItems().size(); i++) {
	    assertThat(playlist.getItems().get(i).getNumber(), equalTo(-1));
	    assertThat(playlist.getItems().get(i).getTvgId(), equalTo("TVG-ID-" + i));
	    assertThat(playlist.getItems().get(i).getTvgName(), equalTo("TVG-NAME-" + i));
	    assertThat(playlist.getItems().get(i).getTvgLogo(), equalTo("TVG-LOGO-" + i));
	    assertThat(playlist.getItems().get(i).getGroupTitle(), equalTo("GROUP-TITLE-" + i));
	    assertThat(playlist.getItems().get(i).getUrl(), equalTo("HTTP://TEST.TS-" + i));
	}
    }

    @Test
    public void testWriteSimple() throws Exception {
	File m3uFile = new File("target/playlistTestSimple.m3u");
	if (m3uFile.exists()) {
	    m3uFile.delete();
	}

	M3UPlaylist playlist = new M3UPlaylist();
	// playlist.setName("MyPlaylist");
	List<M3UItem> items = new ArrayList<>();
	playlist.setItems(items);

	for (int i = 0; i < 10; i++) {
	    M3UItem item = new M3UItem();
	    item.setTvgName("TVG-NAME-" + i);
	    item.setUrl("HTTP://TEST.TS-" + i);
	    items.add(item);
	}

	this.parser.write(playlist, m3uFile);

	assertThat(m3uFile.exists(), is(true));

	M3UPlaylist playlist2 = this.parser.read(m3uFile);

	assertThat(playlist2.getItems().size(), equalTo(playlist.getItems().size()));
	assertThat(playlist2.getItems().get(0).getTvgName(), equalTo(playlist.getItems().get(0).getTvgName()));
	assertThat(playlist2.getItems().get(0).getUrl(), equalTo(playlist.getItems().get(0).getUrl()));

    }

    @Test
    public void testWriteComplex() throws Exception {
	File m3uFile = new File("target/playlistTestComplex.m3u");
	if (m3uFile.exists()) {
	    m3uFile.delete();
	}

	M3UPlaylist playlist = new M3UPlaylist();
	// playlist.setName("MyPlaylist");
	List<M3UItem> items = new ArrayList<>();
	playlist.setItems(items);

	for (int i = 0; i < 10; i++) {
	    M3UItem item = new M3UItem();
	    item.setTvgId("TVG-ID-" + i);
	    item.setTvgName("TVG-NAME-" + i);
	    item.setTvgLogo("TVG-LOGO-" + i);
	    item.setGroupTitle("GROUP-TITLE-" + i);
	    item.setUrl("HTTP://TEST.TS-" + i);
	    items.add(item);
	}

	this.parser.write(playlist, m3uFile);

	assertThat(m3uFile.exists(), is(true));

	M3UPlaylist playlist2 = this.parser.read(m3uFile);

	// assertThat(playlist2.getName(), equalTo(playlist.getName()));
	assertThat(playlist2.getItems().size(), equalTo(playlist.getItems().size()));
	assertThat(playlist2.getItems().get(0).getTvgId(), equalTo(playlist.getItems().get(0).getTvgId()));
	assertThat(playlist2.getItems().get(0).getTvgName(), equalTo(playlist.getItems().get(0).getTvgName()));
	assertThat(playlist2.getItems().get(0).getTvgLogo(), equalTo(playlist.getItems().get(0).getTvgLogo()));
	assertThat(playlist2.getItems().get(0).getGroupTitle(), equalTo(playlist.getItems().get(0).getGroupTitle()));
	assertThat(playlist2.getItems().get(0).getUrl(), equalTo(playlist.getItems().get(0).getUrl()));

    }

    @Test
    public void testWriteToStringWriter() {
	File m3uFile = new File(getClass().getClassLoader().getResource("test-playlist-simple.m3u").getFile());
	M3UPlaylist playlist = parser.read(m3uFile);
	StringWriter sw = new StringWriter();
	parser.write(playlist, sw, null);
	String content = sw.toString();
	System.out.println(content);
	assertThat(content, notNullValue());
    }

    // @Test
    // public void testUnmarshallSourcePlaylistIfPresent() {
    // URL resource =
    // getClass().getClassLoader().getResource("source-playlist.xml");
    // if (resource != null) {
    // File xmlFile = new File(resource.getFile());
    // M3UPlaylist playlist = parser.read(xmlFile);
    // assertThat(playlist.getName(), equalTo("SourcePlaylist"));
    // assertThat(playlist.getItems().size() > 0, is(true));
    // }
    // }

}
