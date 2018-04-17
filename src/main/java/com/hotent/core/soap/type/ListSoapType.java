package com.hotent.core.soap.type;

import com.hotent.core.soap.type.BaseSoapType;
import com.hotent.core.soap.type.SoapType;
import com.hotent.core.soap.type.SoapTypes;
import com.hotent.core.util.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.soap.SOAPElement;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ListSoapType extends BaseSoapType {
	private static Logger logger = Logger.getLogger(BaseSoapType.class);
	private Class<?> currentClass;

	public Class<?>[] getBeanTypes() {
		return new Class[]{List.class};
	}

	public String[] getSoapTypes() {
		return new String[]{"List"};
	}

	void setCurrentValue(SOAPElement element, Object obj, Class<?> klass) {
		String tagName = element.getTagName();

		try {
			List ex = (List) obj;
			if (ex == null) {
				return;
			}

			String elementName = element.getLocalName();
			SOAPElement parentElement = element.getParentElement();
			NodeList fieldNodeList = parentElement.getElementsByTagName(elementName);
			if (fieldNodeList == null) {
				return;
			}

			int nodeCount = fieldNodeList.getLength();
			if (nodeCount == ex.size()) {
				for (int tempElement = 0; tempElement < nodeCount; ++tempElement) {
					Object i$ = ex.get(tempElement);
					this.currentClass = i$.getClass();
					SOAPElement item = (SOAPElement) fieldNodeList.item(tempElement);
					SoapTypes.getTypeByBean(this.currentClass).setValue(item, i$, this.currentClass);
				}
			} else {
				Node arg14 = element.cloneNode(true);
				element.detachNode();
				Iterator arg15 = ex.iterator();

				while (arg15.hasNext()) {
					Object arg16 = arg15.next();
					this.currentClass = arg16.getClass();
					SOAPElement itemElement = (SOAPElement) arg14.cloneNode(true);
					parentElement.addChildElement(itemElement);
					SoapTypes.getTypeByBean(this.currentClass).setValue(itemElement, arg16, this.currentClass);
				}
			}
		} catch (Exception arg13) {
			logger.warn("字段[" + tagName + "]设置失败.", arg13);
		}

	}

	Object convertCurrent(Class<?> klass, SOAPElement element) {
		String tagName = element.getTagName();

		try {
			SOAPElement ex = element.getParentElement();
			NodeList nodeList = ex.getElementsByTagName(tagName);
			int size = nodeList.getLength();
			ArrayList list = new ArrayList();

			for (int i = 0; i < size; ++i) {
				SOAPElement node = (SOAPElement) nodeList.item(i);
				String text = node.getTextContent();
				if (StringUtil.isEmpty(text)) {
					SoapType convert = SoapTypes.getTypeByBean((Class) null);
					Class c = Object.class;

					try {
						c = Class.forName(node.getTagName());
					} catch (Exception arg13) {
						;
					}

					Object obj = convert.convertToBean(c, new SOAPElement[]{element});
					list.add(obj);
				} else {
					list.add(text);
				}
			}

			return list;
		} catch (Exception arg14) {
			logger.warn("字段[" + tagName + "]设置失败.", arg14);
			return null;
		}
	}
}