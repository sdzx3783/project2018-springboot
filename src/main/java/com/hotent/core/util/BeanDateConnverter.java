package com.hotent.core.util;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanDateConnverter implements Converter {
	private static final Logger logger = LoggerFactory.getLogger(BeanDateConnverter.class);
	public static final String[] ACCEPT_DATE_FORMATS = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};

	public Object convert(Class arg0, Object value) {
		logger.debug("conver " + value + " to date object");
		if (value == null) {
			return null;
		} else {
			String dateStr = value.toString();
			dateStr = dateStr.replace("T", " ");

			try {
				return DateUtils.parseDate(dateStr, ACCEPT_DATE_FORMATS);
			} catch (Exception arg4) {
				logger.debug("parse date error:" + arg4.getMessage());
				return null;
			}
		}
	}
}