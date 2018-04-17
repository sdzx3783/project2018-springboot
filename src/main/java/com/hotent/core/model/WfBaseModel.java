package com.hotent.core.model;

import com.hotent.core.model.BaseModel;

public class WfBaseModel extends BaseModel {
	private Long taskId = Long.valueOf(0L);
	private Long runId = Long.valueOf(0L);
	private Long actInstId = Long.valueOf(0L);
	private String nodeId = "";
	private String nodeName = "";

	public Long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getRunId() {
		return this.runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Long getActInstId() {
		return this.actInstId;
	}

	public void setActInstId(Long actInstId) {
		this.actInstId = actInstId;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}