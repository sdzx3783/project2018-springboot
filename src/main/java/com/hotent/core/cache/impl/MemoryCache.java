package com.hotent.core.cache.impl;

import com.hotent.core.cache.ICache;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
@Component
public class MemoryCache implements ICache {
	private Map<String, Object> cache = Collections.synchronizedMap(new HashMap());

	public synchronized void add(String key, Object obj, int timeout) {
		this.cache.put(key, obj);
	}

	public synchronized void add(String key, Object obj) {
		this.cache.put(key, obj);
	}

	public synchronized void delByKey(String key) {
		this.cache.remove(key);
	}

	public void clearAll() {
		this.cache.clear();
	}

	public synchronized Object getByKey(String key) {
		return this.cache.get(key);
	}

	public boolean containKey(String key) {
		return this.cache.containsKey(key);
	}
}