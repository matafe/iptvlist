package com.matafe.iptvlist.m3u;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * M3U Item.
 * 
 * @author matafe@gmail.com
 */
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class M3UItem {

    @XmlAttribute(name = "num")
    private Integer number = 0;

    @XmlAttribute(name = "tvg-id")
    private String tvgId;

    @XmlAttribute(name = "tvg-name")
    private String tvgName;

    @XmlAttribute(name = "tvg-logo")
    private String tvgLogo;

    @XmlAttribute(name = "group-title")
    private String groupTitle;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "url", required = true)
    private String url;

    public M3UItem() {
	super();
    }

    public M3UItem(final String name, final String url, final String groupTitle) {
	this.name = name;
	this.url = url;
	this.groupTitle = groupTitle;
    }

    public Integer getNumber() {
	return number;
    }

    public void setNumber(Integer number) {
	this.number = number;
    }

    public String getTvgId() {
	return tvgId;
    }

    public void setTvgId(String tvgId) {
	this.tvgId = tvgId;
    }

    public String getTvgName() {
	return tvgName;
    }

    public void setTvgName(String tvgName) {
	this.tvgName = tvgName;
    }

    public String getTvgLogo() {
	return tvgLogo;
    }

    public void setTvgLogo(String tvgLogo) {
	this.tvgLogo = tvgLogo;
    }

    public String getGroupTitle() {
	return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
	this.groupTitle = groupTitle;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((url == null) ? 0 : url.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	M3UItem other = (M3UItem) obj;
	if (url == null) {
	    if (other.url != null)
		return false;
	} else if (!url.equals(other.url))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "M3UItem [name=" + name + ", url=" + url + "]";
    }

    public int countNonNullMetadataAttributes() {
	int count = 0;
	count += this.getTvgId() != null ? 1 : 0;
	count += this.getTvgName() != null ? 1 : 0;
	count += this.getTvgLogo() != null ? 1 : 0;
	count += this.getGroupTitle() != null ? 1 : 0;
	return count;

    }

}
