package com.xmg.p2p.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	 * day03_10
	 */
	public static Date endOfDay(Date d) {
		return DateUtils.addSeconds( DateUtils.addDays(DateUtils.truncate(d, Calendar.DATE), 1), -1);
	}

	/**
	 * 两个时间的间隔秒
	 * day04_07
	 */
	public static long secondsBetween(Date d1, Date d2) {
		return Math.abs((d1.getTime() - d2.getTime()) / 1000);
	}


	/**
	 * 将自定义格式(format)的日期字符串,转换得到时间戳字符串
	 * @param dateString 自定义格式的日期字符串
	 * @param format 自定义的时间格式
	 * @return timeStamp 时间戳字符串
	 */
	public static String getTimeStampFromString(String dateString , String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);	//得到日期格式器
		Date date = null;
		try {
			date = dateFormat.parse(dateString);						//格式器根据日期字符串解析得到Date对象
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long longTimeStamp = date.getTime();										//根据日期对象得到时间戳
		String timeStamp = longTimeStamp+"";										//将时间戳由long转为字符串
		return timeStamp;
	}

	/**
	 * 将时间戳字符串自定义转换,得到格式(format)的日期字符串
	 * @param timeStamp 时间戳字符串
	 * @param format 自定义的时间格式
	 * @return dateString 自定义格式的日期字符串
	 */
	public static String getStringFromTimeStamp(String timeStamp , String format){
		long longTimeStamp = Long.parseLong(timeStamp);					//将时间戳由字符串转为long
		Date date = new Date(longTimeStamp);							//根据时间戳得到日期对象
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);		//根据格式得到日期格式解析器
		String dateString = dateFormat.format(date);					//格式器将Date对象格式成日期字符串
		return dateString;
	}

	/**
	 * 将Date类型对象自定义转换,得到格式(format)的日期字符串
	 * @param date 日期对象
	 * @param format 自定义的时间格式
	 * @return dateString 自定义格式的日期字符串
	 */
	public static String getStringFromDate(Date date , String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);		//根据格式得到日期格式解析器
		String dateString = dateFormat.format(date);					//格式器将Date对象格式成日期字符串
		return dateString;
	}

	/**
	 * 将指定格式的日期字符串转换为Date类型
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Date getDateFromString(String dateString , String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获得当前时间的Date对象
	 * @return 当前时间的date对象
	 */
	public static Date getCurrentDate(){
		long longTimeStamp = System.currentTimeMillis();
		Date date = new Date(longTimeStamp);
		return date;
	}

	/**
	 * 获取当前时间的时间戳字符串
	 * @return timeStamp时间戳字符串
	 */
	public static String getCurrntTimeStamp(){
		long longTimeStamp = System.currentTimeMillis();
		String timeStamp = longTimeStamp+"";
		return timeStamp;
	}

	/**
	 * 根据自定义日期格式,得到当前时间的日期字符串
	 * @param format 自定义日期格式
	 * @return dateString日期字符串
	 */
	public static String getCurrentString(String format){
		long longTimeStamp = System.currentTimeMillis();
		Date date = new Date(longTimeStamp);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String dateString = dateFormat.format(date);
		return dateString;
	}

}