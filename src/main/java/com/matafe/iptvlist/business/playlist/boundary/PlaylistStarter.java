package com.matafe.iptvlist.business.playlist.boundary;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matafe.iptvlist.business.playlist.control.M3UItemStore;

/**
 * Playlist Starter
 * 
 * @author matafe@gmail.com
 */
@Startup
@Singleton
public class PlaylistStarter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    M3UItemStore store;

    @PostConstruct
    public void init() {
	logger.info("Starting the playlist... loading items");
	store.initialize();
    }
}