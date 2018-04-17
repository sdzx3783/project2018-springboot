package com.hotent.core.bpm.model;

public class NodeCondition {
	private String nodeName = "";
	private String nodeId = "";
	private String condition = "";

	public NodeCondition(String nodeName, String nodeId, String condition) {
		this.nodeName = nodeName;
		this.nodeId = nodeId;
		this.condition = condition;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}