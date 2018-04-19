package com.hotent.core.util;

import com.hotent.core.util.BeanUtils;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MapUtil {
	public static String getString(Map<String, Object> map, String field) {
		if (BeanUtils.isEmpty(map)) {
			return "";
		} else {
			field = field.toLowerCase();
			Set set = map.keySet();
			Iterator it = set.iterator();
			Hashtable ht = new Hashtable();

			while (it.hasNext()) {
				String obj = (String) it.next();
				ht.put(obj.toLowerCase(), obj);
			}

			field = (String) ht.get(field);
			Object obj1 = map.get(field);
			return obj1 != null ? obj1.toString().trim() : "";
		}
	}

	public static long getLong(Map<String, Object> map, String field) {
		String value = getString(map, field);
		return value.equals("") ? -1L : Long.parseLong(value);
	}

	public static int getInt(Map<String, Object> map, String field) {
		String value = getString(map, field);
		return value.equals("") ? -1 : Integer.parseInt(value);
	}

	public static float getFloat(Map<String, Object> map, String field) {
		String value = getString(map, field);
		return value.equals("") ? -1.0F : Float.parseFloat(value);
	}

	public static double getDouble(Map<String, Object> map, String field) {
		String value = getString(map, field);
		return value.equals("") ? -1.0D : Double.parseDouble(value);
	}

	public static Object get(Map<String, Object> map, String field) {
		field = field.toLowerCase();
		Set set = map.keySet();
		Iterator it = set.iterator();
		Hashtable ht = new Hashtable();

		while (it.hasNext()) {
			String obj = (String) it.next();
			ht.put(obj.toLowerCase(), obj);
		}

		field = (String) ht.get(field);
		Object obj1 = map.get(field);
		System.out.println(obj1.getClass());
		return obj1;
	}

	public static boolean containsKey(Map<String, Object> map, String field) {
		return get(map, field) != null;
	}

	public static void put(Map<String, Object> map, String field, Object val) {
		Iterator i$ = map.keySet().iterator();

		while (i$.hasNext()) {
			String key = (String) i$.next();
			if (key.equalsIgnoreCase(field)) {
				map.put(key, val);
			}
		}

	}

	public static Object transMap2Bean(Map<String, Object> map) {
		Object obj = new Object();
		if (map == null) {
			return null;
		} else {
			try {
				BeanUtils.populate(obj, map);
			} catch (Exception arg2) {
				System.out.println("transMap2Bean2 Error " + arg2);
			}

			return obj;
		}
	}

	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		} else {
			LinkedHashMap map = new LinkedHashMap();

			try {
				BeanInfo e = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = e.getPropertyDescriptors();
				PropertyDescriptor[] arr$ = propertyDescriptors;
				int len$ = propertyDescriptors.length;

				for (int i$ = 0; i$ < len$; ++i$) {
					PropertyDescriptor property = arr$[i$];
					String key = property.getName();
					if (!key.equals("class")) {
						Method getter = property.getReadMethod();
						Object value = getter.invoke(obj, new Object[0]);
						map.put(key, value);
					}
				}
			} catch (Exception arg10) {
				System.out.println("transBean2Map Error " + arg10);
			}

			return map;
		}
	}
}