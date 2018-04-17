package com.hotent.core.bpm.model;

import com.hotent.core.util.BeanUtils;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

public class ProcessTaskHistory {
	private String id;
	private String processDefinitionId;
	private String processInstanceId;
	private String executionId;
	private String name;
	private String parentTaskId;
	private String description;
	private String owner;
	private String assignee;
	private Date startTime;
	private Date endTime;
	private Long durationInMillis;
	private String deleteReason;
	private String taskDefinitionKey;
	private Integer priority;
	private Date dueDate;

	public ProcessTaskHistory() {
	}

	public ProcessTaskHistory(TaskEntity taskEntity) {
		this.processDefinitionId = taskEntity.getId();
		this.processInstanceId = taskEntity.getProcessInstanceId();
		this.executionId = taskEntity.getExecutionId();
		this.name = taskEntity.getName();
		this.parentTaskId = taskEntity.getParentTaskId();
		this.description = taskEntity.getDescription();
		this.owner = taskEntity.getOwner();
		this.assignee = taskEntity.getAssignee();
		this.startTime = taskEntity.getCreateTime();
		this.dueDate = taskEntity.getDueDate();

		try {
			this.durationInMillis = Long.valueOf(taskEntity.getDueDate() == null
					? 0L
					: taskEntity.getDueDate().getTime() - taskEntity.getCreateTime().getTime());
		} catch (Exception arg2) {
			;
		}

		this.taskDefinitionKey = taskEntity.getTaskDefinitionKey();
		this.priority = Integer.valueOf(taskEntity.getPriority());
	}

	public ProcessTaskHistory(HistoricTaskInstanceEntity historyTask) {
		BeanUtils.copyProperties(this, historyTask);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
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

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getDurationInMillis() {
		return this.durationInMillis;
	}

	public void setDurationInMillis(Long durationInMillis) {
		this.durationInMillis = durationInMillis;
	}

	public String getDeleteReason() {
		return this.deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getTaskDefinitionKey() {
		return this.taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}