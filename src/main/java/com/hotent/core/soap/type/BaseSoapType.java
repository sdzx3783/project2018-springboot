package com.hotent.core.soap.type;

import com.hotent.core.soap.type.SoapType;
import java.util.ArrayList;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

abstract class BaseSoapType implements SoapType {
	public abstract Class<?>[] getBeanTypes();

	public abstract String[] getSoapTypes();

	private final Class<?> getDefaultClass() {
		Class[] klasses = this.getBeanTypes();
		return klasses != null && klasses.length != 0 ? klasses[0] : Object.class;
	}

	abstract Object convertCurrent(Class<?> arg0, SOAPElement arg1);

	public final Object convertToBean(Class<?> klass, SOAPElement... elements) throws SOAPException {
		if (elements != null && elements.length >= 1) {
			if (elements.length <= 1) {
				return this.convertCurrent(klass, elements[0]);
			} else {
				ArrayList list = new ArrayList();
				SOAPElement[] arr$ = elements;
				int len$ = elements.length;

				for (int i$ = 0; i$ < len$; ++i$) {
					SOAPElement element = arr$[i$];
					Object obj = this.convertCurrent(klass, element);
					list.add(obj);
				}

				return list;
			}
		} else {
			return null;
		}
	}

	abstract void setCurrentValue(SOAPElement arg0, Object arg1, Class<?> arg2);

	public final void setValue(SOAPElement element, Object obj, Class<?> klass) throws SOAPException {
		if (obj != null) {
			if (klass == null) {
				klass = this.getDefaultClass();
			}

			this.setCurrentValue(element, obj, klass);
		}
	}

	public final Object convertToBean(SOAPElement... elements) throws SOAPException {
		return this.convertToBean(this.getDefaultClass(), elements);
	}

	public final void setValue(SOAPElement element, Object obj) throws SOAPException {
		this.setValue(element, obj, this.getDefaultClass());
	}
}