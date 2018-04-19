package com.hotent.core.json;

import com.hotent.core.util.DateUtil;
import java.beans.PropertyEditorSupport;
import java.util.Date;

public class SmartDateEditor extends PropertyEditorSupport {
	public void setAsText(String text) throws IllegalArgumentException {
		if (text != null && text.length() != 0) {
			try {
				this.setValue(DateUtil.parseDate(text));
			} catch (Exception arg2) {
				throw new IllegalArgumentException("转换日期失败: " + arg2.getMessage(), arg2);
			}
		} else {
			this.setValue((Object) null);
		}

	}

	public String getAsText() {
		Object obj = this.getValue();
		if (obj == null) {
			return "";
		} else {
			Date value = (Date) obj;
			String dateStr = null;

			try {
				dateStr = DateUtil.formatEnDate(value);
				if (dateStr.endsWith(" 00:00:00")) {
					dateStr = dateStr.substring(0, 10);
				} else if (dateStr.endsWith(":00")) {
					dateStr = dateStr.substring(0, 16);
				}

				return dateStr;
			} catch (Exception arg4) {
				throw new IllegalArgumentException("转换日期失败: " + arg4.getMessage(), arg4);
			}
		}
	}
}