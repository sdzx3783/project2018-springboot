package com.hotent.core.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONUtil {
	public static boolean isEmpty(Object obj) {
		return obj == null
				? true
				: (obj instanceof JSONObject
						? ((JSONObject) obj).isNullObject()
						: (obj instanceof JSONArray
								? ((JSONArray) obj).isEmpty()
								: JSONNull.getInstance().equals(obj)));
	}

	public static boolean isNotEmpty(JSONObject obj) {
		return !isEmpty(obj);
	}

	public static String escapeSpecialChar(String str) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			switch (c) {
				case '\b' :
					sb.append("\\b");
					break;
				case '\t' :
					sb.append("\\t");
					break;
				case '\n' :
					sb.append("\\n");
					break;
				case '\f' :
					sb.append("\\f");
					break;
				case '\r' :
					sb.append("\\r");
					break;
				case '\"' :
					sb.append("\\\"");
					break;
				case '/' :
					sb.append("\\/");
					break;
				case '\\' :
					sb.append("\\\\");
					break;
				default :
					sb.append(c);
			}
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(JSONUtil.class);
		JSONArray jsonAry = new JSONArray();
		JSONObject json = new JSONObject();
		jsonAry.add(json);
		logger.info(String.valueOf(isEmpty(jsonAry)));
	}
}