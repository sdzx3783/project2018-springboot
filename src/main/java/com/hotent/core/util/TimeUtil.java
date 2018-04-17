package com.hotent.core.util;

import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.DateFormatUtil;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static final int SECOND = 0;
	public static final int MINUTE = 1;
	public static final int HOUR = 2;
	public static final int DAY = 3;
	public static final int MONTH = 4;

	public static Date convertString(String value, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (value == null) {
			return null;
		} else {
			try {
				return sdf.parse(value);
			} catch (Exception arg3) {
				return null;
			}
		}
	}

	public static Date convertString(String value) {
		return convertString(value, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date convertDateString(String value) {
		return convertString(value, "yyyy-MM-dd");
	}

	public static String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return formatter.format(date);
	}

	public static String getDateTimeString(long millseconds) {
		return getDate(millseconds, "yyyy-MM-dd HH:mm:ss");
	}

	public static String getDayDate(long time) {
		return getDate(time, "yyyyMMdd");
	}

	public static String getDate(long time, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return formater.format(new Date(time));
	}

	public static String getDateTimeString(Date date) {
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
	}

	public static String getDateTimeString(Date date, String format) {
		return (new SimpleDateFormat(format)).format(date);
	}

	public static boolean isTimeLarge(String startTime, String endTime) {
		try {
			SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			Date dt1 = e.parse(startTime, pos);
			Date dt2 = e.parse(endTime, pos1);
			long lDiff = dt2.getTime() - dt1.getTime();
			return lDiff > 0L;
		} catch (Exception arg8) {
			return false;
		}
	}

	public static long getTime(Date startTime, Date endTime) {
		return endTime.getTime() - startTime.getTime();
	}

	public static String getTimeDiff(String startTime, String endTime) {
		try {
			String e = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			Date dt1 = formatter.parse(startTime, pos);
			Date dt2 = formatter.parse(endTime, pos1);
			long lDiff = dt2.getTime() - dt1.getTime();
			int days = (int) (lDiff / 86400000L);
			if (days > 0) {
				lDiff -= (long) (days * 1000 * 60 * 60 * 24);
				e = e + days + "天";
			}

			int hours = (int) (lDiff / 3600000L);
			if (hours > 0) {
				lDiff -= (long) (hours * 1000 * 60 * 60);
			}

			e = e + hours + "小时";
			int minute = (int) (lDiff / 60000L);
			e = e + minute + "分钟";
			return e;
		} catch (Exception arg12) {
			arg12.printStackTrace();
			return "-1";
		}
	}

	public static String getTime(Long millseconds) {
		String time = "";
		if (millseconds == null) {
			return "";
		} else {
			int days = (int) millseconds.longValue() / 1000 / 60 / 60 / 24;
			if (days > 0) {
				time = time + days + "天";
			}

			long hourMillseconds = millseconds.longValue() - (long) (days * 1000 * 60 * 60 * 24);
			int hours = (int) hourMillseconds / 1000 / 60 / 60;
			if (hours > 0) {
				time = time + hours + "小时";
			}

			long minuteMillseconds = millseconds.longValue() - (long) (days * 1000 * 60 * 60 * 24)
					- (long) (hours * 1000 * 60 * 60);
			int minutes = (int) minuteMillseconds / 1000 / 60;
			if (minutes > 0) {
				time = time + minutes + "分钟";
			}

			return time;
		}
	}

	public static String getDateString(Date date) {
		return date != null ? (new SimpleDateFormat("yyyy-MM-dd")).format(date) : "";
	}

	public static String getCurrentDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		return formatter.format(date);
	}

	public static String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return formatter.format(date);
	}

	public static String getDateString(long millseconds) {
		return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date(millseconds));
	}

	public static String getDateString(String formater) {
		return (new SimpleDateFormat(formater)).format(new Date());
	}

	public static long getMillsByToday() {
		String str = getDateString("yyyy-MM-dd");
		str = String.valueOf(getMillsByDateString(str));
		return Long.parseLong(str.substring(0, str.length() - 3) + "000");
	}

	public static long getNextDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(5, days);
		String str = String.valueOf(cal.getTimeInMillis());
		return Long.parseLong(str.substring(0, str.length() - 3) + "000");
	}

	public static Date getNextDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(5, days);
		return cal.getTime();
	}

	public static long getMillsByDateString(String strDate) {
		Calendar cal = Calendar.getInstance();
		if (strDate != null && strDate.trim().length() > 9) {
			strDate = strDate.trim();

			try {
				int e = Integer.parseInt(strDate.substring(0, 4));
				int month = Integer.parseInt(strDate.substring(5, 7)) - 1;
				int date = Integer.parseInt(strDate.substring(8, 10));
				cal.set(e, month, date, 0, 0, 0);
				String str = String.valueOf(cal.getTimeInMillis());
				return Long.parseLong(str.substring(0, str.length() - 3) + "000");
			} catch (Exception arg5) {
				;
			}
		}

		return 0L;
	}

	public static long getMillsByDateTimeString(String strDateTime) {
		Calendar cal = Calendar.getInstance();
		if (strDateTime != null && strDateTime.trim().length() > 18) {
			strDateTime = strDateTime.trim();

			try {
				int e = Integer.parseInt(strDateTime.substring(0, 4));
				int month = Integer.parseInt(strDateTime.substring(5, 7)) - 1;
				int date = Integer.parseInt(strDateTime.substring(8, 10));
				int hour = Integer.parseInt(strDateTime.substring(11, 13));
				int minute = Integer.parseInt(strDateTime.substring(14, 16));
				int second = Integer.parseInt(strDateTime.substring(17, 19));
				cal.set(e, month, date, hour, minute, second);
				return cal.getTimeInMillis();
			} catch (Exception arg7) {
				;
			}
		}

		return 0L;
	}

	public static String getFormatString(long millseconds, String format) {
		if (format == null || format.trim().length() == 0) {
			format = "yyyy-MM-dd";
		}

		format = format.trim();
		return (new SimpleDateFormat(format)).format(new Date(millseconds));
	}

	public static long getCurrentTimeMillis() {
		Calendar c = Calendar.getInstance();
		return c.getTimeInMillis();
	}

	public static String getTimeByMills(long mills) throws Exception {
		try {
			if (mills == 0L) {
				return "-";
			} else {
				Date e = null;
				Calendar ca = Calendar.getInstance();
				ca.setTimeInMillis(mills);
				e = ca.getTime();
				SimpleDateFormat myformat;
				if (ca.get(11) == 0 && ca.get(12) == 0 && ca.get(13) == 0) {
					myformat = new SimpleDateFormat("yyyy-MM-dd");
				} else {
					myformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				}

				return myformat.format(e);
			}
		} catch (Exception arg4) {
			return "-";
		}
	}

	public static String formatDate(long mills) throws Exception {
		try {
			if (mills == 0L) {
				return "-";
			} else {
				Date e = null;
				Calendar ca = Calendar.getInstance();
				ca.setTimeInMillis(mills);
				e = ca.getTime();
				SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd");
				return myformat.format(e);
			}
		} catch (Exception arg4) {
			return "-";
		}
	}

	public static String formatTime(long mills) throws Exception {
		try {
			if (mills == 0L) {
				return "-";
			} else {
				Date e = null;
				Calendar ca = Calendar.getInstance();
				ca.setTimeInMillis(mills);
				e = ca.getTime();
				SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return myformat.format(e);
			}
		} catch (Exception arg4) {
			return "-";
		}
	}

	public static long getMillsByTime(String strTime) throws Exception {
		try {
			if (strTime.length() != 19 && strTime.length() != 10) {
				throw new Exception("the time string is wrong.");
			} else {
				int e = Integer.parseInt(strTime.substring(0, 4));
				int month = Integer.parseInt(strTime.substring(5, 7)) - 1;
				int day = Integer.parseInt(strTime.substring(8, 10));
				if (e >= 1000 && e <= 3000) {
					if (month >= 0 && month <= 12) {
						if (day >= 1 && day <= 31) {
							Calendar ca = Calendar.getInstance();
							if (strTime.length() == 19) {
								int hour = Integer.parseInt(strTime.substring(11, 13));
								int minute = Integer.parseInt(strTime.substring(14, 16));
								int second = Integer.parseInt(strTime.substring(17, 19));
								if (hour < 0 || hour > 24) {
									throw new Exception("the hour is wrong.");
								}

								if (minute < 0 || minute > 60) {
									throw new Exception("the minute is wrong.");
								}

								if (second < 0 || second > 60) {
									throw new Exception("the second is wrong.");
								}

								ca.set(e, month, day, hour, minute, second);
							} else if (strTime.length() == 10) {
								ca.set(e, month, day, 0, 0, 0);
							}

							return ca.getTimeInMillis();
						} else {
							throw new Exception("the day is wrong");
						}
					} else {
						throw new Exception("the month is wrong.");
					}
				} else {
					throw new Exception("the year is wrong.");
				}
			}
		} catch (Exception arg7) {
			arg7.printStackTrace();
			return -1L;
		}
	}

	public static long getNextTime(int timeUnit, int interval, long timeMill) {
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(timeMill);
		switch (timeUnit) {
			case 0 :
				ca.add(13, interval);
				break;
			case 1 :
				ca.add(12, interval);
				break;
			case 2 :
				ca.add(10, interval);
				break;
			case 3 :
				ca.add(5, interval);
				break;
			case 4 :
				ca.add(2, interval);
				break;
			default :
				return 0L;
		}

		return ca.getTimeInMillis();
	}

	public static Date getDateTimeByTimeString(String timeString) {
		Date date = new Date();

		try {
			date = DateFormatUtil.parse(timeString, "yy-MM-dd HH:mm:ss");
		} catch (ParseException arg2) {
			arg2.printStackTrace();
		}

		return date;
	}

	public static Date getDateByDateString(String timeString) {
		Date date = new Date();

		try {
			date = DateFormatUtil.parse(timeString, "yy-MM-dd");
		} catch (ParseException arg2) {
			arg2.printStackTrace();
		}

		return date;
	}

	public static Date getDate(int year, int month, int date) {
		return getDate(year, month, date, 0, 0, 0);
	}

	public static Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date, hourOfDay, minute, second);
		return cal.getTime();
	}

	public static int getSecondDiff(Date endTime, Date startTime) {
		long start = startTime.getTime();
		long end = endTime.getTime();
		return (int) ((end - start) / 1000L);
	}

	public static int getDaysOfMonth(int year, int mon) {
		Calendar cal = Calendar.getInstance();
		cal.set(1, year);
		cal.set(2, mon - 1);
		return cal.getActualMaximum(5);
	}

	public static int getWeekDayOfMonth(int year, int mon) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, mon - 1, 1);
		return cal.get(7);
	}

	public static String getDurationTime(Date time) {
		if (BeanUtils.isEmpty(time)) {
			return "";
		} else {
			Long millseconds = Long.valueOf(getTime(time, new Date()));
			return getTime(millseconds);
		}
	}
}