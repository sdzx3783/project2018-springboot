package com.hotent.core.log;

import java.util.HashMap;
import java.util.Map;

public class SysAuditThreadLocalHolder {
	public static final short RESULT_SUCCESS = 0;
	public static final short RESULT_FAIL = 0;
	private static ThreadLocal<String> detailLocal = new ThreadLocal();
	private static ThreadLocal<Short> resultLocal = new ThreadLocal();
	private static ThreadLocal<Map<String, Object>> paramertersLocal = new ThreadLocal();
	private static ThreadLocal<Boolean> shouldLogLocal = new ThreadLocal();

	public static String getDetail() {
		return (String) detailLocal.get();
	}

	public static void setDatail(String detail) {
		detailLocal.set(detail);
	}

	public static void clearDetail() {
		detailLocal.remove();
	}

	public static Short getResult() {
		return (Short) resultLocal.get();
	}

	public static void setResult(Short result) {
		resultLocal.set(result);
	}

	public static void clearResult() {
		resultLocal.remove();
	}

	public static Boolean getShouldLog() {
		return (Boolean) shouldLogLocal.get();
	}

	public static void setShouldLog(Boolean shouldLog) {
		shouldLogLocal.set(shouldLog);
	}

	public static void clearShouldLog() {
		shouldLogLocal.remove();
	}

	public static Map<String, Object> getParamerters() {
		if (paramertersLocal.get() == null) {
			paramertersLocal.set(new HashMap());
		}

		return (Map) paramertersLocal.get();
	}

	public static Object getParamerter(String key) {
		if (paramertersLocal.get() == null) {
			paramertersLocal.set(new HashMap());
		}

		return ((Map) paramertersLocal.get()).get(key);
	}

	public static void putParamerter(String key, Object value) {
		if (paramertersLocal.get() == null) {
			paramertersLocal.set(new HashMap());
		}

		((Map) paramertersLocal.get()).put(key, value);
	}

	public static void clearParameters() {
		paramertersLocal.remove();
	}
}