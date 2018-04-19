package com.hotent.core.soap.type;

import com.hotent.core.soap.type.BaseSoapType;
import javax.xml.soap.SOAPElement;

public class StringSoapType extends BaseSoapType {
	public Class<?>[] getBeanTypes() {
		return new Class[]{String.class};
	}

	public String[] getSoapTypes() {
		return new String[]{"string"};
	}

	void setCurrentValue(SOAPElement element, Object obj, Class<?> klass) {
		element.setTextContent(obj.toString());
	}

	Object convertCurrent(Class<?> klass, SOAPElement element) {
		return element.getTextContent();
	}
}