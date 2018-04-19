package com.hotent.core.api.bpm.model;

import java.util.Date;
import java.util.Map;

public interface IBpmDefinition {
	void setDefId(Long arg0);

	Long getDefId();

	Short getIsUseOutForm();

	void setIsUseOutForm(Short arg0);

	void setTypeId(Long arg0);

	String getFormDetailUrl();

	void setFormDetailUrl(String arg0);

	Long getTypeId();

	void setSubject(String arg0);

	String getSubject();

	void setDefKey(String arg0);

	String getDefKey();

	void setTaskNameRule(String arg0);

	String getTaskNameRule();

	void setDescp(String arg0);

	String getDescp();

	void setCreatetime(Date arg0);

	Date getCreatetime();

	void setStatus(Short arg0);

	String getTestStatusTag();

	void setTestStatusTag(String arg0);

	Short getStatus();

	void setDefXml(String arg0);

	String getDefXml();

	void setActDeployId(Long arg0);

	Long getActDeployId();

	void setActDefKey(String arg0);

	String getActDefKey();

	void setActDefId(String arg0);

	String getActDefId();

	void setCreateBy(Long arg0);

	Long getCreateBy();

	void setUpdateBy(Long arg0);

	Long getUpdateBy();

	void setReason(String arg0);

	String getReason();

	void setVersionNo(Integer arg0);

	Integer getVersionNo();

	void setParentDefId(Long arg0);

	Long getParentDefId();

	void setIsMain(Short arg0);

	Short getIsMain();

	void setUpdatetime(Date arg0);

	Date getUpdatetime();

	String getTypeName();

	void setTypeName(String arg0);

	Short getShowFirstAssignee();

	void setShowFirstAssignee(Short arg0);

	Short getToFirstNode();

	void setToFirstNode(Short arg0);

	String getCanChoicePath();

	void setCanChoicePath(String arg0);

	Short getIsPrintForm();

	void setIsPrintForm(Short arg0);

	Integer getAllowFinishedDivert();

	void setAllowFinishedDivert(Integer arg0);

	Integer getAllowFinishedCc();

	void setAllowFinishedCc(Integer arg0);

	Integer getAllowDivert();

	void setAllowDivert(Integer arg0);

	Long getAttachment();

	void setAttachment(Long arg0);

	String getInformType();

	void setInformType(String arg0);

	Short getSameExecutorJump();

	void setSameExecutorJump(Short arg0);

	void setCanChoicePathNodeMap(Map arg0);

	Integer getSubmitConfirm();

	void setSubmitConfirm(Integer arg0);

	String getInformStart();

	void setInformStart(String arg0);

	Integer getAllowRefer();

	void setAllowRefer(Integer arg0);

	Integer getInstanceAmount();

	void setInstanceAmount(Integer arg0);

	Integer getDirectstart();

	void setDirectstart(Integer arg0);

	String getCcMessageType();

	void setCcMessageType(String arg0);

	Integer getAllowMobile();

	void setAllowMobile(Integer arg0);

	Integer getAllowRevert();

	void setAllowRevert(Integer arg0);

	String getSkipSetting();

	void setSkipSetting(String arg0);
}