package com.hotent.core.util.jsonobject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.jsonobject.support.BooleanSerializer;
import com.hotent.core.util.jsonobject.support.DateSerializer;
import com.hotent.core.util.jsonobject.support.JsonObjectSerializer;
import com.hotent.core.util.jsonobject.support.SuperclassExclusionStrategy;
import com.hotent.core.web.util.RequestUtil;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class JSONObjectUtil {
	public static <C> C toBean(JSONObject jsonObject, Class<C> cls) {
		return toBean(jsonObject.toString(), cls);
	}

	public static <C> C toBean(String jsonStr, Class<C> cls) {
		try {
			GsonBuilder e = new GsonBuilder();
			e.registerTypeAdapter(String.class, new JsonObjectSerializer())
					.registerTypeAdapter(Date.class, new DateSerializer())
					.registerTypeAdapter(Boolean.class, new BooleanSerializer())
					.addDeserializationExclusionStrategy(new SuperclassExclusionStrategy())
					.addSerializationExclusionStrategy(new SuperclassExclusionStrategy());
			Gson gson = e.create();
			Object o = gson.fromJson(jsonStr, cls);
			return (C) o;
		} catch (Exception arg4) {
			return null;
		}
	}

	public static <C> C toBean(JSONObject jsonObject, Class<C> cls, Map<String, Class<?>> map) {
		HashMap jaMap = new HashMap();
		Iterator main = map.keySet().iterator();

		while (main.hasNext()) {
			String e = (String) main.next();
			JSONArray key = jsonObject.getJSONArray(e);
			jaMap.put(e, key);
			jsonObject.remove(e);
		}

		Object main1 = toBean(jsonObject, cls);

		try {
			Iterator e1 = map.keySet().iterator();

			while (e1.hasNext()) {
				String key1 = (String) e1.next();
				Class c = (Class) map.get(key1);
				JSONArray ja = (JSONArray) jaMap.get(key1);
				List subList = toBeanList(ja, c);
				Method method = cls.getMethod("set" + StringUtil.makeFirstLetterUpperCase(key1),
						new Class[]{List.class});
				method.invoke(main1, new Object[]{subList});
			}

			return (C) main1;
		} catch (Exception arg10) {
			return null;
		}
	}

	public static <C> C toBean(String json, Class<C> cls, Map<String, Class<?>> map) {
		return toBean(JSONObject.fromObject(json), cls, map);
	}

	public static <C> List<C> toBeanList(String arrayStr, Class<C> cls) {
		return toBeanList(JSONArray.fromObject(arrayStr), cls);
	}

	public static <C> List<C> toBeanList(JSONArray jsonArray, Class<C> cls) {
		ArrayList list = new ArrayList();

		for (int i = 0; i < jsonArray.size(); ++i) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Object o = toBean(jsonObject, cls);
			list.add(o);
		}

		return list;
	}

	public static Map<String, Object> getQueryMap(JSONObject jsonObject) {
		HashMap map = new HashMap();
		Iterator i$ = jsonObject.keySet().iterator();

		while (i$.hasNext()) {
			Object obj = i$.next();
			String key = obj.toString();
			String[] values = new String[]{jsonObject.getString(key)};
			String val;
			if (key.equals("sortField") || key.equals("orderSeq") || key.equals("sortColumns")) {
				val = values[0].trim();
				if (StringUtil.isNotEmpty(val)) {
					map.put(key, values[0].trim());
				}
			}

			if (values.length > 0 && StringUtils.isNotEmpty(values[0])) {
				if (key.startsWith("Q_")) {
					RequestUtil.addParameter(key, values, map);
				} else if (values.length == 1) {
					val = values[0].trim();
					if (StringUtil.isNotEmpty(val)) {
						map.put(key, values[0].trim());
					}
				} else {
					map.put(key, values);
				}
			}
		}

		return map;
	}

	public static void main(String[] args) {
	}
}