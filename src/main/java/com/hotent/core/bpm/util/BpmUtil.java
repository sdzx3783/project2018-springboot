package com.hotent.core.bpm.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.apache.commons.lang.time.DateUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.hotent.core.bpm.graph.Point;
import com.hotent.core.bpm.model.ForkNode;
import com.hotent.core.bpm.model.NodeCondition;
import com.hotent.core.model.TaskExecutor;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.ClassLoadUtil;
import com.hotent.core.util.Dom4jUtil;
import com.hotent.core.util.StringUtil;

public class BpmUtil {
	private static final String VAR_PRE_NAME = "v_";
	private static final String BPM_XML_NS = "xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"";

	public static List<TaskExecutor> getTaskExecutors(String executors) {
		String[] aryExecutor = executors.split("#");
		ArrayList list = new ArrayList();
		String[] arr$ = aryExecutor;
		int len$ = aryExecutor.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			String tmp = arr$[i$];
			String[] aryTmp = tmp.split("\\^");
			if (aryTmp.length == 3) {
				list.add(new TaskExecutor(aryTmp[0], aryTmp[1], aryTmp[2]));
			} else if (aryTmp.length == 1) {
				list.add(new TaskExecutor(aryTmp[0]));
			}
		}

		return list;
	}

	public static Map<String, Map<String, String>> getTranstoActivitys(String defXml, List<String> nodes,
			Boolean flag) {
		Map actMap = getTaskActivitys(defXml, flag);
		Collection values = actMap.values();
		Iterator i$ = nodes.iterator();

		while (i$.hasNext()) {
			String node = (String) i$.next();
			Iterator i$1 = values.iterator();

			while (i$1.hasNext()) {
				Map map = (Map) i$1.next();
				map.remove(node);
			}
		}

		return actMap;
	}

	public static Map<String, Map<String, String>> getTaskActivitys(String defXml) {
		return getTaskActivitys(defXml, Boolean.valueOf(true));
	}

	public static Map<String, Map<String, String>> getTaskActivitys(String defXml, Boolean flag) {
		HashMap rtnMap = new HashMap();
		defXml = defXml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document doc = Dom4jUtil.loadXml(defXml);
		Element root = doc.getRootElement();
		List list = root.selectNodes("./process//userTask");
		HashMap taskMap = new HashMap();
		addToMap(list, taskMap);
		List callActivityList = root.selectNodes("./process//callActivity");
		if (callActivityList.size() > 0) {
			addToMap(callActivityList, taskMap);
		}

		rtnMap.put("任务节点", taskMap);
		HashMap gateWayMap = new HashMap();
		List parallelGatewayList = root.selectNodes("./process//parallelGateway");
		if (parallelGatewayList.size() > 0) {
			addToMap(parallelGatewayList, gateWayMap);
		}

		List inclusiveGatewayList = root.selectNodes("./process//inclusiveGateway");
		if (inclusiveGatewayList.size() > 0) {
			addToMap(inclusiveGatewayList, gateWayMap);
		}

		List exclusiveGatewayGatewayList = root.selectNodes("./process//exclusiveGateway");
		if (exclusiveGatewayGatewayList.size() > 0) {
			addToMap(exclusiveGatewayGatewayList, gateWayMap);
		}

		if (gateWayMap.size() > 0) {
			rtnMap.put("网关节点", gateWayMap);
		}

		List endList;
		HashMap endMap;
		if (flag.booleanValue()) {
			endList = root.selectNodes("./process//startEvent");
			endMap = new HashMap();
			addToMap(endList, endMap);
			rtnMap.put("开始节点", endMap);
		}

		endList = root.selectNodes("./process//endEvent");
		endMap = new HashMap();
		addToMap(endList, endMap);
		rtnMap.put("结束节点", endMap);
		List serviceTask = root.selectNodes("./process//serviceTask");
		if (serviceTask.size() > 0) {
			HashMap serviceMap = new HashMap();
			addToMap(serviceTask, serviceMap);
			rtnMap.put("自动任务", serviceMap);
		}

		return rtnMap;
	}

	private static void addToMap(List<Node> list, Map<String, String> map) {
		Iterator i$ = list.iterator();

		while (i$.hasNext()) {
			Node node = (Node) i$.next();
			Element el = (Element) node;
			String id = el.attributeValue("id");
			String name = el.attributeValue("name");
			map.put(id, name);
		}

	}

	public static Map<String, Map<String, String>> getTranstoActivitys(String defXml, List<String> nodes) {
		Map actMap = getTaskActivitys(defXml);
		Collection values = actMap.values();
		Iterator i$ = nodes.iterator();

		while (i$.hasNext()) {
			String node = (String) i$.next();
			Iterator i$1 = values.iterator();

			while (i$1.hasNext()) {
				Map map = (Map) i$1.next();
				map.remove(node);
			}
		}

		return actMap;
	}

	public static boolean isTaskListener(String className) {
		try {
			Class e = Class.forName(className);
			return BeanUtils.isInherit(e, TaskListener.class);
		} catch (ClassNotFoundException arg1) {
			return false;
		}
	}

	public static String transform(String id, String name, String xml)
			throws TransformerFactoryConfigurationError, Exception {
		return ClassLoadUtil.transform(id, name, xml);
	}

	private static Map<String, Integer> caculateCenterPosition(List<Integer> waypoints) {
		boolean x = false;
		boolean y = false;
		boolean flag = false;
		HashMap point = new HashMap();
		ArrayList lens = new ArrayList();

		int halfLen;
		for (halfLen = 2; halfLen < waypoints.size(); halfLen += 2) {
			lens.add(Integer.valueOf(Math.abs(
					((Integer) waypoints.get(halfLen - 2)).intValue() - ((Integer) waypoints.get(halfLen)).intValue())
					+ Math.abs(((Integer) waypoints.get(halfLen - 1)).intValue()
							- ((Integer) waypoints.get(halfLen + 1)).intValue())));
		}

		halfLen = 0;

		int i;
		for (Iterator accumulativeLen = lens.iterator(); accumulativeLen.hasNext(); halfLen += i) {
			i = ((Integer) accumulativeLen.next()).intValue();
		}

		halfLen /= 2;
		int arg11 = 0;

		for (i = 0; i < lens.size(); ++i) {
			arg11 += ((Integer) lens.get(i)).intValue();
			if (arg11 > halfLen) {
				break;
			}
		}

		++i;
		int arg8;
		int arg9;
		byte arg10;
		if (((Integer) waypoints.get(2 * i)).intValue() == ((Integer) waypoints.get(2 * (i - 1))).intValue()) {
			if (((Integer) waypoints.get(2 * i + 1)).intValue() > ((Integer) waypoints.get(2 * (i - 1) + 1))
					.intValue()) {
				arg9 = halfLen - (arg11 - ((Integer) lens.get(i - 1)).intValue())
						+ ((Integer) waypoints.get(2 * (i - 1) + 1)).intValue();
				arg10 = 1;
			} else {
				arg9 = arg11 - ((Integer) lens.get(i - 1)).intValue()
						+ ((Integer) waypoints.get(2 * (i - 1) + 1)).intValue() - halfLen;
				arg10 = 2;
			}

			arg8 = ((Integer) waypoints.get(2 * i)).intValue();
		} else {
			if (((Integer) waypoints.get(2 * i)).intValue() > ((Integer) waypoints.get(2 * (i - 1))).intValue()) {
				arg8 = halfLen - (arg11 - ((Integer) lens.get(i - 1)).intValue())
						+ ((Integer) waypoints.get(2 * (i - 1))).intValue();
				arg10 = 3;
			} else {
				arg8 = arg11 - ((Integer) lens.get(i - 1)).intValue()
						+ ((Integer) waypoints.get(2 * (i - 1))).intValue() - halfLen;
				arg10 = 4;
			}

			arg9 = ((Integer) waypoints.get(2 * i + 1)).intValue();
		}

		point.put("x", Integer.valueOf(arg8));
		point.put("y", Integer.valueOf(arg9));
		point.put("flag", Integer.valueOf(arg10));
		return point;
	}

	private static Point[] caculateLabelRectangle(String name, List<Integer> waypoints) {
		if (name == null) {
			return new Point[0];
		} else {
			BufferedImage processDiagram = new BufferedImage(2, 2, 2);
			Graphics2D g = processDiagram.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setPaint(Color.black);
			Font font = new Font("宋体", 1, 12);
			g.setFont(font);
			FontMetrics fontMetrics = g.getFontMetrics();
			Map pos = caculateCenterPosition(waypoints);
			int x = ((Integer) pos.get("x")).intValue();
			int y = ((Integer) pos.get("y")).intValue();
			int flag = ((Integer) pos.get("flag")).intValue();
			int drawX = x;
			int drawY = y;
			switch (flag) {
				case 1 :
					drawX = x + fontMetrics.getHeight() / 2;
					drawY = y;
					break;
				case 2 :
					drawX = x - fontMetrics.stringWidth(name) - fontMetrics.getHeight() / 2;
					drawY = y + fontMetrics.getHeight();
					break;
				case 3 :
					drawX = x - fontMetrics.stringWidth(name) / 2;
					drawY = y - fontMetrics.getHeight() / 2 - fontMetrics.getHeight();
					break;
				case 4 :
					drawX = x - fontMetrics.stringWidth(name) / 2;
					drawY = y + fontMetrics.getHeight() - fontMetrics.getHeight();
			}

			Point[] points = new Point[]{new Point((float) drawX, (float) drawY), new Point(
					(float) (drawX + fontMetrics.stringWidth(name)), (float) (drawY + fontMetrics.getHeight()))};
			return points;
		}
	}

	public static ForkNode getForkNode(String forkNode, String xml) throws IOException {
		xml = xml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "xmlns:bpm=\'hotent\'");
		Document doc = Dom4jUtil.loadXml(xml);
		Element root = doc.getRootElement();
		List preNodes = root.selectNodes("//sequenceFlow[@targetRef=\'" + forkNode + "\']");
		ForkNode model = new ForkNode();
		model.setForkNodeId(forkNode);
		Element el;
		if (preNodes.size() == 1) {
			Element nodes = (Element) preNodes.get(0);
			String i$ = nodes.attributeValue("sourceRef");
			el = (Element) root.selectSingleNode("//userTask[@id=\'" + i$ + "\']");
			if (el != null) {
				model.setPreNodeId(i$);
				Element id = el.element("multiInstanceLoopCharacteristics");
				if (id != null) {
					model.setMulti(true);
				}
			}
		}

		List nodes1 = root.selectNodes("//sequenceFlow[@sourceRef=\'" + forkNode + "\']");
		Iterator i$1 = nodes1.iterator();

		while (i$1.hasNext()) {
			el = (Element) i$1.next();
			String id1 = el.attributeValue("targetRef");
			String condition = "";
			Element conditionNode = el.element("conditionExpression");
			if (conditionNode != null) {
				condition = conditionNode.getText().trim();
				condition = StringUtil.trimPrefix(condition, "${");
				condition = StringUtil.trimSufffix(condition, "}");
			}

			Element targetNode = (Element) root.selectSingleNode("//*[@id=\'" + id1 + "\']");
			String nodeName = targetNode.attributeValue("name");
			NodeCondition nodeCondition = new NodeCondition(nodeName, id1, condition);
			model.addNode(nodeCondition);
		}

		return model;
	}

	public static Map<String, String> getDecisionConditions(String processXml, String decisionNodeId) {
		HashMap map = new HashMap();
		processXml = processXml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"",
				"xmlns:bpm=\'hotent\'");
		Document doc = Dom4jUtil.loadXml(processXml);
		Element root = doc.getRootElement();
		List nodes = root.selectNodes("//sequenceFlow[@sourceRef=\'" + decisionNodeId + "\']");

		String id;
		String condition;
		for (Iterator i$ = nodes.iterator(); i$.hasNext(); map.put(id, condition)) {
			Element el = (Element) i$.next();
			id = el.attributeValue("targetRef");
			condition = "";
			Element conditionNode = el.element("conditionExpression");
			if (conditionNode != null) {
				condition = conditionNode.getText().trim();
				condition = StringUtil.trimPrefix(condition, "${");
				condition = StringUtil.trimSufffix(condition, "}");
			}
		}

		return map;
	}

	public static String setCondition(String sourceNode, Map<String, String> map, String xml) throws IOException {
		xml = xml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "xmlns:bpm=\"hotent\"");
		Document doc = Dom4jUtil.loadXml(xml, "utf-8");
		Element root = doc.getRootElement();
		List nodes = root.selectNodes("//sequenceFlow[@sourceRef=\'" + sourceNode + "\']");
		Iterator outXml = nodes.iterator();

		while (outXml.hasNext()) {
			Element el = (Element) outXml.next();
			String id = el.attributeValue("targetRef");
			String condition = (String) map.get(id);
			Element conditionEl = el.element("conditionExpression");
			if (conditionEl != null) {
				el.remove(conditionEl);
			}

			if (StringUtil.isNotEmpty(condition)) {
				Element elAdd = el.addElement("conditionExpression");
				elAdd.addAttribute("xsi:type", "tFormalExpression");
				elAdd.addCDATA(condition);
			}
		}

		String outXml1 = doc.asXML();
		outXml1 = outXml1.replace("xmlns:bpm=\"hotent\"", "xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"");
		return outXml1;
	}

	

	public static Object getValue(String type, String paramValue) {
		Object value = null;
		if ("S".equals(type)) {
			value = paramValue;
		} else if ("L".equals(type)) {
			value = new Long(paramValue);
		} else if ("I".equals(type)) {
			value = new Integer(paramValue);
		} else if ("DB".equals(type)) {
			value = new java.lang.Double(paramValue);
		} else if ("BD".equals(type)) {
			value = new BigDecimal(paramValue);
		} else if ("F".equals(type)) {
			value = new Float(paramValue);
		} else if ("SH".equals(type)) {
			value = new Short(paramValue);
		} else if ("D".equals(type)) {
			try {
				value = DateUtils.parseDate(paramValue, new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
			} catch (Exception arg3) {
				;
			}
		} else {
			value = paramValue;
		}

		return value;
	}

	public static String setGraphXml(String sourceNode, Map<String, String> map, String xml) throws IOException {
		Document doc = Dom4jUtil.loadXml(xml);
		Element root = doc.getRootElement();
		Element node = (Element) root.selectSingleNode("//bg:Gateway[@id=\'" + sourceNode + "\']");
		Element portsEl = node.element("ports");
		List portList = portsEl.elements();

		for (int i = 0; i < portList.size(); ++i) {
			Element portEl = (Element) portList.get(i);
			if (portEl.attribute("x") != null || portEl.attribute("y") != null) {
				String id = portEl.attributeValue("id");
				Element outNode = (Element) root.selectSingleNode("//bg:SequenceFlow[@startPort=\'" + id + "\']");
				if (outNode != null) {
					String outPort = outNode.attributeValue("endPort");
					Element tmpNode = (Element) root.selectSingleNode("//ciied:Port[@id=\'" + outPort + "\']");
					Element taskNode = tmpNode.getParent().getParent();
					String taskId = taskNode.attributeValue("id");
					Element conditionEl = outNode.element("Condition");
					if (conditionEl != null) {
						outNode.remove(conditionEl);
					}

					if (map.containsKey(taskId)) {
						String condition = (String) map.get(taskId);
						Element elAdd = outNode.addElement("Condition");
						elAdd.addText(condition);
					}
				}
			}
		}

		return doc.asXML();
	}

	public static String getStrByRule(String rule, Map<String, Object> map) {
		Pattern regex = Pattern.compile("\\{(.*?)\\}", 98);
		Matcher matcher = regex.matcher(rule);

		while (matcher.find()) {
			String tag = matcher.group(0);
			String name = matcher.group(1);
			Object value = map.get(name);
			if (BeanUtils.isEmpty(value)) {
				rule = rule.replace(tag, "");
			} else {
				rule = rule.replace(tag, value.toString());
			}
		}

		return rule;
	}

	public static String getTitleByRule(String titleRule, Map<String, Object> map) {
		if (StringUtil.isEmpty(titleRule)) {
			return "";
		} else {
			Pattern regex = Pattern.compile("\\{(.*?)\\}", 98);
			Matcher matcher = regex.matcher(titleRule);

			while (matcher.find()) {
				String tag = matcher.group(0);
				String rule = matcher.group(1);
				String[] aryRule = rule.split(":");
				String name = "";
				if (aryRule.length == 1) {
					name = rule;
				} else {
					name = aryRule[1];
				}

				if (map.containsKey(name)) {
					Object obj = map.get(name);
					if (BeanUtils.isNotEmpty(obj)) {
						try {
							titleRule = titleRule.replace(tag, obj.toString());
						} catch (Exception arg9) {
							titleRule = titleRule.replace(tag, "");
						}
					} else {
						titleRule = titleRule.replace(tag, "");
					}
				} else {
					titleRule = titleRule.replace(tag, "");
				}
			}

			return titleRule;
		}
	}

	public static boolean isMultiTask(DelegateTask delegateTask) {
		ActivityExecution execution = (ActivityExecution) delegateTask.getExecution();
		String multiInstance = (String) execution.getActivity().getProperty("multiInstance");
		return multiInstance != null;
	}

	public static boolean isMuiltiExcetion(ExecutionEntity execution) {
		String multiInstance = (String) execution.getActivity().getProperty("multiInstance");
		return multiInstance != null;
	}

	public static int isHandlerValidNoCmd(String handler, Class<?>[] parameterTypes) {
		if (handler.indexOf(".") == -1) {
			return -1;
		} else {
			String[] aryHandler = handler.split("[.]");
			String beanId = aryHandler[0];
			String method = aryHandler[1];
			Object serviceBean = null;

			try {
				serviceBean = AppUtil.getBean(beanId);
			} catch (Exception arg8) {
				return -2;
			}

			if (serviceBean == null) {
				return -2;
			} else {
				try {
					Method e = serviceBean.getClass().getMethod(method, parameterTypes);
					return e != null ? 0 : -3;
				} catch (NoSuchMethodException arg6) {
					return -3;
				} catch (Exception arg7) {
					return -4;
				}
			}
		}
	}

	
}