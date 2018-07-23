package com.matafe.iptvlist.sec;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

/**
 * Security Store Test
 * 
 * @author matafe@gmail.com
 */
public class SecurityStoreTest {

    private SecurityStore securityStore;

    @Before
    public void setUp() throws Exception {
	this.securityStore = new SecurityStore();
    }

    @Test
    public void testFind() {
	User user = new User();
	user.setUsername("user0");
	user.setPassword("passwd0");

	securityStore.add(user);

	User found = securityStore.find(user.getUsername());

	assertThat(found, equalTo(user));
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindNotFound() {
	securityStore.find("notexist");
    }

    @Test
    public void testAdd() {

	User user = new User();
	user.setUsername("user0");
	user.setPassword("passwd0");

	securityStore.add(user);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testAddDuplicated() {

	User user = new User();
	user.setUsername("user0");
	user.setPassword("passwd0");

	securityStore.add(user);
	securityStore.add(user);
    }

    @Test
    public void testFindAll() {
	int num = 10;
	for (int i = 0; i < num; i++) {
	    User user = new User();
	    user.setUsername("user" + i);
	    user.setPassword("passwd" + i);

	    securityStore.add(user);
	}

	Collection<User> users = securityStore.findAll();
	assertThat(users.size(), equalTo(num));
    }

}
