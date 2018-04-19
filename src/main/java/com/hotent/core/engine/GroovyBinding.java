package com.hotent.core.engine;

import groovy.lang.Binding;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GroovyBinding extends Binding {
	private Map variables;
	private static ThreadLocal<Map<String, Object>> localVars = new ThreadLocal();
	private static Map<String, Object> propertyMap = new HashMap();

	public GroovyBinding() {
	}

	public GroovyBinding(Map variables) {
		localVars.set(variables);
	}

	public GroovyBinding(String[] args) {
		this();
		this.setVariable("args", args);
	}

	public Object getVariable(String name) {
		Map map = (Map) localVars.get();
		Object result = null;
		if (map != null && map.containsKey(name)) {
			result = map.get(name);
		} else {
			result = propertyMap.get(name);
		}

		return result;
	}

	public void setVariable(String name, Object value) {
		if (localVars.get() == null) {
			LinkedHashMap vars = new LinkedHashMap();
			vars.put(name, value);
			localVars.set(vars);
		} else {
			((Map) localVars.get()).put(name, value);
		}

	}

	public Map getVariables() {
		return (Map) (localVars.get() == null ? new LinkedHashMap() : (Map) localVars.get());
	}

	public void clearVariables() {
		localVars.remove();
	}

	public Object getProperty(String property) {
		return propertyMap.get(property);
	}

	public void setProperty(String property, Object newValue) {
		propertyMap.put(property, newValue);
	}
}