/*
 * Copyright (C) 2011 The CASIO Android Project
 *
 *
 */
package com.gc.frame.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import android.content.Context;
import android.text.TextUtils;

/**
 *
 */
public class DateUtil {
	public static final int		DATA_FORMAT_SHORT	= 3;
	public static final String	DATE_PATTERN		= "yyyyMMdd";
	private static int			year;
	private static int			month;
	private static int			day;
	private static Calendar		calendar			= Calendar.getInstance();
	static {
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int getYear() {
		return year;
	}

	public static int getMonth() {
		return month;
	}

	public static int getDay() {
		return day;
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		return getYear(toStringFormat(date));
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static int getYear(String date) {
		if (TextUtils.isEmpty(date) == true) {
			return year;
		}

		int year = DateUtil.year;
		if (date.length() == 8 && date.matches("[0-9]*") == true) {
			String yearString = date.substring(0, 4);
			year = Integer.parseInt(yearString);
		} else {
			return -1;
		}
		return year;
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		return getMonth(toStringFormat(date));
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static int getMonth(String date) {
		if (TextUtils.isEmpty(date) == true) {
			return month;
		}

		int month = DateUtil.month;
		if (date.length() == 8 && date.matches("[0-9]*") == true) {
			String monthString = date.substring(4, 6);
			month = Integer.parseInt(monthString);
		} else {
			return -1;
		}
		return month;
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		return getDay(toStringFormat(date));
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static int getDay(String date) {
		if (TextUtils.isEmpty(date) == true) {
			return day;
		}
		int day = DateUtil.day;
		if (date.length() == 8 && date.matches("[0-9]*") == true) {
			String dayString = date.substring(6, 8);
			day = Integer.parseInt(dayString);
		} else {
			return -1;
		}
		return day;
	}

	/**
	 *
	 * @return
	 */
	public static String toStringFormat() {
		return toStringFormat(year, month, day);
	}


	/**
	 *
	 * @param year
	 * @return
	 */
	public static String toStringFormat(int year, int month, int day) {
		return toStringFormat(year, month, day, DATE_PATTERN);
	}

	/**
	 *
	 * @param pattern
	 * @return
	 */
	private static String toStringFormat(int year, int month, int day, String pattern) {
		Date date = new Date(year - 1900, month - 1, day);
		return toStringFormat(date, pattern);
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static String toStringFormat(Date date) {
		return toStringFormat(date, DATE_PATTERN);
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static String toStringFormat(Date date, String pattern) {
		SimpleDateFormat formatString = new SimpleDateFormat(pattern);
		String dateStringFormat = formatString.format(date);

		return dateStringFormat;
	}

	/**
	 *
	 * @return
	 */
	public static String toShortFormat() {
		return toShortFormat(year, month, day);
	}

	public static String toStringFormatDefaultDateFormat(Date date) {
		DateFormat sTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);	// HH:MM style.
		return sTimeFormat.format(date);
	}


	public static String toStringFormatDefaultDateFormat() {
		Date date = new Date(year - 1900, month - 1, day);
		return toStringFormatDefaultDateFormat(date);
	}

	/**
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String toShortFormat(int year, int month, int day) {
		Date date = new Date(year - 1900, month - 1, day);
		return toShortFormat(date);
	}

	public static String toStringFormatDefaultDateFormat(int year, int month, int day) {
		Date date = new Date(year - 1900, month - 1, day);
		return toStringFormatDefaultDateFormat(date);
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static String toShortFormat(Date date) {
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DATA_FORMAT_SHORT);
		return dateFormat.format(date);
	}

	public static String toShortFormat(String date) {
		return toShortFormat(toDate(date));
	}
	
	/**
	 *
	 * @param date
	 */
	public static String toShortFormatByMonthDay(String date) {
		return toShortFormatByMonthDay(toDate(date));
	}

	/**
	 *
	 * @param date
	 */
	public static String toShortFormatByMonthDay(Date date) {
		String dateCompareFrom = DateUtil.toStringFormatDefaultDateFormat(
				1996, getMonth(date), getDay(date));
		String dateCompareTo = DateUtil.toStringFormatDefaultDateFormat(2012,
				getMonth(date), getDay(date));
		String value = toShortFormatValue(dateCompareFrom, dateCompareTo);
		return value;
	}

	/**
	 *
	 * @param date
	 */
	public static String toShortFormatByYearMonth(String date) {
		return toShortFormatByYearMonth(toDate(date));
	}

	/**
	 *
	 * @param date
	 */
	public static String toShortFormatByYearMonth(Date date) {
		String dateCompareFrom = DateUtil.toStringFormatDefaultDateFormat(
				date.getYear(), date.getMonth(), 11);
		String dateCompareTo = DateUtil.toStringFormatDefaultDateFormat(
				date.getYear(), date.getMonth(), 28);
		String value = toShortFormatValue(dateCompareFrom, dateCompareTo);
		return value;
	}

	/**
	 *
	 * @param dateCompareFrom
	 * @param dateCompareTo
	 */
	private static String toShortFormatValue(String dateCompareFrom,
			String dateCompareTo) {
		Pattern pattern = Pattern.compile("[0-9]*");
		String value = "";
		for (int i = 0; i < dateCompareFrom.length(); i++) {
			String from = dateCompareFrom.substring(i, i + 1);
			String to = dateCompareTo.substring(i, i + 1);
			if (from.equals(to) == true) {
				value += from;
			}
		}

		String first = value.substring(0, 1);
		String last = value.substring(value.length() - 1, value.length());

		boolean isFirstCharNumber = pattern.matcher(first).matches();
		boolean isLastCharNumber = pattern.matcher(last).matches();

		if (isFirstCharNumber == false) {
			value = value.substring(1, value.length());
		} else if (isLastCharNumber == false) {
			value = value.substring(0, value.length() - 1);
		} else {
			for (int i = 0; i < value.length(); i++) {
				String v = value.substring(i, i + 1);
				boolean isNumber = pattern.matcher(v).matches();
				if (isNumber == false) {
					value = value.replaceFirst(v, "");
				}
			}
		}
		return value;
	}
	
	public static String toStringFormatDefaultDateFormat(String date) {
		return toStringFormatDefaultDateFormat(toDate(date));
	}
	
	/**
	 * @param date
	 * @param context
	 * @return
	 */
	public static String toStringFormatDefaultDateFormat(String date, Context context) {
		Date dDate = toDate(date);
		if( context == null ) {
			return toStringFormatDefaultDateFormat(dDate);
		}
		DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
		return dateFormat.format(dDate);
	}
	
	public static void update() {
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
	}

	/** 
	 * @return Date
	 */
	public static Date today() {
		update();
		Date date = new Date(year - 1900, month - 1, day);
		return date;
	}

	public static Date toDate(int year, int month, int day) {
		Date date = new Date(year - 1900, month - 1, day);
		return date;
	}

	/**
	 *
	 * @param date
	 *            "yyyyMMdd"
	 */
	public static Date toDate(String date) {
		int year = getYear(date);
		int month = getMonth(date);
		int day = getDay(date);
		Date newDate = new Date(year - 1900, month - 1, day);
		return newDate;
	}

	/**
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date toDate(Date date, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, offset);
		return cal.getTime();
	}

	/**
	 * @param str
	 * @param fmtStr
	 * @return
	 */
	public static Date toDate(String str, String fmtStr) {
		SimpleDateFormat format = new SimpleDateFormat(fmtStr);
		try {
			return format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * @param date
	 *            return
	 */
	public static Date toPreviousDay(Date date) {
		return toPreviousDay(getYear(date), getMonth(date), getDay(date));
	}

	/**
	 *
	 * @param date
	 *            "yyyyMMdd"
	 */
	public static Date toPreviousDay(String date) {
		return toPreviousDay(getYear(date), getMonth(date), getDay(date));
	}

	/**
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date toPreviousDay(int year, int month, int day) {
		int previousMonth = month - 1;
		if (previousMonth == 0) {
			previousMonth = 12;
		}

		int lastDayOfMonth = getDays(year, previousMonth);

		if (month == 1 && day == 1) {
			year -= 1;
		}

		if (day == 1) {
			month = previousMonth;
			day = lastDayOfMonth;
		} else {
			day -= 1;
		}
		return new Date(year - 1900, month - 1, day);
	}

	/**
	 *
	 * @param year
	 */
	public static Date toNextDay(int year, int month, int day, int offSet) {
		Date date = toDate(year, month, day);
		if (0 < offSet) {
			for (int i = 1; i <= offSet; i++) {
				date = toNextDay(date);
			}
		}
		return date;
	}

	/**
	 *
	 * @param date
	 */
	public static Date toNextDay(Date date) {
		return toNextDay(getYear(date), getMonth(date), getDay(date));
	}

	/**
	 *
	 * @param date
	 *            "yyyyMMdd"
	 */
	public static Date toNextDay(String date) {
		return toNextDay(getYear(date), getMonth(date), getDay(date));
	}

	/**
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date toNextDay(int year, int month, int day) {
		int nextMonth = month + 1;
		if (nextMonth == 13) {
			nextMonth = 1;
		}

		int lastDayOfMonth = getDays(year, month);

		if (month == 12 && day == 31) {
			year += 1;
		}

		if (day == lastDayOfMonth) {
			month = nextMonth;
			day = 1;
		} else {
			day += 1;
		}
		return new Date(year - 1900, month - 1, day);
	}

	/**
	 * get last day of the month
	 *
	 * @param month
	 *
	 */
	public static int getDays(int year, int month) {
		int days;

		if (month == 2) {
			if (isLeapYear(year) == true) {
				days = 29;
			} else {
				days = 28;
			}
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			days = 30;
		} else {
			days = 31;
		}
		return days;
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * tell if a leap year
	 *
	 * @return boolean
	 */
	public static boolean isLeapYear(int year) {
		return year % 4 == 0 && (year % 400 == 0 || year % 100 != 0);
	}

	/**
	 * 获得指定日期的前n天
	 *
	 * @param date
	 * @return
	 */
	public static String getSpecifiedDayBefore(Date date,int dayInt,String pattern) {//可以用new Date().toLocalString()传递参数
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - dayInt);

		String dayBefore = new SimpleDateFormat(pattern).format(c
				.getTime());
		return dayBefore;
	}
}
