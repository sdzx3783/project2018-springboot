package com.hotent.core.util;

import com.hotent.core.util.StringUtil;

public class SqlDateFormat {
	public static String convertDateFormat(String format, String value, String dbType) {
		return "oracle".equals(dbType)
				? convertToOracle(format, value)
				: ("mysql".equals(dbType)
						? convertToMySql(format, value)
						: ("mssql".equals(dbType) ? convertToMsSql(format, value) : value));
	}

	private static String convertToOracle(String format, String value) {
		if (StringUtil.isEmpty(format)) {
			format = "yyyy-MM-dd";
		}

		format = format.replace("HH", "hh24");
		format = format.replace("mm", "mi");
		String rtn = " TO_DATE(\'" + value + "\',\'" + format + "\')";
		return rtn;
	}

	private static String convertToMySql(String format, String value) {
		if (StringUtil.isEmail(format)) {
			format = "%Y-%m-%d";
		}

		format = format.replace("yyyy", "%Y");
		format = format.replace("MM", "%m");
		format = format.replace("dd", "%d");
		format = format.replace("HH", "%H");
		format = format.replace("hh", "%h");
		format = format.replace("mm", "%i");
		format = format.replace("ss", "%s");
		String rtn = " STR_TO_DATE(\'" + value + "\',\'" + format + "\')";
		return rtn;
	}

	private static String convertToMsSql(String format, String value) {
		String rtn = " cast(\'" + value + "\' as datetime) ";
		return rtn;
	}
}