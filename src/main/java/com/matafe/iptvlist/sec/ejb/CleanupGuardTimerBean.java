package com.matafe.iptvlist.sec.ejb;

import java.util.Calendar;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matafe.iptvlist.sec.SecurityManager;
import com.matafe.iptvlist.util.DateUtil;

@Singleton
public class CleanupGuardTimerBean {

    private Logger logger = LoggerFactory.getLogger(CleanupGuardTimerBean.class);

    @Inject
    private SecurityManager securityManager;

    @Schedule(second = "*", minute = "*", hour = "6,18", persistent = false, info = "Every 6am and 6pm every day timer")
    // For testing purposes
     //@Schedule(second = "*/30", minute = "*", hour = "*", persistent = false, info
     //= "Every 30 second timer")
    public void doInactivation() {

	System.out.println("###########");//temp
	System.out.println("###########");
	System.out.println("###########");
	logger.info("Starting cleanup now... {}", DateUtil.format(Calendar.getInstance()));

	securityManager.inactiveExpiredUsers();

	logger.info("Cleanup finished... {}", DateUtil.format(Calendar.getInstance()));
	System.out.println("###########");
	System.out.println("###########");
	System.out.println("###########");
    }
}
