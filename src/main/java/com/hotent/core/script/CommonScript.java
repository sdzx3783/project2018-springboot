package com.hotent.core.script;

import com.hotent.core.engine.IScript;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.DateFormatUtil;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class CommonScript implements IScript {
	public boolean equalsIgnoreCase(String str1, String str2) {
		return StringUtils.equalsIgnoreCase(str1, str2);
	}

	public boolean equals(String str1, String str2) {
		return StringUtils.equals(str1, str2);
	}

	public short parseShort(String str) {
		return this.parseShort(str, (short)0);
	}

	public short parseShort(String str, short defaultValue) {
		return StringUtils.isEmpty(str) ? defaultValue : Short.parseShort(str);
	}

	public int parseInt(String str) {
		return this.parseInt(str, 0);
	}

	public int parseInt(String str, int defaultValue) {
		return StringUtils.isEmpty(str) ? defaultValue : Integer.parseInt(str);
	}

	public long parseLong(String str) {
		return this.parseLong(str, 0L);
	}

	public long parseLong(String str, long defaultValue) {
		return StringUtils.isEmpty(str) ? defaultValue : Long.parseLong(str);
	}

	public float parseFloat(String str) {
		return this.parseFloat(str, 0.0F);
	}

	public float parseFloat(String str, float defaultValue) {
		return StringUtils.isEmpty(str) ? defaultValue : Float.parseFloat(str);
	}

	public double parseDouble(String str) {
		return this.parseDouble(str, 0.0D);
	}

	public double parseDouble(String str, double defaultValue) {
		return StringUtils.isEmpty(str) ? defaultValue : Double.parseDouble(str);
	}

	public boolean parseBoolean(String str) {
		return this.parseBoolean(str, Boolean.valueOf(false)).booleanValue();
	}

	public Boolean parseBoolean(String str, Boolean defaultValue) {
		return StringUtils.isEmpty(str)
				? defaultValue
				: (StringUtils.isNumeric(str)
						? Boolean.valueOf(Integer.parseInt(str) == 1)
						: Boolean.valueOf(Boolean.parseBoolean(str)));
	}

	public String parseString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public String parseString(Object obj, String style) {
		return obj == null ? "" : (obj instanceof Date ? DateFormatUtil.format((Date) obj, style) : obj.toString());
	}

	public Date parseDate(String str, String style) throws Exception {
		if (StringUtils.isEmpty(str)) {
			return null;
		} else {
			if (StringUtils.isEmpty(style)) {
				style = "yyyy-MM-dd HH:mm:ss";
			}

			return DateFormatUtil.parse(str, style);
		}
	}

	public Date parseDate(String str, Date defaultValue, String style) throws Exception {
		if (StringUtils.isEmpty(str)) {
			return defaultValue;
		} else {
			if (StringUtils.isEmpty(style)) {
				style = "yyyy-MM-dd HH:mm:ss";
			}

			return DateFormatUtil.parse(str, style);
		}
	}

	public int compareTo(Date date1, Date date2) {
		return date1 == null && date2 == null
				? 0
				: (date1 == null ? 10 : (date2 == null ? -10 : date1.compareTo(date2)));
	}

	public int compareTo(String str1, String str2) {
		return str1 == null && str2 == null ? 0 : (str1 == null ? 10 : (str2 == null ? -10 : str1.compareTo(str2)));
	}

	public boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	public boolean isEmpty(Object obj) {
		return BeanUtils.isEmpty(obj);
	}

	public static void main(String[] args) {
	}
}
