package com.hotent.core.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.util.JSONUtils;

public class DefaultDefaultValueProcessor implements DefaultValueProcessor {
	public Object getDefaultValue(Class type) {
		return JSONUtils.isArray(type)
				? new JSONArray()
				: (JSONUtils.isNumber(type)
						? (JSONUtils.isDouble(type) ? new Double(0.0D) : new Integer(0))
						: (JSONUtils.isBoolean(type)
								? Boolean.FALSE
								: (JSONUtils.isString(type) ? "" : JSONNull.getInstance())));
	}
}