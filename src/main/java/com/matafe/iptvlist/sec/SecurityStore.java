package com.matafe.iptvlist.sec;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.collections4.map.PassiveExpiringMap;

/**
 * Security Store
 * 
 * @author matafe@gmail.com
 */
@ApplicationScoped
public class SecurityStore {

    private static final int TIME_TO_EXPIRE_IN_MINUTES = 30;

    // username and user
    private Map<String, User> users = new ConcurrentHashMap<>();

    // token and user
    PassiveExpiringMap<String, User> loggerUsers = new PassiveExpiringMap<>(TIME_TO_EXPIRE_IN_MINUTES,
	    TimeUnit.MINUTES);

    @PostConstruct
    public void init() {
	addDefaultAdminUser();
    }

    private void addDefaultAdminUser() {
	User admin = new User();
	admin.setUsername("admin");
	admin.setPassword("31OpjXYGJQxxzbOxsedW/g==");
	admin.setFullname("Administrator");
	Calendar validUntil = Calendar.getInstance();
	validUntil.add(Calendar.YEAR, 100);
	admin.setValidUntil(validUntil);
	try {
	    add(admin);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public User find(String username) {
	User user = users.get(username);
	if (user == null) {
	    throw new UserNotFoundException(username);
	}
	return user;
    }

    public void add(User user) {
	try {
	    find(user.getUsername());
	    throw new UserAlreadyExistsException(user.getUsername());
	} catch (UserNotFoundException e) {
	    user.setLastUpdated(Calendar.getInstance());
	    users.put(user.getUsername(), user);
	}
    }

    public List<User> findAll() {
	return new ArrayList<>(users.values());
    }

    public void remove(String username) {
	users.remove(username);
    }

    public void activate(String username, Calendar validUntil) {
	User found = find(username);
	found.setActive(true);
	found.setLastUpdated(Calendar.getInstance());
	found.setValidUntil(validUntil);
	users.put(username, found);
    }

    public void inactivate(String username) {
	User found = find(username);
	found.setActive(false);
	found.setLastUpdated(Calendar.getInstance());
	users.put(username, found);
    }

    public void addLogged(User user, String token) {
	user.setLoginTime(Calendar.getInstance());
	loggerUsers.put(token, user);
    }

    public User getAndUpdateLogged(String token) {
	User user = loggerUsers.get(token);
	// not found or expired!
	if (user == null) {
	    throw new TokenExpiredException(token, String.valueOf(TIME_TO_EXPIRE_IN_MINUTES));
	}

	// inactivated
	if (!user.isActive()) {
	    throw new UserNotFoundException(user.getUsername());
	}
	// update it
	loggerUsers.put(token, user);
	return user;
    }

}
