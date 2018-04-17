package com.hotent.core.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat DATETIME_NOSECOND_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public static final DateFormat TIME_NOSECOND_FORMAT = new SimpleDateFormat("HH:mm");
	public static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public static Date parse(String dateString) throws ParseException {
		return (Date) (dateString.trim().indexOf(" ") > 0 && dateString.trim().indexOf(".") > 0
				? new Timestamp(TIMESTAMP_FORMAT.parse(dateString).getTime())
				: (dateString.trim().indexOf(" ") > 0
						? (dateString.trim().indexOf(":") != dateString.trim().lastIndexOf(":")
								? new Timestamp(DATETIME_FORMAT.parse(dateString).getTime())
								: new Timestamp(DATETIME_NOSECOND_FORMAT.parse(dateString).getTime()))
						: (dateString.indexOf(":") > 0
								? (dateString.trim().indexOf(":") != dateString.trim().lastIndexOf(":")
										? new Time(TIME_FORMAT.parse(dateString).getTime())
										: new Time(TIME_NOSECOND_FORMAT.parse(dateString).getTime()))
								: new java.sql.Date(DATE_FORMAT.parse(dateString).getTime()))));
	}

	public static Date parseTime(String dateString) throws ParseException {
		if (dateString.trim().indexOf(" ") > 0) {
			String[] d = dateString.trim().split(" ");
			return dateString.trim().indexOf(":") != dateString.trim().lastIndexOf(":")
					? new Timestamp(TIME_FORMAT.parse(d[1]).getTime())
					: new Timestamp(TIME_NOSECOND_FORMAT.parse(d[1]).getTime());
		} else {
			return (Date) (dateString.indexOf(":") > 0
					? (dateString.trim().indexOf(":") != dateString.trim().lastIndexOf(":")
							? new Time(TIME_FORMAT.parse(dateString).getTime())
							: new Time(TIME_NOSECOND_FORMAT.parse(dateString).getTime()))
					: new java.sql.Date(DATETIME_FORMAT.parse(dateString).getTime()));
		}
	}

	public static String format(Date date) {
		return date instanceof Timestamp
				? TIMESTAMP_FORMAT.format(date)
				: (date instanceof Time
						? TIME_FORMAT.format(date)
						: (date instanceof java.sql.Date ? DATE_FORMAT.format(date) : DATETIME_FORMAT.format(date)));
	}

	public static Date parse(String dateString, String style) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(style);
		return dateFormat.parse(dateString);
	}

	public static String format(Date date, String style) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(style);
		return dateFormat.format(date);
	}

	public static Date parseDate(String dateString) throws ParseException {
		return DATE_FORMAT.parse(dateString);
	}

	public static String formatDate(Date date) {
		return DATE_FORMAT.format(date);
	}

	public static Date parseDateTime(String dateString) throws ParseException {
		return DATETIME_FORMAT.parse(dateString);
	}

	public static String formaDatetTime(Date date) {
		return DATETIME_FORMAT.format(date);
	}

	public static String formatTimeNoSecond(Date date) {
		return DATETIME_NOSECOND_FORMAT.format(date);
	}

	public static Date parseTimeNoSecond(String dateString) throws ParseException {
		return DATETIME_NOSECOND_FORMAT.parse(dateString);
	}

	public static String getNowByString(String style) {
		if (null == style || "".equals(style)) {
			style = "yyyy-MM-dd HH:mm:ss";
		}

		return format(new Date(), style);
	}
}