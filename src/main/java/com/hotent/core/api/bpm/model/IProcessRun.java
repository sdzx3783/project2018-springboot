package com.hotent.core.api.bpm.model;

import java.util.Date;

public interface IProcessRun {
	Long getRunId();

	void setRunId(Long arg0);

	Long getDefId();

	void setDefId(Long arg0);

	String getSubject();

	void setSubject(String arg0);

	Long getCreatorId();

	void setCreatorId(Long arg0);

	String getCreator();

	void setCreator(String arg0);

	Date getCreatetime();

	void setCreatetime(Date arg0);

	String getBusDescp();

	void setBusDescp(String arg0);

	Short getStatus();

	void setStatus(Short arg0);

	String getActInstId();

	void setActInstId(String arg0);

	String getActDefId();

	void setActDefId(String arg0);

	String getBusinessKey();

	void setBusinessKey(String arg0);

	Long getBusinessKeyNum();

	void setBusinessKeyNum(Long arg0);

	String getBusinessUrl();

	void setBusinessUrl(String arg0);

	Date getEndTime();

	void setEndTime(Date arg0);

	Long getDuration();

	void setDuration(Long arg0);

	String getProcessName();

	void setProcessName(String arg0);

	String getPkName();

	void setPkName(String arg0);

	String getTableName();

	void setTableName(String arg0);

	Long getParentId();

	void setParentId(Long arg0);

	String getStartOrgName();

	void setStartOrgName(String arg0);

	Long getStartOrgId();

	void setStartOrgId(Long arg0);

	Short getRecover();

	void setRecover(Short arg0);

	Long getFormDefId();

	void setFormDefId(Long arg0);

	String getDsAlias();

	void setDsAlias(String arg0);

	String getFormKeyUrl();

	void setFormKeyUrl(String arg0);

	Short getFormType();

	void setFormType(Short arg0);

	String getFlowKey();

	void setFlowKey(String arg0);
}