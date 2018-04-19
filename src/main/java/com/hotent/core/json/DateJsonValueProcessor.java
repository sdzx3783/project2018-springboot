package com.hotent.core.json;

import com.hotent.core.util.DateFormatUtil;
import java.util.Date;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor {
	private String format = "yyyy-MM-dd HH:mm:ss";

	public DateJsonValueProcessor(String format) {
		this.format = format;
	}

	public DateJsonValueProcessor() {
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return this.process(value);
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return this.process(value);
	}

	private Object process(Object value) {
		if (value == null) {
			return "";
		} else {
			if (value instanceof Date) {
				try {
					return DateFormatUtil.format((Date) value, this.format);
				} catch (Exception arg2) {
					;
				}
			}

			return value.toString();
		}
	}
}