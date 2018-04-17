package com.hotent.core.bpm.model;

import com.hotent.core.api.bpm.IBpmDao;
import com.hotent.core.bpm.model.FlowNode;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.Dom4jUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

public class NodeCache {
	private static final Log logger = LogFactory.getLog(NodeCache.class);
	private static Map<String, Map<String, FlowNode>> actNodesMap = new HashMap();

	public static synchronized Map<String, FlowNode> getByActDefId(String actDefId) {
		if (actNodesMap.containsKey(actDefId)) {
			return (Map) actNodesMap.get(actDefId);
		} else {
			Map map = readFromXml(actDefId);
			actNodesMap.put(actDefId, map);
			return map;
		}
	}

	public static FlowNode getStartNode(String actDefId) {
		getByActDefId(actDefId);
		Map nodeMap = (Map) actNodesMap.get(actDefId);
		return getStartNode(nodeMap);
	}

	public static FlowNode getStartNode(Map<String, FlowNode> nodeMap) {
		Iterator i$ = nodeMap.values().iterator();

		FlowNode flowNode;
		FlowNode parentNode;
		do {
			do {
				if (!i$.hasNext()) {
					return null;
				}

				flowNode = (FlowNode) i$.next();
			} while (!"startEvent".equals(flowNode.getNodeType()));

			parentNode = flowNode.getParentNode();
		} while (parentNode != null && !"callActivity".equals(parentNode.getNodeType()));

		return flowNode;
	}

	public static List<FlowNode> getFirstNode(String actDefId) {
		FlowNode startNode = getStartNode(actDefId);
		return (List) (startNode == null ? new ArrayList() : startNode.getNextFlowNodes());
	}

	public static FlowNode getFirstNodeId(String actDefId) throws Exception {
		FlowNode startNode = getStartNode(actDefId);
		if (startNode == null) {
			return null;
		} else {
			List list = startNode.getNextFlowNodes();
			if (list.size() > 1) {
				throw new Exception("流程定义:" + actDefId + ",起始节点后续节点超过1个，请检查流程图设置!");
			} else if (list.size() == 0) {
				throw new Exception("流程定义:" + actDefId + ",起始节点没有后续节点，请检查流程图设置!");
			} else {
				return (FlowNode) list.get(0);
			}
		}
	}

	public static boolean isMultipleFirstNode(String actDefId) throws Exception {
		FlowNode startNode = getStartNode(actDefId);
		if (startNode == null) {
			return false;
		} else {
			List list = startNode.getNextFlowNodes();
			if (list.size() == 0) {
				throw new Exception("流程定义:" + actDefId + ",起始节点没有后续节点，请检查流程图设置!");
			} else {
				return list.size() > 1;
			}
		}
	}

	public static boolean isFirstNode(String actDefId, String nodeId) {
		List list = getFirstNode(actDefId);
		Iterator i$ = list.iterator();

		FlowNode flowNode;
		do {
			if (!i$.hasNext()) {
				return false;
			}

			flowNode = (FlowNode) i$.next();
		} while (!nodeId.equals(flowNode.getNodeId()));

		return true;
	}

	public static boolean isSignTaskNode(String actDefId, String nodeId) {
		getByActDefId(actDefId);
		Map nodeMap = (Map) actNodesMap.get(actDefId);
		FlowNode flowNode = (FlowNode) nodeMap.get(nodeId);
		return flowNode.getIsMultiInstance().booleanValue() && flowNode.getNodeType().equals("userTask");
	}

	public static List<FlowNode> getEndNode(String actDefId) {
		getByActDefId(actDefId);
		Map nodeMap = (Map) actNodesMap.get(actDefId);
		return getEndNode(nodeMap);
	}

	public static FlowNode getNodeByActNodeId(String actDefId, String nodeId) {
		getByActDefId(actDefId);
		Map nodeMap = (Map) actNodesMap.get(actDefId);
		return (FlowNode) nodeMap.get(nodeId);
	}

