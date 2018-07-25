package com.matafe.iptvlist.sec;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matafe.iptvlist.sec.ejb.CleanupGuardTimerBean;
import com.matafe.iptvlist.util.DateUtil;

/**
 * Security Manager
 * 
 * @author matafe@gmail.com
 */
public class SecurityManager {

    private Logger logger = LoggerFactory.getLogger(CleanupGuardTimerBean.class);

    @Inject
    private SecurityStore store;

    public void setStore(SecurityStore store) {
	this.store = store;
    }

    public void inactiveExpiredUsers() {

	Calendar now = Calendar.getInstance();

	List<User> inactivated = new ArrayList<>();

	for (User user : store.findAll()) {
	    Calendar validUntil = user.getValidUntil();
	    if (now.after(validUntil)) {
		inactivate(user.getUsername());
		inactivated.add(user);
	    }
	}

	logger.info("{} inactivated users...", inactivated.size());
	for (User user : inactivated) {
	    logger.info("> username: {} - validUntil: {} - fullname: {}", user.getUsername(),
		    DateUtil.format(user.getValidUntil()), user.getFullname());
	}

    }

    public void activate(String username, Calendar validUntil) {
	User user = store.find(username);
	user.setActive(true);
	user.setLastUpdated(Calendar.getInstance());
	user.setValidUntil(validUntil);
	store.update(user);
    }

    public void inactivate(String username) {
	if (!"admin".equals(username)) {
	    User user = store.find(username);
	    user.setActive(false);
	    user.setLastUpdated(Calendar.getInstance());
	    store.update(user);
	}
    }

    public User find(String username) {
	return store.find(username);
    }

    public void add(User user) {
	try {
	    find(user.getUsername());
	    throw new UserAlreadyExistsException(user.getUsername());
	} catch (UserNotFoundException e) {
	    user.setLastUpdated(Calendar.getInstance());
	    store.add(user);
	}
    }

    public List<User> findAll() {
	return new ArrayList<>(store.findAll());
    }

    public void addLogged(User user, String token) {
	user.setLoginTime(Calendar.getInstance());
	store.addLogged(user, token);
    }

    public void remove(String username) {
	if (!"admin".equals(username)) {
	    store.remove(username);
	}
    }

    public User getAndUpdateLogged(String token) {
	return store.getAndUpdateLogged(token);

    }

}
