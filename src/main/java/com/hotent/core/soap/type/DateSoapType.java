package com.hotent.core.soap.type;

import com.hotent.core.soap.type.BaseSoapType;
import java.util.Calendar;
import java.util.Date;
import javax.xml.soap.SOAPElement;
import org.apache.xmlbeans.impl.values.XmlDateTimeImpl;

public class DateSoapType extends BaseSoapType {
	public Class<?>[] getBeanTypes() {
		return new Class[]{Date.class, Calendar.class};
	}

	public String[] getSoapTypes() {
		return new String[]{"date", "dateTime"};
	}

	void setCurrentValue(SOAPElement element, Object obj, Class<?> klass) {
		XmlDateTimeImpl xmlDateTime = new XmlDateTimeImpl();
		if (obj instanceof Date) {
			xmlDateTime.setDateValue((Date) obj);
		} else if (obj instanceof Calendar) {
			xmlDateTime.setCalendarValue((Calendar) obj);
		}

		element.setTextContent(xmlDateTime.getStringValue());
	}

	Object convertCurrent(Class<?> klass, SOAPElement element) {
		XmlDateTimeImpl xmlDateTime = new XmlDateTimeImpl();
		xmlDateTime.setStringValue(element.getTextContent());
		return klass == Date.class
				? xmlDateTime.getDateValue()
				: (klass == Calendar.class ? xmlDateTime.getCalendarValue() : element.getTextContent());
	}
}