package com.hotent.core.cache;

public interface ICache {
	void add(String arg0, Object arg1, int arg2);

	void add(String arg0, Object arg1);

	void delByKey(String arg0);

	void clearAll();

	Object getByKey(String arg0);

	boolean containKey(String arg0);
}