package com.hotent.core.bpm.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ProcessTask {
	private String id;
	private String name;
	private String subject;
	private String parentTaskId;
	private String description;
	protected int priority;
	private Date createTime;
	private String owner;
	private String assignee;
	private String delegationState;
	private String executionId;
	private String processInstanceId;
	private String processDefinitionId;
	private String taskDefinitionKey;
	private Date dueDate;
	private Integer revision;
	private String processName;
	private String taskUrl;
	private String status;
	private String type;
	private Integer allowDivert;
	private Integer ischeck;
	private Long defId;
	private Integer allowBatchApprove;
	private Long runId;
	private Long typeId;
	private String typeName;
	private String orgName;
	private String tagIds;
	private Long creatorId;
	private String creator;
	private Boolean isAgent = Boolean.valueOf(false);
	private Boolean isDivert = Boolean.valueOf(false);
	private Short taskStatus;
	private String codebefore;
	private Integer hasRead = Integer.valueOf(0);
	private Integer reminderLv;
	private Date createDate;
	private String globalFlowNo;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentTaskId() {
		return this.parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getDelegationState() {
		return this.delegationState;
	}

	public void setDelegationState(String delegationState) {
		this.delegationState = delegationState;
	}

	public String getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getTaskDefinitionKey() {
		return this.taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getRevision() {
		return this.revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public String getProcessName() {
		return this.processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getTaskUrl() {
		return this.taskUrl;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAllowDivert() {
		return this.allowDivert;
	}

	public void setAllowDivert(Integer allowDivert) {
		this.allowDivert = allowDivert;
	}

	public Integer getIscheck() {
		return this.ischeck;
	}

	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}

	public Long getDefId() {
		return this.defId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public Integer getAllowBatchApprove() {
		return this.allowBatchApprove;
	}

	public void setAllowBatchApprove(Integer allowBatchApprove) {
		this.allowBatchApprove = allowBatchApprove;
	}

	public Long getRunId() {
		return this.runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Long getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getTagIds() {
		return this.tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Boolean getIsAgent() {
		return this.isAgent;
	}

	public void setIsAgent(Boolean isAgent) {
		this.isAgent = isAgent;
	}

	public Boolean getIsDivert() {
		return this.isDivert;
	}

	public void setIsDivert(Boolean isDivert) {
		this.isDivert = isDivert;
	}

	public Short getTaskStatus() {
		return this.taskStatus;
	}

	public void setTaskStatus(Short taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getCodebefore() {
		return this.codebefore;
	}

	public void setCodebefore(String codebefore) {
		this.codebefore = codebefore;
	}

	public Integer getHasRead() {
		return this.hasRead;
	}

	public void setHasRead(Integer hasRead) {
		this.hasRead = hasRead;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

	public Integer getReminderLv() {
		return this.reminderLv;
	}

	public void setReminderLv(Integer reminderLv) {
		this.reminderLv = reminderLv;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getGlobalFlowNo() {
		return this.globalFlowNo;
	}

	public void setGlobalFlowNo(String globalFlowNo) {
		this.globalFlowNo = globalFlowNo;
	}
}