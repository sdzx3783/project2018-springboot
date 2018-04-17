package com.hotent.core.util;

import com.hotent.core.util.DateFormatUtil;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	private static final Log logger = LogFactory.getLog(DateUtil.class);

	public static Calendar setStartDay(Calendar cal) {
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		return cal;
	}

	public static Calendar setEndDay(Calendar cal) {
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		return cal;
	}
	

	public static void copyYearMonthDay(Calendar destCal, Calendar sourceCal) {
		destCal.set(1, sourceCal.get(1));
		destCal.set(2, sourceCal.get(2));
		destCal.set(5, sourceCal.get(5));
	}

	public static String formatEnDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		return sdf.format(date).replaceAll("上午", "AM").replaceAll("下午", "PM");
	}

	public static Date parseDate(String dateString) {
		Date date = null;

		try {
			date = DateUtils.parseDate(dateString, new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm",
					"HH:mm:ss", "HH:mm", "yyyy-MM-dd HH:mm:ss.SSS"});
		} catch (Exception arg2) {
			logger.error("Pase the Date(" + dateString + ") occur errors:" + arg2.getMessage());
		}

		return date;
	}

	public static Date addDay(Date date, int day) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(5, day);
		return ca.getTime();
	}

	public static Date addOneDay(Date date) {
		return addDay(date, 1);
	}

	public static String addOneDay(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();

		try {
			Date tmpDate = format.parse(date);
			calendar.setTime(tmpDate);
			calendar.add(5, 1);
		} catch (ParseException arg3) {
			arg3.printStackTrace();
		}

		String tmpDate1 = format.format(calendar.getTime());
		return tmpDate1.substring(5, 7) + "/" + tmpDate1.substring(8, 10) + "/" + tmpDate1.substring(0, 4);
	}

	public static String addOneHour(String date) {
		String amPm = date.substring(20, 22);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		int hour = Integer.parseInt(date.substring(11, 13));

		try {
			if (amPm.equals("PM")) {
				hour += 12;
			}

			date = date.substring(0, 11) + (hour >= 10 ? Integer.valueOf(hour) : "0" + hour) + date.substring(13, 19);
			Date tmpDate = format.parse(date);
			calendar.setTime(tmpDate);
			calendar.add(11, 1);
		} catch (ParseException arg5) {
			arg5.printStackTrace();
		}

		String tmpDate1 = format.format(calendar.getTime());
		hour = Integer.parseInt(tmpDate1.substring(11, 13));
		amPm = hour >= 12 && hour != 0 ? "PM" : "AM";
		if (amPm.equals("PM")) {
			hour -= 12;
		}

		tmpDate1 = tmpDate1.substring(5, 7) + "/" + tmpDate1.substring(8, 10) + "/" + tmpDate1.substring(0, 4) + " "
				+ (hour >= 10 ? Integer.valueOf(hour) : "0" + hour) + tmpDate1.substring(13, tmpDate1.length()) + " "
				+ amPm;
		return tmpDate1;
	}

	public static String timeStrToDateStr(String timeStr) {
		String dateStr = timeStr.substring(24, 28) + "-";
		String mon = timeStr.substring(4, 7);
		if (mon.equals("Jan")) {
			dateStr = dateStr + "01";
		} else if (mon.equals("Feb")) {
			dateStr = dateStr + "02";
		} else if (mon.equals("Mar")) {
			dateStr = dateStr + "03";
		} else if (mon.equals("Apr")) {
			dateStr = dateStr + "04";
		} else if (mon.equals("May")) {
			dateStr = dateStr + "05";
		} else if (mon.equals("Jun")) {
			dateStr = dateStr + "06";
		} else if (mon.equals("Jul")) {
			dateStr = dateStr + "07";
		} else if (mon.equals("Aug")) {
			dateStr = dateStr + "08";
		} else if (mon.equals("Sep")) {
			dateStr = dateStr + "09";
		} else if (mon.equals("Oct")) {
			dateStr = dateStr + "10";
		} else if (mon.equals("Nov")) {
			dateStr = dateStr + "11";
		} else if (mon.equals("Dec")) {
			dateStr = dateStr + "12";
		}

		dateStr = dateStr + "-" + timeStr.substring(8, 10);
		return dateStr;
	}

	public static int getExtraDayOfWeek(String sDate) {
		try {
			SimpleDateFormat ex = new SimpleDateFormat("yyyy-MM-dd");
			Date date = ex.parse(sDate);
			String weekday = date.toString().substring(0, 3);
			return weekday.equals("Mon")
					? 1
					: (weekday.equals("Tue")
							? 2
							: (weekday.equals("Wed")
									? 3
									: (weekday.equals("Thu")
											? 4
											: (weekday.equals("Fri") ? 5 : (weekday.equals("Sat") ? 6 : 0)))));
		} catch (Exception arg3) {
			return 0;
		}
	}

	public static String getDateWeekDay(String sDate) {
		try {
			SimpleDateFormat ex = new SimpleDateFormat("yyyy-MM-dd");
			Date date = ex.parse(sDate);
			String weekday = date.toString().substring(0, 3);
			return weekday;
		} catch (Exception arg3) {
			return "";
		}
	}

	public static List<String> getUpDownFiveYear(Calendar cal) {
		ArrayList yearlist = new ArrayList();
		int curyear = cal.get(1);
		yearlist.add(String.valueOf(curyear - 2));
		yearlist.add(String.valueOf(curyear - 1));
		yearlist.add(String.valueOf(curyear));
		yearlist.add(String.valueOf(curyear + 1));
		yearlist.add(String.valueOf(curyear + 2));
		return yearlist;
	}

	public static List<String> getTwelveMonth() {
		ArrayList monthlist = new ArrayList();

		for (int idx = 1; idx <= 12; ++idx) {
			monthlist.add(String.valueOf(idx));
		}

		return monthlist;
	}

	public static String[] getDaysBetweenDate(Date startTime, Date endTime) {
		String[] dateArr = null;

		try {
			Integer e = Integer.valueOf(daysBetween(startTime, endTime));
			dateArr = new String[e.intValue() + 1];

			for (int i = 0; i < dateArr.length; ++i) {
				if (i == 0) {
					dateArr[i] = DateFormatUtil.formatDate(startTime);
				} else {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startTime);
					calendar.add(5, i);
					dateArr[i] = DateFormatUtil.formatDate(calendar.getTime());
				}
			}
		} catch (Exception arg5) {
			arg5.printStackTrace();
		}

		return dateArr;
	}

	public static String[] getDaysBetweenDate(String startTime, String endTime) {
		String[] dateArr = null;

		try {
			String e = timeStrToDateStr(startTime);
			String etime = timeStrToDateStr(endTime);
			Date date1 = (new SimpleDateFormat("yyyy-MM-dd")).parse(e);
			Date date2 = (new SimpleDateFormat("yyyy-MM-dd")).parse(etime);
			long day = (date1.getTime() - date2.getTime()) / 86400000L > 0L
					? (date1.getTime() - date2.getTime()) / 86400000L
					: (date2.getTime() - date1.getTime()) / 86400000L;
			dateArr = new String[Integer.valueOf(String.valueOf(day + 1L)).intValue()];

			for (int idx = 0; idx < dateArr.length; ++idx) {
				if (idx == 0) {
					dateArr[idx] = e;
				} else {
					e = addOneDay(e);
					e = e.substring(6, 10) + "-" + e.substring(0, 2) + "-" + e.substring(3, 5);
					dateArr[idx] = e;
				}
			}
		} catch (ParseException arg9) {
			arg9.printStackTrace();
		}

		return dateArr;
	}

	public static Date getMonthStartDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfMonth = calendar.get(5);
		calendar.add(5, -(dayOfMonth - 1));
		Date firstDateOfMonth = calendar.getTime();
		return firstDateOfMonth;
	}

	public static Date getMonthEndDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfMonth = calendar.get(5);
		calendar.add(5, calendar.getActualMaximum(5) - dayOfMonth);
		Date lastDateOfMonth = calendar.getTime();
		return lastDateOfMonth;
	}

	public static Date getMonthStartDateTime(Date date) {
		Date firstDateOfMonth = getMonthStartDate(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstDateOfMonth);
		calendar = setStartDay(calendar);
		return calendar.getTime();
	}

	public static Date getMonthEndDateTime(Date date) {
		Date endDateOfMonth = getMonthEndDate(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDateOfMonth);
		calendar = setEndDay(calendar);
		return calendar.getTime();
	}

	public static Date getWeekStartDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(7, 2);
		return calendar.getTime();
	}

	public static Date getWeekEndDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(7, 1);
		calendar.add(3, 1);
		return calendar.getTime();
	}

	public static Date getWeekStartDateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(7, 2);
		calendar = setStartDay(calendar);
		return calendar.getTime();
	}

	public static Date getWeekEndDateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(7, 1);
		calendar.add(3, 1);
		calendar = setEndDay(calendar);
		return calendar.getTime();
	}

	public static String getWeekOfDate(Date date) {
		String[] weekDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(7) - 1;
		if (w < 0) {
			w = 0;
		}

		return weekDays[w];
	}

	public static int daysBetween(Date startDate, Date endDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long startTime = cal.getTimeInMillis();
		cal.setTime(endDate);
		long endTime = cal.getTimeInMillis();
		long between_days = (endTime - startTime) / 86400000L;
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static Date getNextTime(Date date, int field, int amount) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(field, amount);
		return ca.getTime();
	}

	public static Date addHour(Date date, int hour) {
		return getNextTime(date, 11, hour);
	}

	public static Date addHour(Date date, double time) {
		int hour = (int) time;
		double minute1 = (time - (double) hour) * 60.0D;
		int minute = (int) minute1;
		int second = (int) ((minute1 - (double) minute) * 60.0D);
		Date dateHour = getNextTime(date, 11, hour);
		Date dateMinute = getNextTime(dateHour, 12, minute);
		return getNextTime(dateMinute, 13, second);
	}

	public static Date addMinute(Date date, int minute) {
		return getNextTime(date, 12, minute);
	}

	public static boolean isBetween(Date startTime, Date endTime, Date date) {
		return date.after(startTime) && date.before(endTime);
	}

	public static double betweenMinute(Date startTime, Date endTime) {
		Long seconds = Long.valueOf((endTime.getTime() - startTime.getTime()) / 1000L);
		double s = (double) seconds.longValue() % 60.0D / 60.0D;
		DecimalFormat df = new DecimalFormat("0.00");
		double sec = Double.parseDouble(df.format(s));
		Long minute = Long.valueOf(seconds.longValue() / 60L);
		return (double) minute.longValue() + sec;
	}

	public static double betweenHour(Date startTime, Date endTime) {
		Long minutes = Long.valueOf((endTime.getTime() - startTime.getTime()) / 1000L / 60L);
		double m = (double) minutes.longValue() % 60.0D / 60.0D;
		DecimalFormat df = new DecimalFormat("0.00");
		double min = Double.parseDouble(df.format(m));
		Long hour = Long.valueOf(minutes.longValue() / 60L);
		return (double) hour.longValue() + min;
	}

	public static double betweenHour(Date startTime, Date endTime, Double restMinutes) {
		Long minutes = Long.valueOf((endTime.getTime() - startTime.getTime()) / 1000L / 60L);
		Long ms = Long.valueOf((long) ((double) minutes.longValue() - restMinutes.doubleValue()));
		double m = (double) ms.longValue() % 60.0D / 60.0D;
		DecimalFormat df = new DecimalFormat("0.00");
		double min = Double.parseDouble(df.format(m));
		Long hour = Long.valueOf(ms.longValue() / 60L);
		return (double) hour.longValue() + min;
	}

	public static Date getTime(Date date, Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar ca = Calendar.getInstance();
		ca.setTime(time);
		ca.set(cal.get(1), cal.get(2), cal.get(5));
		return ca.getTime();
	}

	public static void main(String[] args) {
		Date start = new Date();
		Date end = new Date(start.getTime() + 10000L);
		System.out.println(daysBetween(start, end));
	}
}