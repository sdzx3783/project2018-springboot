package com.hotent.core.util;

import com.hotent.core.util.AppUtil;
import com.hotent.core.util.StringUtil;
import java.util.Properties;

public class AppConfigUtil {
	public static String get(String propertyKey) {
		Properties properties = (Properties) AppUtil.getBean("configproperties");
		return properties.getProperty(propertyKey);
	}

	public static int getInt(String propertyKey) {
		String val = get(propertyKey);
		return Integer.parseInt(val);
	}

	public static int getInt(String propertyKey, int defaultValue) {
		String val = get(propertyKey);
		return StringUtil.isEmpty(val) ? defaultValue : Integer.parseInt(val);
	}

	public static String get(String propertyKey, String defultValue) {
		String val = get(propertyKey);
		return StringUtil.isEmpty(val) ? defultValue : val;
	}

	public static boolean getBoolean(String propertyKey) {
		String val = get(propertyKey);
		return val.equalsIgnoreCase("true");
	}
}