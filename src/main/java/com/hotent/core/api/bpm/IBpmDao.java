package com.hotent.core.api.bpm;

public interface IBpmDao {
	String getActDefIdByDeployId(String arg0);

	String getDefXmlByDeployId(String arg0);

	void wirteDefXml(String arg0, String arg1);

	String getDeployIdByActdefId(String arg0);

	String getXmlByDefKey(String arg0);
}