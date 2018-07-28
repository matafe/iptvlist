package com.matafe.iptvlist.business.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.matafe.iptvlist.business.ApplicationException;
import com.matafe.iptvlist.business.Message;

/**
 * Date Util
 * 
 * @author matafe@gmail.com
 */
public class DateUtil {

    private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat PRETTY_DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static Calendar parseCalendar(String dateTime) {
	try {
	    Date parsedDate = DATE_TIME_FORMAT.parse(dateTime);
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(parsedDate);
	    return calendar;
	} catch (ParseException e) {
	    throw new ApplicationException(new Message.Builder().text("Invalid Date Time ''{0}''").build(), dateTime);
	}
    }

    public static String format(Calendar calendar) {
	return DATE_TIME_FORMAT.format(calendar.getTime());
    }

    public static String prettyFormat(Calendar calendar) {
	return PRETTY_DATE_TIME_FORMAT.format(calendar.getTime());
    }

    public static Calendar parseCalendar(Integer year, Integer month, Integer day, Integer hour, Integer min,
	    Integer sec) {
	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.YEAR, year);
	calendar.set(Calendar.MONTH, month);
	calendar.set(Calendar.DAY_OF_MONTH, day);
	if (hour != null) {
	    calendar.set(Calendar.HOUR_OF_DAY, hour);
	} else {
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	}
	if (min != null) {
	    calendar.set(Calendar.MINUTE, min);
	} else {
	    calendar.set(Calendar.MINUTE, 0);
	}
	if (sec != null) {
	    calendar.set(Calendar.SECOND, sec);
	} else {
	    calendar.set(Calendar.SECOND, 0);
	}

	return calendar;
    }

}
