package com.hotent.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	public static String getExceptionMessage(Exception e) {
		if (e == null) {
			return "";
		} else {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			return str;
		}
	}
}