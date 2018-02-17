package com.xmg.p2p.base.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * day03_10
 */
public class DateUtil {

	/**
	 * 得到一天的最后一秒钟
	 * truncate方法得到所给日期的0时0分0秒
	 * addDays加一天得到第二天的0时0分0秒
	 * addSeconds减一秒得到所给日期的23时59分59秒
	 * @param d
	 * @return
	 */
	public static Date endOfDay(Date d) {
		return DateUtils.addSeconds( DateUtils.addDays(DateUtils.truncate(d, Calendar.DATE), 1), -1);
	}

	/**
	 * 两个时间的间隔秒
	 * @return
	 */
	public static long secondsBetween(Date d1, Date d2) {
		return Math.abs((d1.getTime() - d2.getTime()) / 1000);
	}
}