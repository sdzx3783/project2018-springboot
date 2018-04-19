package com.hotent.core.soap.type;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

public interface SoapType {
	Object convertToBean(Class<?> arg0, SOAPElement... arg1) throws SOAPException;

	Object convertToBean(SOAPElement... arg0) throws SOAPException;

	void setValue(SOAPElement arg0, Object arg1, Class<?> arg2) throws SOAPException;

	void setValue(SOAPElement arg0, Object arg1) throws SOAPException;

	Class<?>[] getBeanTypes();

	String[] getSoapTypes();
}