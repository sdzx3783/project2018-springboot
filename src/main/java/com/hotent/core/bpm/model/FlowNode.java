package com.hotent.core.bpm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FlowNode {
	public static final String TYPE_USERTASK = "userTask";
	public static final String TYPE_SUBPROCESS = "subProcess";
	public static final String TYPE_START_EVENT = "startEvent";
	public static final String TYPE_EXCLUSIVEGATEWAY = "exclusiveGateway";
	private String nodeId = null;
	private String nodeName = null;
	private String nodeType = null;
	private Boolean isMultiInstance = Boolean.valueOf(false);
	private FlowNode parentNode = null;
	private boolean isFirstNode = false;
	private Map<String, String> attrMap = new HashMap();
	private Map<String, FlowNode> subProcessNodes = new HashMap();
	private List<FlowNode> preFlowNodes = new ArrayList();
	private List<FlowNode> nextFlowNodes = new ArrayList();
	private List<FlowNode> subFlowNodes = new ArrayList();
	private FlowNode subFirstNode = null;

	public String getNodeId() {
		return this.nodeId;
	}

	public FlowNode() {
	}

	public FlowNode(String nodeId, String nodeName, String nodeType) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
	}

	public FlowNode(String nodeId, String nodeName, String nodeType, List<FlowNode> subFlowNodes) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.subFlowNodes = subFlowNodes;
	}

	public FlowNode(String nodeId, String nodeName, String nodeType, boolean isMultiInstance) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.isMultiInstance = Boolean.valueOf(isMultiInstance);
	}

	public FlowNode(String nodeId, String nodeName, String nodeType, boolean isMultiInstance, FlowNode parentNode) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.isMultiInstance = Boolean.valueOf(isMultiInstance);
		this.parentNode = parentNode;
	}

	public FlowNode(String nodeId, String nodeName, String nodeType, List<FlowNode> subFlowNodes,
			boolean isMultiInstance) {
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.subFlowNodes = subFlowNodes;
		this.isMultiInstance = Boolean.valueOf(isMultiInstance);
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

	public String getNodeType() {
		return this.nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public List<FlowNode> getPreFlowNodes() {
		return this.preFlowNodes;
	}

	public void setPreFlowNodes(List<FlowNode> preFlowNodes) {
		this.preFlowNodes = preFlowNodes;
	}

	public List<FlowNode> getNextFlowNodes() {
		return this.nextFlowNodes;
	}

	public void setNextFlowNodes(List<FlowNode> nextFlowNodes) {
		this.nextFlowNodes = nextFlowNodes;
	}

	public List<FlowNode> getSubFlowNodes() {
		return this.subFlowNodes;
	}

	public void setSubFlowNodes(List<FlowNode> subFlowNodes) {
		this.subFlowNodes = subFlowNodes;
	}

	public Boolean getIsMultiInstance() {
		return this.isMultiInstance;
	}

	public void setIsMultiInstance(Boolean isMultiInstance) {
		this.isMultiInstance = isMultiInstance;
	}

	public boolean isFirstNode() {
		return this.isFirstNode;
	}

	public void setFirstNode(boolean isFirstNode) {
		this.isFirstNode = isFirstNode;
	}

	public FlowNode getSubFirstNode() {
		return this.subFirstNode;
	}

	public void setSubFirstNode(FlowNode subFirstNode) {
		this.subFirstNode = subFirstNode;
	}

	public FlowNode getParentNode() {
		return this.parentNode;
	}

	public void setParentNode(FlowNode parentNode) {
		this.parentNode = parentNode;
	}

	public boolean getIsSubProcess() {
		return this.nodeType.equalsIgnoreCase("subProcess");
	}

	public boolean getIsCallActivity() {
		return this.nodeType.equalsIgnoreCase("callActivity");
	}

	public boolean getIsSignNode() {
		return this.nodeType.equalsIgnoreCase("userTask") && this.getIsMultiInstance().booleanValue();
	}

	public boolean getIsFirstNode() {
		if (this.getPreFlowNodes() != null) {
			List list = this.getPreFlowNodes();
			Iterator i$ = list.iterator();

			while (i$.hasNext()) {
				FlowNode node = (FlowNode) i$.next();
				if (node.getNodeType().equalsIgnoreCase("startEvent")) {
					return true;
				}
			}
		}

		return false;
	}

	public void setAttribute(String name, String value) {
		this.attrMap.put(name, value);
	}

	public String getAttribute(String name) {
		return this.attrMap.containsKey(name) ? (String) this.attrMap.get(name) : "";
	}

	public Map<String, FlowNode> getSubProcessNodes() {
		return this.subProcessNodes;
	}

	public void setSubProcessNodes(Map<String, FlowNode> subProcessNodes) {
		this.subProcessNodes = subProcessNodes;
	}
}