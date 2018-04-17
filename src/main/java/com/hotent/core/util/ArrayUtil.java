package com.hotent.core.util;

import org.apache.commons.lang.ArrayUtils;

public class ArrayUtil {
	public static Long[] convertArray(String[] aryStr) {
		if (ArrayUtils.isEmpty(aryStr)) {
			return ArrayUtils.EMPTY_LONG_OBJECT_ARRAY;
		} else {
			Long[] aryLong = new Long[aryStr.length];

			for (int i = 0; i < aryStr.length; ++i) {
				aryLong[i] = Long.valueOf(Long.parseLong(aryStr[i]));
			}

			return aryLong;
		}
	}

	public static String[] convertArray(Long[] aryLong) {
		if (ArrayUtils.isEmpty(aryLong)) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		} else {
			String[] aryStr = new String[aryLong.length];

			for (int i = 0; i < aryStr.length; ++i) {
				aryStr[i] = String.valueOf(aryStr[i]);
			}

			return aryStr;
		}
	}

	public static String contact(String[] aryStr, String separator) {
		if (aryStr.length == 1) {
			return aryStr[0];
		} else {
			String out = "";

			for (int i = 0; i < aryStr.length; ++i) {
				if (i == 0) {
					out = out + aryStr[i];
				} else {
					out = out + separator + aryStr[i];
				}
			}

			return out;
		}
	}

	public static String addQuote(String val) {
		String[] aryVal = val.split(",");
		if (aryVal.length == 1) {
			return "\'" + val + "\'";
		} else {
			String tmp = "";

			for (int i = 0; i < aryVal.length; ++i) {
				if (i == 0) {
					tmp = tmp + "\'" + aryVal[i] + "\'";
				} else {
					tmp = tmp + ",\'" + aryVal[i] + "\'";
				}
			}

			return tmp;
		}
	}
}