	private static List<FlowNode> getEndNode(Map<String, FlowNode> nodeMap) {
		ArrayList flowNodeList = new ArrayList();
		Iterator i$ = nodeMap.values().iterator();

		while (i$.hasNext()) {
			FlowNode flowNode = (FlowNode) i$.next();
			if ("endEvent".equals(flowNode.getNodeType()) && BeanUtils.isEmpty(flowNode.getNextFlowNodes())) {
				flowNodeList.add(flowNode);
			}
		}

		return flowNodeList;
	}

	public static boolean hasExternalSubprocess(String actDefId) {
		getByActDefId(actDefId);
		Map nodeMap = (Map) actNodesMap.get(actDefId);
		Iterator i$ = nodeMap.values().iterator();

		FlowNode flowNode;
		do {
			if (!i$.hasNext()) {
				return false;
			}

			flowNode = (FlowNode) i$.next();
		} while (!"callActivity".equals(flowNode.getNodeType()));

		return true;
	}

	public static void clear(String actDefId) {
		actNodesMap.remove(actDefId);
	}

	private static Map<String, FlowNode> readFromXml(String actDefId) {
		IBpmDao dao = (IBpmDao) AppUtil.getBean(IBpmDao.class);
		String deployId = dao.getDeployIdByActdefId(actDefId);
		String xml = dao.getDefXmlByDeployId(deployId.toString());
		return parseXml(xml, (FlowNode) null);
	}

	private static String getXmlByDefKey(String actDefkey) {
		IBpmDao dao = (IBpmDao) AppUtil.getBean(IBpmDao.class);
		String xml = dao.getXmlByDefKey(actDefkey);
		return xml;
	}

	private static Map<String, FlowNode> parseXml(String xml, FlowNode parentNode) {
		xml = xml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document document = Dom4jUtil.loadXml(xml);
		Element el = document.getRootElement();
		Object map = new HashMap();
		Element processEl = (Element) el.selectSingleNode("./process");
		Iterator its = processEl.elementIterator();

		while (true) {
			String sourceRef;
			FlowNode sourceFlowNode;
			while (its.hasNext()) {
				Element seqFlowList = (Element) its.next();
				String i = seqFlowList.getName();
				String seqFlow = seqFlowList.attributeValue("id");
				sourceRef = seqFlowList.attributeValue("name");
				boolean targetRef = seqFlowList.selectSingleNode("./multiInstanceLoopCharacteristics") != null;
				if (!"startEvent".equals(i) && !"userTask".equals(i) && !"parallelGateway".equals(i)
						&& !"inclusiveGateway".equals(i) && !"exclusiveGateway".equals(i) && !"endEvent".equals(i)
						&& !"serviceTask".equals(i)) {
					if ("subProcess".equals(i)) {
						map = getSubInnerProFlowNode((Map) map, seqFlow, sourceRef, i, targetRef, seqFlowList);
					} else if ("callActivity".equals(i)) {
						String arg19 = seqFlowList.attributeValue("calledElement");
						String targetFlowNode = getXmlByDefKey(arg19);
						FlowNode flowNode = new FlowNode(seqFlow, sourceRef, i, targetRef);
						Map subChildNodes = parseXml(targetFlowNode, flowNode);
						flowNode.setAttribute("subFlowKey", arg19);
						((Map) map).put(seqFlow, flowNode);
						flowNode.setSubProcessNodes(subChildNodes);
					}
				} else {
					sourceFlowNode = new FlowNode(seqFlow, sourceRef, i, targetRef);
					if ("startEvent".equalsIgnoreCase(i)) {
						sourceFlowNode.setParentNode(parentNode);
					}

					((Map) map).put(seqFlow, sourceFlowNode);
				}
			}

			List arg15 = document.selectNodes("/definitions/process//sequenceFlow");

			for (int arg16 = 0; arg16 < arg15.size(); ++arg16) {
				Element arg17 = (Element) arg15.get(arg16);
				sourceRef = arg17.attributeValue("sourceRef");
				String arg18 = arg17.attributeValue("targetRef");
				sourceFlowNode = (FlowNode) ((Map) map).get(sourceRef);
				FlowNode arg20 = (FlowNode) ((Map) map).get(arg18);
				if (sourceFlowNode != null && arg20 != null) {
					logger.debug("sourceRef:" + sourceFlowNode.getNodeName() + " targetRef:" + arg20.getNodeName());
					if (arg20.getParentNode() != null) {
						logger.debug("parentNode:" + arg20.getParentNode().getNodeName());
					}

					if ("startEvent".equals(sourceFlowNode.getNodeType()) && sourceFlowNode.getParentNode() != null) {
						sourceFlowNode.setFirstNode(true);
						sourceFlowNode.getParentNode().setSubFirstNode(sourceFlowNode);
						if (arg20.getParentNode() != null
								&& arg20.getParentNode().getIsMultiInstance().booleanValue()) {
							arg20.setIsMultiInstance(Boolean.valueOf(true));
						}
					}

					sourceFlowNode.getNextFlowNodes().add(arg20);
					arg20.getPreFlowNodes().add(sourceFlowNode);
				}
			}

			return (Map) map;
		}
	}

