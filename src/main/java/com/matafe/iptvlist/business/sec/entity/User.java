package com.matafe.iptvlist.business.sec.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String fullname;

    private String password;

    private boolean active = true;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar lastUpdated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar validUntil;

    @XmlTransient
    private Calendar loginTime;

    @XmlTransient
    private Calendar lastTokenValidationTime;

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getFullname() {
	return fullname;
    }

    public void setFullname(String fullname) {
	this.fullname = fullname;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public boolean isActive() {
	return active;
    }

    public void setActive(boolean active) {
	this.active = active;
    }

    public Calendar getLastUpdated() {
	return lastUpdated;
    }

    public void setLastUpdated(Calendar lastUpdated) {
	this.lastUpdated = lastUpdated;
    }

    public Calendar getValidUntil() {
	return validUntil;
    }

    public void setValidUntil(Calendar validUntil) {
	this.validUntil = validUntil;
    }

    public void setLoginTime(Calendar loginTime) {
	this.loginTime = loginTime;
    }

    public Calendar getLoginTime() {
	return loginTime;
    }

    public void setLastTokenValidationTime(Calendar time) {
	this.lastTokenValidationTime = time;
    }

    public Calendar getLastTokenValidationTime() {
	return lastTokenValidationTime;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((username == null) ? 0 : username.hashCode());
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
	User other = (User) obj;
	if (username == null) {
	    if (other.username != null)
		return false;
	} else if (!username.equals(other.username))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "User [username=" + username + "]";
    }

}
