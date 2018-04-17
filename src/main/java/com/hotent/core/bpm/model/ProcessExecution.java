package com.hotent.core.bpm.model;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public class ProcessExecution {
	private String id;
	private Integer revision;
	private String processInstanceId;
	private String businessKey;
	private String processDefinitionId;
	private String activityId;
	private Short isActive;
	private Short isConcurrent;
	private Short isScope;
	private String parentId;
	private String superExecutionId;
	private Short isEventScope;
	private Integer suspensionState;
	private Integer cachedEntityState;

	public ProcessExecution() {
	}

	public ProcessExecution(ExecutionEntity executionEntity) {
		this.revision = Integer.valueOf(executionEntity.getRevision());
		this.processInstanceId = executionEntity.getProcessInstanceId();
		this.processDefinitionId = executionEntity.getProcessDefinitionId();
		this.activityId = executionEntity.getActivityId();
		this.isActive = Short.valueOf((short) (executionEntity.isActive() ? 1 : 0));
		this.isConcurrent = Short.valueOf((short) (executionEntity.isConcurrent() ? 1 : 0));
		this.isScope = Short.valueOf((short) (executionEntity.isScope() ? 1 : 0));
		this.parentId = executionEntity.getId();
		this.superExecutionId = executionEntity.getSuperExecutionId();
		this.isEventScope = Short.valueOf((short) (executionEntity.isEventScope() ? 1 : 0));
		this.suspensionState = Integer.valueOf(executionEntity.getSuspensionState());
		this.cachedEntityState = Integer.valueOf(executionEntity.getCachedEntityState());
	}

	public Integer getRevision() {
		return this.revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getSuperExecutionId() {
		return this.superExecutionId;
	}

	public void setSuperExecutionId(String superExecutionId) {
		this.superExecutionId = superExecutionId;
	}

	public String getActivityId() {
		return this.activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public Short getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}

	public Short getIsConcurrent() {
		return this.isConcurrent;
	}

	public void setIsConcurrent(Short isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public Short getIsScope() {
		return this.isScope;
	}

	public void setIsScope(Short isScope) {
		this.isScope = isScope;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Short getIsEventScope() {
		return this.isEventScope;
	}

	public void setIsEventScope(Short isEventScope) {
		this.isEventScope = isEventScope;
	}

	public Integer getSuspensionState() {
		return this.suspensionState;
	}

	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}

	public Integer getCachedEntityState() {
		return this.cachedEntityState;
	}

	public void setCachedEntityState(Integer cachedEntityState) {
		this.cachedEntityState = cachedEntityState;
	}
}