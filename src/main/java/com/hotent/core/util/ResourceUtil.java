package com.hotent.core.util;

import com.hotent.core.util.AppUtil;
import java.util.Locale;
import org.springframework.context.MessageSource;

public class ResourceUtil {
	public static String getText(String msgKey, Object arg, Locale local) {
		MessageSource messageSource = (MessageSource) AppUtil.getBean(MessageSource.class);
		return messageSource.getMessage(msgKey, new Object[]{arg}, local);
	}

	public static String getText(String msgKey, Object[] args, Locale local) {
		MessageSource messageSource = (MessageSource) AppUtil.getBean(MessageSource.class);
		return messageSource.getMessage(msgKey, args, local);
	}
}