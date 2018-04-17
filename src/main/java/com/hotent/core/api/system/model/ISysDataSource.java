package com.hotent.core.api.system.model;

public interface ISysDataSource {
	void setId(Long arg0);

	Long getId();

	void setName(String arg0);

	String getName();

	void setAlias(String arg0);

	String getAlias();

	void setDbType(String arg0);

	String getDbType();

	void setSettingJson(String arg0);

	String getSettingJson();

	Boolean getInitOnStart();

	void setInitOnStart(Boolean arg0);

	Boolean getEnabled();

	void setEnabled(Boolean arg0);

	void setClassPath(String arg0);

	String getClassPath();

	void setInitMethod(String arg0);

	String getInitMethod();

	void setCloseMethod(String arg0);

	String getCloseMethod();

	Long getRunId();

	void setRunId(Long arg0);
}