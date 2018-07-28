package com.matafe.iptvlist.business.sec.control;

import java.util.Calendar;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matafe.iptvlist.business.sec.boundary.SecurityManager;
import com.matafe.iptvlist.business.util.DateUtil;

/**
 * Clenup expired users.
 * 
 * @author matafe@gmail.com
 */
@Singleton
public class CleanupGuardTimerBean {

    private Logger logger = LoggerFactory.getLogger(CleanupGuardTimerBean.class);

    @Inject
    private SecurityManager securityManager;

    @Schedule(second = "0", minute = "0", hour = "6,18", persistent = false, info = "Every 6am and 6pm every day timer")
    public void doInactivation() {

	logger.info("Starting cleanup now... {}", DateUtil.format(Calendar.getInstance()));

	securityManager.inactiveExpiredUsers();

	logger.info("Cleanup finished... {}", DateUtil.format(Calendar.getInstance()));
    }
}
