package com.matafe.iptvlist.m3u;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * M3U Playlist.
 * 
 * @author matafe@gmail.com
 */
@XmlRootElement(name = "playlist")
@XmlAccessorType(XmlAccessType.FIELD)
public class M3UPlaylist {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "item")
    private List<M3UItem> items;

    public M3UPlaylist() {
	this.items = new ArrayList<>();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<M3UItem> getItems() {
	return items;
    }

    public void setItems(List<M3UItem> items) {
	this.items = items;
    }

    public void addItem(M3UItem item) {
	this.items.add(item);
    }

    public void remoteItem(M3UItem item) {
	this.items.remove(item);
    }

    @Override
    public String toString() {
	return "M3UPlaylist [name=" + name + ", items=" + items.size() + "]";
    }

}