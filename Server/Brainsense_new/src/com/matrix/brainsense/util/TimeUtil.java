package com.matrix.brainsense.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	
	public static long convertToMillionSecond(int days){
		return days*86400000;
	}
	
	/**
	 * convert String to Date
	 * @param strDate the String date
	 * @return see <code>Date</code>
	 */
	public static Date convert(String strDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * convert from Date to String
	 * @param date <code>Date</code>
	 * @return return a String of Date
	 */
	public static String convert(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	public static Date addDaysToDate(Date date, int days){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	public static String addDaysToString(Date date, int days){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return convert(cal.getTime());
	}
	
	public static String addDays(String date, int days){
		Date date2 = convert(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date2);
		cal.add(Calendar.DATE, days);
		return convert(cal.getTime());
	}
	
	public static boolean isOvertime(String currentTime, String compareTime){
		Date date1 = convert(currentTime);
		Date date2 = convert(compareTime);
		if(date1.before(date2)){
			return false;
		}
		return true;
	}
	
}
