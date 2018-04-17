package com.hotent.core.bpmn20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class ContextFactory {
	private static Map<String, JAXBContext> contexts = Collections.synchronizedMap(new LinkedHashMap());

	public static JAXBContext newInstance(Class... classes) throws JAXBException {
		JAXBContext jAXBContext = null;
		ArrayList classeNames = new ArrayList();
		String newKey = "";
		Class[] i$ = classes;
		int key = classes.length;

		for (int keyAry = 0; keyAry < key; ++keyAry) {
			Class clss = i$[keyAry];
			newKey = newKey + clss.getName() + ",";
			classeNames.add(clss.getName());
		}

		newKey = newKey.substring(0, newKey.length() - 1);
		Iterator arg7 = contexts.keySet().iterator();

		while (arg7.hasNext()) {
			String arg8 = (String) arg7.next();
			String[] arg9 = arg8.split(",");
			List arg10 = Arrays.asList(arg9);
			if (arg10.equals(arg10)) {
				jAXBContext = (JAXBContext) contexts.get(arg8);
				break;
			}
		}

		if (jAXBContext == null) {
			jAXBContext = JAXBContext.newInstance(classes);
			contexts.put(newKey, jAXBContext);
		}

		return jAXBContext;
	}
}