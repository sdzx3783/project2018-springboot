package com.hotent.core.soap.type;

import com.hotent.core.soap.type.BaseSoapType;
import java.math.BigDecimal;
import javax.xml.soap.SOAPElement;

public class NumberSoapType extends BaseSoapType {
	public Class<?>[] getBeanTypes() {
		return new Class[]{Integer.TYPE, Integer.class, Long.TYPE, Long.class, Short.TYPE, Short.class, Double.TYPE,
				Double.class, Float.TYPE, Float.class, BigDecimal.class};
	}

	public String[] getSoapTypes() {
		return new String[]{"int", "long", "short", "integer"};
	}

	void setCurrentValue(SOAPElement element, Object obj, Class<?> klass) {
		element.setTextContent(obj.toString());
	}

	Object convertCurrent(Class<?> klass, SOAPElement element) {
		String value = element.getTextContent();
		return klass == Integer.class
				? Integer.valueOf(value)
				: (klass == Long.class
						? Long.valueOf(value)
						: (klass == Short.class
								? Short.valueOf(value)
								: (klass == Double.class
										? Double.valueOf(value)
										: (klass == Float.class
												? Float.valueOf(value)
												: (klass == BigDecimal.class
														? BigDecimal.valueOf(Double.valueOf(value).doubleValue())
														: value)))));
	}
}