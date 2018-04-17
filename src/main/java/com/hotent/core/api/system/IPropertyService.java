package com.hotent.core.api.system;

public interface IPropertyService {
	String getByAlias(String arg0);

	String getByAlias(String arg0, String arg1);

	Integer getIntByAlias(String arg0);

	Integer getIntByAlias(String arg0, Integer arg1);

	Long getLongByAlias(String arg0);

	boolean getBooleanByAlias(String arg0);

	boolean getBooleanByAlias(String arg0, boolean arg1);
}