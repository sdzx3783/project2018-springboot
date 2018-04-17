package com.hotent.core.datakey;

public interface IKeyGenerator {
	Object nextId() throws Exception;

	void setAlias(String arg0);
}