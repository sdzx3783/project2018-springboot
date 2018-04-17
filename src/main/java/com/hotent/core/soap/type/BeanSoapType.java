package com.hotent.core.soap.type;

import com.hotent.core.soap.type.BaseSoapType;
import com.hotent.core.soap.type.SoapTypes;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import java.lang.reflect.Field;
import java.util.Iterator;
import javax.xml.soap.SOAPElement;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

public class BeanSoapType extends BaseSoapType {
	private static Logger logger = Logger.getLogger(BaseSoapType.class);

	public Class<?>[] getBeanTypes() {
		return new Class[]{Object.class};
	}

	public String[] getSoapTypes() {
		return new String[]{"anyType"};
	}

	void setCurrentValue(SOAPElement element, Object obj, Class<?> klass) {
		Class myKlass = obj.getClass();
		if (myKlass != null) {
			klass = myKlass;
		}

		Field[] it = klass.getDeclaredFields();
		int child = it.length;

		for (int content = 0; content < child; ++content) {
			Field field = it[content];
			field.setAccessible(true);
			Class fieldType = field.getType();
			String fieldName = field.getName();
			fieldName = fieldName.replace("$cglib_prop_", "");
			NodeList fieldNodeList = element.getElementsByTagName(fieldName);
			if (fieldNodeList != null && fieldNodeList.getLength() >= 1) {
				try {
					Object e = field.get(obj);
					SOAPElement targetElement = (SOAPElement) fieldNodeList.item(0);
					if (BeanUtils.isEmpty(e)) {
						boolean hasChild = targetElement.hasChildNodes();
						if (!hasChild) {
							targetElement.detachNode();
						}
					} else {
						SoapTypes.getTypeByBean(fieldType).setValue(targetElement, e, fieldType);
					}
				} catch (Exception arg14) {
					logger.warn("字段[" + fieldName + "]设置失败.", arg14);
				}
			}
		}

		Iterator arg15 = element.getChildElements();

		while (arg15.hasNext()) {
			SOAPElement arg16 = (SOAPElement) arg15.next();
			if (!arg16.hasChildNodes()) {
				String arg17 = arg16.getTextContent();
				if (StringUtil.isEmpty(arg17)) {
					arg16.detachNode();
				}
			}
		}

	}

	Object convertCurrent(Class<?> klass, SOAPElement element) {
		Object bean;
		try {
			bean = klass.newInstance();
		} catch (Exception arg12) {
			logger.error("类别[" + klass + "]无法实例化.", arg12);
			return null;
		}

		Field[] arr$ = klass.getDeclaredFields();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			Field field = arr$[i$];
			field.setAccessible(true);
			Class fieldType = field.getType();
			String fieldName = field.getName();
			NodeList fieldNodeList = element.getElementsByTagName(fieldName);
			if (fieldNodeList != null && fieldNodeList.getLength() >= 1) {
				try {
					Object e = SoapTypes.getTypeByBean(fieldType).convertToBean(fieldType, new SOAPElement[]{element});
					field.set(bean, e);
				} catch (Exception arg11) {
					logger.warn("字段[" + fieldName + "]设置失败.", arg11);
				}
			}
		}

		return bean;
	}
}