package com.matafe.iptvlist.sec;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Security Store Test
 * 
 * @author matafe@gmail.com
 */
public class SecurityManagerTest {

    private SecurityManager securityManager;

    @Before
    public void setUp() throws Exception {
	this.securityManager = new SecurityManager();
	this.securityManager.setStore(new SecurityStore());
    }

    @Test
    public void testFind() {
	User user = new User();
	user.setUsername("user0");
	user.setPassword("passwd0");

	securityManager.add(user);

	User found = securityManager.find(user.getUsername());

	assertThat(found, equalTo(user));
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindNotFound() {
	securityManager.find("notexist");
    }

    @Test
    public void testAdd() {

	User user = new User();
	user.setUsername("user0");
	user.setPassword("passwd0");

	securityManager.add(user);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testAddDuplicated() {

	User user = new User();
	user.setUsername("user0");
	user.setPassword("passwd0");

	securityManager.add(user);
	securityManager.add(user);
    }

    @Test
    public void testFindAll() {
	int num = 10;
	for (int i = 0; i < num; i++) {
	    User user = new User();
	    user.setUsername("user" + i);
	    user.setPassword("passwd" + i);

	    securityManager.add(user);
	}

	List<User> users = securityManager.findAll();
	assertThat(users.size(), equalTo(num));
    }

}