	private static Map<String, FlowNode> getSubInnerProFlowNode(Map<String, FlowNode> map, String nodeId,
			String nodeName, String nodeType, boolean isMultiInstance, Element nodeEl) {
		FlowNode subProcessNode = new FlowNode(nodeId, nodeName, nodeType, new ArrayList(), isMultiInstance);
		map.put(nodeId, subProcessNode);
		List subElements = nodeEl.elements();
		Iterator i$ = subElements.iterator();

		while (true) {
			while (i$.hasNext()) {
				Element subEl = (Element) i$.next();
				String subNodeType = subEl.getName();
				boolean subIsMultiInstance = nodeEl.selectSingleNode("./multiInstanceLoopCharacteristics") != null;
				String subNodeName = subEl.attributeValue("name");
				String subNodeId = subEl.attributeValue("id");
				if (!"startEvent".equals(subNodeType) && !"userTask".equals(subNodeType)
						&& !"parallelGateway".equals(subNodeType) && !"inclusiveGateway".equals(subNodeType)
						&& !"exclusiveGateway".equals(subNodeType) && !"endEvent".equals(subNodeType)
						&& !"serviceTask".equals(subNodeType)) {
					if ("callActivity".equals(subNodeType)) {
						String flowKey1 = subEl.attributeValue("calledElement");
						String subProcessXml = getXmlByDefKey(flowKey1);
						FlowNode flowNode = new FlowNode(subNodeId, subNodeName, subNodeType, subIsMultiInstance);
						subProcessNode.getSubFlowNodes().add(flowNode);
						flowNode.setAttribute("subFlowKey", flowKey1);
						Map subChildNodes = parseXml(subProcessXml, flowNode);
						flowNode.setSubProcessNodes(subChildNodes);
						map.put(subNodeId, flowNode);
					} else if ("subProcess".equals(subNodeType)) {
						map = getSubInnerProFlowNode(map, subNodeId, subNodeName, subNodeType, subIsMultiInstance,
								subEl);
					}
				} else {
					FlowNode flowKey = new FlowNode(subNodeId, subNodeName, subNodeType, false, subProcessNode);
					subProcessNode.getSubFlowNodes().add(flowKey);
					map.put(subNodeId, flowKey);
				}
			}

			return map;
		}
	}

	public static Set<String> getSubKeysByXml(String xml) {
		HashSet keySet = new HashSet();
		getSubKeysByXml(xml, keySet);
		return keySet;
	}

	private static void getSubKeysByXml(String xml, Set<String> keySet) {
		xml = xml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document document = Dom4jUtil.loadXml(xml);
		Element el = document.getRootElement();
		Element processEl = (Element) el.selectSingleNode("./process");
		if (!BeanUtils.isEmpty(processEl)) {
			Iterator its = processEl.elementIterator();

			while (its.hasNext()) {
				Element nodeEl = (Element) its.next();
				String nodeType = nodeEl.getName();
				if ("callActivity".equals(nodeType)) {
					String flowKey = nodeEl.attributeValue("calledElement");
					keySet.add(flowKey);
					String subProcessXml = getXmlByDefKey(flowKey);
					getSubKeysByXml(subProcessXml, keySet);
				}
			}

		}
	}
}