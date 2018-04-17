package com.hotent.core.util;

import com.hotent.core.encrypt.EncryptUtil;
import java.util.Enumeration;
import java.util.Properties;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.ObjectUtils;

public class ExtpPropertyPlaceholderConfiger extends PropertyPlaceholderConfigurer {
	private String[] encryptNames = new String[]{"jdbc.password"};

	protected void convertProperties(Properties props) {
		Enumeration propertyNames = props.propertyNames();

		while (propertyNames.hasMoreElements()) {
			String propertyName = (String) propertyNames.nextElement();
			String propertyValue = props.getProperty(propertyName);
			String convertedValue = this.convertPropertyValue(propertyValue);
			if (this.isEncryptProp(propertyName)) {
				try {
					convertedValue = EncryptUtil.decrypt(convertedValue);
				} catch (Exception arg6) {
					arg6.printStackTrace();
				}
			}

			if (!ObjectUtils.nullSafeEquals(propertyValue, convertedValue)) {
				props.setProperty(propertyName, convertedValue);
			}
		}

	}

	private boolean isEncryptProp(String propertyName) {
		String[] arr$ = this.encryptNames;
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			String name = arr$[i$];
			if (name.equals(propertyName)) {
				return true;
			}
		}

		return false;
	}
}