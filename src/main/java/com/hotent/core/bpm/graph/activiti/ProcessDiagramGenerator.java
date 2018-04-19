package com.hotent.core.bpm.graph.activiti;

import com.hotent.core.api.bpm.model.IBpmProStatus;
import com.hotent.core.bpm.graph.activiti.BPMNEdge;
import com.hotent.core.bpm.graph.activiti.BPMNShap;
import com.hotent.core.bpm.graph.activiti.BPMNShapType;
import com.hotent.core.bpm.graph.activiti.DirectionType;
import com.hotent.core.bpm.graph.activiti.GraphDrawInstruction;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramCanvas;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.1;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.10;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.11;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.12;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.13;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.14;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.15;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.16;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.17;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.18;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.19;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.2;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.20;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.3;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.4;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.5;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.6;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.7;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.8;
import com.hotent.core.bpm.graph.activiti.ProcessDiagramGenerator.9;
import com.hotent.core.util.Dom4jUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.dom4j.Document;
import org.dom4j.Element;

public class ProcessDiagramGenerator {
	protected static final Map<BPMNShapType, GraphDrawInstruction> graphDrawInstructions = new HashMap();

	public static ProcessDiagramCanvas initProcessDiagramCanvas(List<BPMNShap> shaps, List<BPMNEdge> edges) {
		Double[] points = caculateCanvasSize(shaps, edges);
		double shiftX = points[0].getX() < 0.0D ? points[0].getX() : 0.0D;
		double shiftY = points[0].getY() < 0.0D ? points[0].getY() : 0.0D;
		shiftProcessDefinition(shaps, edges, shiftX, shiftY);
		int width = (int) Math.round(points[1].getX() + 10.0D - shiftX);
		int height = (int) Math.round(points[1].getY() + 10.0D - shiftY);
		int minX = (int) Math.round(points[0].getX() - shiftX);
		int minY = (int) Math.round(points[0].getY() - shiftY);
		return new ProcessDiagramCanvas(width, height, minX, minY);
	}

	public static InputStream generatePngDiagram(String bpmnXml) {
		return generateDiagram(bpmnXml, "png", Collections.emptyList());
	}

	public static InputStream generateJpgDiagram(String bpmnXml) {
		return generateDiagram(bpmnXml, "jpg", Collections.emptyList());
	}

	public static ProcessDiagramCanvas generateDiagram(String bpmnXml, List<String> highLightedActivities) {
		List bpmnShaps = extractBPMNShap(bpmnXml);
		List bpmnEdges = extractBPMNEdge(bpmnXml);
		ProcessDiagramCanvas processDiagramCanvas = initProcessDiagramCanvas(bpmnShaps, bpmnEdges);
		Iterator i$ = bpmnShaps.iterator();

		while (i$.hasNext()) {
			BPMNShap bpmnShap = (BPMNShap) i$.next();
			drawActivity(processDiagramCanvas, bpmnShap, highLightedActivities);
		}

		drawSequenceFlows(processDiagramCanvas, bpmnEdges);
		return processDiagramCanvas;
	}

	public static ProcessDiagramCanvas generateDiagram(Map<String, String> highLightedActivities, String bpmnXml) {
		List bpmnEdges = extractBPMNEdge(bpmnXml);
		List bpmnShaps = extractBPMNShap(bpmnXml);
		ProcessDiagramCanvas processDiagramCanvas = initProcessDiagramCanvas(bpmnShaps, bpmnEdges);
		Iterator i$ = bpmnShaps.iterator();

		while (i$.hasNext()) {
			BPMNShap bpmnShap = (BPMNShap) i$.next();
			drawActivity(processDiagramCanvas, highLightedActivities, bpmnShap);
		}

		drawSequenceFlows(processDiagramCanvas, bpmnEdges);
		return processDiagramCanvas;
	}

	public static InputStream generateDiagram(String bpmnXml, Map<String, String> highLightedActivities,
			String imageType) {
		return generateDiagram(highLightedActivities, bpmnXml).generateImage(imageType);
	}

	public static InputStream generateDiagram(String bpmnXml, String imageType, List<String> highLightedActivities) {
		return generateDiagram(bpmnXml, highLightedActivities).generateImage(imageType);
	}

	public static void drawActivity(ProcessDiagramCanvas processDiagramCanvas, BPMNShap bpmnShap,
			List<String> highLightedActivities) {
		GraphDrawInstruction drawInstruction = (GraphDrawInstruction) graphDrawInstructions.get(bpmnShap.getType());
		if (drawInstruction != null) {
			drawInstruction.draw(processDiagramCanvas, bpmnShap);
			boolean multiInstanceSequential = false;
			boolean multiInstanceParallel = false;
			boolean collapsed = false;
			Properties properties = bpmnShap.getProperties();
			String isSequential = null;
			if (properties != null) {
				isSequential = (String) bpmnShap.getProperties().get("isSequential");
			}

			if (isSequential != null) {
				if ("true".equals(isSequential)) {
					multiInstanceSequential = true;
				} else {
					multiInstanceParallel = true;
				}
			}

			Boolean expanded = bpmnShap.isExpanded();
			if (expanded != null) {
				collapsed = !expanded.booleanValue();
			}

			processDiagramCanvas.drawActivityMarkers((int) Math.round(bpmnShap.getX()),
					(int) Math.round(bpmnShap.getY()), (int) Math.round(bpmnShap.getWidth()),
					(int) Math.round(bpmnShap.getHeight()), multiInstanceSequential, multiInstanceParallel, collapsed);
			if (highLightedActivities.contains(bpmnShap.getBpmnElement())) {
				drawHighLight(processDiagramCanvas, bpmnShap);
			}
		}

	}

	public static void drawActivity(ProcessDiagramCanvas processDiagramCanvas,
			Map<String, String> highLightedActivities, BPMNShap bpmnShap) {
		GraphDrawInstruction drawInstruction = (GraphDrawInstruction) graphDrawInstructions.get(bpmnShap.getType());
		if (drawInstruction != null) {
			drawInstruction.draw(processDiagramCanvas, bpmnShap);
			boolean multiInstanceSequential = false;
			boolean multiInstanceParallel = false;
			boolean collapsed = false;
			Properties properties = bpmnShap.getProperties();
			String isSequential = null;
			if (properties != null) {
				isSequential = (String) bpmnShap.getProperties().get("isSequential");
			}

			if (isSequential != null) {
				if ("true".equals(isSequential)) {
					multiInstanceSequential = true;
				} else {
					multiInstanceParallel = true;
				}
			}

			Boolean expanded = bpmnShap.isExpanded();
			if (expanded != null) {
				collapsed = !expanded.booleanValue();
			}

			processDiagramCanvas.drawActivityMarkers((int) Math.round(bpmnShap.getX()),
					(int) Math.round(bpmnShap.getY()), (int) Math.round(bpmnShap.getWidth()),
					(int) Math.round(bpmnShap.getHeight()), multiInstanceSequential, multiInstanceParallel, collapsed);
			if (highLightedActivities.containsKey(bpmnShap.getBpmnElement())) {
				String color = (String) highLightedActivities.get(bpmnShap.getBpmnElement());
				drawHighLight(processDiagramCanvas, bpmnShap, color);
			}
		}

	}

	public static void drawSequenceFlows(ProcessDiagramCanvas processDiagramCanvas, List<BPMNEdge> bpmnEdges) {
		Iterator i$ = bpmnEdges.iterator();

		while (i$.hasNext()) {
			BPMNEdge bpmnEdge = (BPMNEdge) i$.next();
			processDiagramCanvas.drawSequenceflowWidthLabel(bpmnEdge);
		}

	}

	private static Map<String, Short> getHighLightedMap(List<IBpmProStatus> highLightedActivities) {
		HashMap map = new HashMap();
		Iterator i$ = highLightedActivities.iterator();

		while (i$.hasNext()) {
			IBpmProStatus status = (IBpmProStatus) i$.next();
			map.put(status.getNodeid(), status.getStatus());
		}

		return map;
	}

	private static void drawHighLight(ProcessDiagramCanvas processDiagramCanvas, BPMNShap bpmnShap) {
		processDiagramCanvas.drawHighLight((int) Math.round(bpmnShap.getX()), (int) Math.round(bpmnShap.getY()),
				(int) Math.round(bpmnShap.getWidth()), (int) Math.round(bpmnShap.getHeight()));
	}

	private static void drawHighLight(ProcessDiagramCanvas processDiagramCanvas, BPMNShap bpmnShap, String color) {
		processDiagramCanvas.drawHighLight((int) Math.round(bpmnShap.getX()), (int) Math.round(bpmnShap.getY()),
				(int) Math.round(bpmnShap.getWidth()), (int) Math.round(bpmnShap.getHeight()), color);
	}

	private static void drawHighLight(ProcessDiagramCanvas processDiagramCanvas, BPMNShap bpmnShap, Short status) {
		processDiagramCanvas.drawHighLight((int) Math.round(bpmnShap.getX()), (int) Math.round(bpmnShap.getY()),
				(int) Math.round(bpmnShap.getWidth()), (int) Math.round(bpmnShap.getHeight()), status);
	}

	private static FontMetrics getFontMetrics() {
		BufferedImage processDiagram = new BufferedImage(2, 2, 2);
		Graphics2D g = processDiagram.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setPaint(Color.black);
		Font font = new Font("宋体", 1, 12);
		g.setFont(font);
		FontMetrics fontMetrics = g.getFontMetrics();
		return fontMetrics;
	}

	public static Double[] caculateCanvasSize(List<BPMNShap> shaps, List<BPMNEdge> edges) {
		double minX = java.lang.Double.MAX_VALUE;
		double minY = java.lang.Double.MAX_VALUE;
		double maxX = 0.0D;
		double maxY = 0.0D;
		Iterator i$ = shaps.iterator();

		while (i$.hasNext()) {
			BPMNShap edge = (BPMNShap) i$.next();
			if (edge.getX() < minX) {
				minX = edge.getX();
			}

			if (edge.getY() < minY) {
				minY = edge.getY();
			}

			if (edge.getX() + edge.getWidth() > maxX) {
				maxX = edge.getX() + edge.getWidth();
			}

			if (edge.getY() + edge.getHeight() > maxY) {
				maxY = edge.getY() + edge.getHeight();
			}
		}

		i$ = edges.iterator();

		while (i$.hasNext()) {
			BPMNEdge edge1 = (BPMNEdge) i$.next();
			Iterator label = edge1.getPoints().iterator();

			Double midPoint;
			while (label.hasNext()) {
				midPoint = (Double) label.next();
				if (midPoint.getX() < minX) {
					minX = midPoint.getX();
				}

				if (midPoint.getY() < minY) {
					minY = midPoint.getY();
				}

				if (midPoint.getX() > maxX) {
					maxX = midPoint.getX();
				}

				if (midPoint.getY() > maxY) {
					maxY = midPoint.getY();
				}
			}

			String label1 = edge1.getName() == null ? "" : edge1.getName();
			midPoint = edge1.getMidpoint();
			DirectionType directionType = edge1.getDirection();
			FontMetrics fontMetrics = getFontMetrics();
			double labelMinX;
			double labelMinY;
			if (directionType == DirectionType.UpToDown) {
				labelMinX = midPoint.getX() + (double) (fontMetrics.getHeight() / 2);
				labelMinY = midPoint.getY();
			} else if (directionType == DirectionType.DownToUp) {
				labelMinX = midPoint.getX() - (double) fontMetrics.stringWidth(label1)
						- (double) (fontMetrics.getHeight() / 2);
				labelMinY = midPoint.getY() - (double) (fontMetrics.getHeight() / 2) - (double) fontMetrics.getHeight();
			} else if (directionType == DirectionType.LeftToRight) {
				labelMinX = midPoint.getX() - (double) (fontMetrics.stringWidth(label1) / 2);
				labelMinY = midPoint.getY();
			} else {
				labelMinX = (double) (fontMetrics.stringWidth(label1) / 2);
				labelMinY = midPoint.getY() + (double) fontMetrics.getHeight() - (double) fontMetrics.getHeight();
			}

			double labelMaxX = labelMinX + (double) fontMetrics.stringWidth(label1);
			double labelMaxY = labelMinY + (double) fontMetrics.getHeight();
			if (labelMinX < minX) {
				minX = labelMinX;
			}

			if (labelMinY < minY) {
				minY = labelMinY;
			}

			if (labelMaxX > maxX) {
				maxX = labelMaxX;
			}

			if (labelMaxY > maxY) {
				maxY = labelMaxY;
			}
		}

		return new Double[]{new Double(minX, minY), new Double(maxX, maxY)};
	}

	private static void shiftProcessDefinition(List<BPMNShap> shaps, List<BPMNEdge> edges, double x, double y) {
		Iterator i$ = shaps.iterator();

		while (i$.hasNext()) {
			BPMNShap edge = (BPMNShap) i$.next();
			edge.setX(edge.getX() - x);
			edge.setY(edge.getY() - y);
		}

		BPMNEdge edge1;
		for (i$ = edges.iterator(); i$.hasNext(); edge1.getMidpoint().y = edge1.getMidpoint().getY() - y) {
			edge1 = (BPMNEdge) i$.next();

			Double point;
			for (Iterator i$1 = edge1.getPoints().iterator(); i$1.hasNext(); point.y = point.getY() - y) {
				point = (Double) i$1.next();
				point.x = point.getX() - x;
			}

			edge1.getMidpoint().x = edge1.getMidpoint().getX() - x;
		}

	}

	public static List<BPMNShap> extractBPMNShap(String bpmnXml) {
		bpmnXml = bpmnXml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document doc = Dom4jUtil.loadXml(bpmnXml);
		Element root = doc.getRootElement();
		List shaps = root.selectNodes("//bpmndi:BPMNShape");
		ArrayList bpmnShaps = new ArrayList();
		Iterator i$ = shaps.iterator();

		while (i$.hasNext()) {
			Element shap = (Element) i$.next();
			BPMNShap bpmnShap = new BPMNShap();
			bpmnShap.setBpmnElement(shap.attributeValue("bpmnElement"));
			bpmnShap.setChoreographyActivityShape(shap.attributeValue("choreographyActivityShape"));
			bpmnShap.setHorizontal(getBooleanAttr(shap, "isHorizontal"));
			bpmnShap.setExpanded(getBooleanAttr(shap, "isExpanded"));
			bpmnShap.setMarkerVisible(getBooleanAttr(shap, "isMarkerVisible"));
			bpmnShap.setMessageVisible(getBooleanAttr(shap, "isMessageVisible"));
			bpmnShap.setParticipantBandKind(shap.attributeValue("participantBandKind"));
			Element bound = (Element) shap.selectSingleNode("./omgdc:Bounds");
			bpmnShap.setX(java.lang.Double.parseDouble(bound.attributeValue("x")));
			bpmnShap.setY(java.lang.Double.parseDouble(bound.attributeValue("y")));
			bpmnShap.setWidth(java.lang.Double.parseDouble(bound.attributeValue("width")));
			bpmnShap.setHeight(java.lang.Double.parseDouble(bound.attributeValue("height")));
			Element component = (Element) root.selectSingleNode("//*[@id=\'" + bpmnShap.getBpmnElement() + "\']");
			if (component != null) {
				BPMNShapType type = getBPMNShapType(component);
				bpmnShap.setType(type);
				bpmnShap.setName(component.attributeValue("name"));
				setBPMNShapProperties(component, bpmnShap);
				bpmnShaps.add(bpmnShap);
			}
		}

		return bpmnShaps;
	}

	public static List<BPMNEdge> extractBPMNEdge(String bpmnXml) {
		ArrayList bpmnEdges = new ArrayList();
		bpmnXml = bpmnXml.replace("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
		Document doc = Dom4jUtil.loadXml(bpmnXml);
		Element root = doc.getRootElement();
		List edges = root.selectNodes("//bpmndi:BPMNEdge");
		Iterator i$ = edges.iterator();

		while (i$.hasNext()) {
			Element edge = (Element) i$.next();
			BPMNEdge bpmnEdge = new BPMNEdge();
			ArrayList points = new ArrayList();
			List waypoints = edge.selectNodes("./omgdi:waypoint");
			Iterator bpmnElement = waypoints.iterator();

			Element component;
			double x;
			double y;
			while (bpmnElement.hasNext()) {
				component = (Element) bpmnElement.next();
				x = java.lang.Double.parseDouble(component.attributeValue("x"));
				y = java.lang.Double.parseDouble(component.attributeValue("y"));
				Double directionType = new Double(x, y);
				points.add(directionType);
			}

			bpmnEdge.setPoints(points);
			String arg23 = edge.attributeValue("bpmnElement");
			component = (Element) root.selectSingleNode("//sequenceFlow[@id=\'" + arg23 + "\']");
			bpmnEdge.setName(component.attributeValue("name"));
			x = 0.0D;
			y = 0.0D;
			ArrayList lens = new ArrayList();

			for (int halfLen = 1; halfLen < points.size(); ++halfLen) {
				lens.add(java.lang.Double.valueOf(Math
						.abs(((Double) points.get(halfLen - 1)).getX() - ((Double) points.get(halfLen)).getX())
						+ Math.abs(((Double) points.get(halfLen - 1)).getY() - ((Double) points.get(halfLen)).getY())));
			}

			double arg25 = 0.0D;

			double len;
			for (Iterator accumulativeLen = lens.iterator(); accumulativeLen.hasNext(); arg25 += len) {
				len = ((java.lang.Double) accumulativeLen.next()).doubleValue();
			}

			arg25 /= 2.0D;
			double arg26 = 0.0D;

			int i;
			for (i = 0; i < lens.size(); ++i) {
				arg26 += ((java.lang.Double) lens.get(i)).doubleValue();
				if (arg26 > arg25) {
					break;
				}
			}

			DirectionType arg24;
			if (((Double) points.get(i)).getX() == ((Double) points.get(i + 1)).getX()) {
				if (((Double) points.get(i)).getY() < ((Double) points.get(i + 1)).getY()) {
					y = arg25 - arg26 + ((Double) points.get(i + 1)).getY();
					arg24 = DirectionType.UpToDown;
				} else {
					y = arg26 - arg25 + ((Double) points.get(i + 1)).getY();
					arg24 = DirectionType.DownToUp;
				}

				x = ((Double) points.get(i)).getX();
			} else {
				if (((Double) points.get(i)).getX() < ((Double) points.get(i + 1)).getX()) {
					x = arg25 - arg26 + ((Double) points.get(i + 1)).getX();
					arg24 = DirectionType.LeftToRight;
				} else {
					x = arg26 - arg25 + ((Double) points.get(i + 1)).getX();
					arg24 = DirectionType.RightToLef;
				}

				y = ((Double) points.get(i)).getY();
			}

			Double midpoint = new Double(x, y);
			bpmnEdge.setMidpoint(midpoint);
			bpmnEdge.setDirection(arg24);
			bpmnEdges.add(bpmnEdge);
		}

		return bpmnEdges;
	}

	private static BPMNShap setBPMNShapProperties(Element component, BPMNShap bpmnShap) {
		BPMNShapType type = bpmnShap.getType();
		Properties properties = bpmnShap.getProperties();
		if (properties == null) {
			properties = new Properties();
		}

		Element errorEventDefinition;
		String errorRef;
		if (type == BPMNShapType.Task || type == BPMNShapType.ScriptTask || type == BPMNShapType.ServiceTask
				|| type == BPMNShapType.BusinessRuleTask || type == BPMNShapType.ManualTask
				|| type == BPMNShapType.UserTask || type == BPMNShapType.CallActivity
				|| type == BPMNShapType.SubProcess) {
			errorEventDefinition = (Element) component.selectSingleNode("./multiInstanceLoopCharacteristics");
			if (errorEventDefinition != null) {
				errorRef = errorEventDefinition.attributeValue("isSequential");
				properties.put("isSequential", errorRef);
			}
		}

		if (type == BPMNShapType.ErrorEvent) {
			errorEventDefinition = (Element) component.selectSingleNode("errorEventDefinition");
			errorRef = errorEventDefinition.attributeValue("errorRef");
			properties.put("errorRef", errorRef);
		}

		bpmnShap.setProperties(properties);
		return bpmnShap;
	}

	private static Boolean getBooleanAttr(Element element, String attr) {
		String attrVal = element.attributeValue(attr);
		return attrVal != null ? Boolean.valueOf(attrVal.equalsIgnoreCase("true")) : null;
	}

	public static BPMNShapType getBPMNShapType(Element component) {
		BPMNShapType retVal = BPMNShapType.UnknowType;
		if (component.getName().equals("startEvent")) {
			retVal = BPMNShapType.StartEvent;
		} else if (component.getName().equals("endEvent")) {
			Element id = (Element) component.selectSingleNode("errorEventDefinition");
			if (id == null) {
				retVal = BPMNShapType.EndEvent;
			} else {
				retVal = BPMNShapType.ErrorEvent;
			}
		} else if (component.getName().equals("exclusiveGateway")) {
			retVal = BPMNShapType.ExclusiveGateway;
		} else if (component.getName().equals("inclusiveGateway")) {
			retVal = BPMNShapType.InclusiveGateway;
		} else if (component.getName().equals("parallelGateway")) {
			retVal = BPMNShapType.ParallelGateway;
		} else if (component.getName().equals("scriptTask")) {
			retVal = BPMNShapType.ScriptTask;
		} else if (component.getName().equals("serviceTask")) {
			retVal = BPMNShapType.ServiceTask;
		} else if (component.getName().equals("businessRuleTask")) {
			retVal = BPMNShapType.BusinessRuleTask;
		} else if (component.getName().equals("task")) {
			retVal = BPMNShapType.Task;
		} else if (component.getName().equals("manualTask")) {
			retVal = BPMNShapType.ManualTask;
		} else if (component.getName().equals("userTask")) {
			retVal = BPMNShapType.UserTask;
		} else if (component.getName().equals("sendTask")) {
			retVal = BPMNShapType.SendTask;
		} else if (component.getName().equals("receiveTask")) {
			retVal = BPMNShapType.ReceiveTask;
		} else if (component.getName().equals("subProcess")) {
			retVal = BPMNShapType.SubProcess;
		} else if (component.getName().equals("callActivity")) {
			retVal = BPMNShapType.CallActivity;
		} else if (component.getName().equals("intermediateCatchEvent")) {
			retVal = BPMNShapType.IntermediateCatchEvent;
		} else if (component.getName().equals("adHocSubProcess")) {
			retVal = BPMNShapType.ComplexGateway;
		} else if (component.getName().equals("eventBasedGateway")) {
			retVal = BPMNShapType.EventBasedGateway;
		} else if (component.getName().equals("transaction")) {
			retVal = BPMNShapType.Transaction;
		} else {
			Element shap;
			String id1;
			if (component.getName().equals("participant")) {
				id1 = component.attributeValue("id");
				String root = component.attributeValue("processRef");
				shap = component.getDocument().getRootElement();
				Element isHorizontal = (Element) shap.selectSingleNode("//*[@id=\'" + root + "\']");
				if (isHorizontal.element("laneSet") != null) {
					Element shap1 = (Element) shap.selectSingleNode("//*[@bpmnElement=\'" + id1 + "\']");
					String isHorizontal1 = shap1.attributeValue("isHorizontal");
					if (isHorizontal1 != null && isHorizontal1.equalsIgnoreCase("false")) {
						retVal = BPMNShapType.VPool;
					} else {
						retVal = BPMNShapType.HPool;
					}
				}
			} else if (component.getName().equals("lane")) {
				id1 = component.attributeValue("id");
				Element root1 = component.getDocument().getRootElement();
				shap = (Element) root1.selectSingleNode("//*[@bpmnElement=\'" + id1 + "\']");
				String isHorizontal2 = shap.attributeValue("isHorizontal");
				if (isHorizontal2 != null && isHorizontal2.equalsIgnoreCase("false")) {
					retVal = BPMNShapType.VLane;
				} else {
					retVal = BPMNShapType.HLane;
				}
			}
		}

		return retVal;
	}

	static {
      graphDrawInstructions.put(BPMNShapType.StartEvent, new 1());
      graphDrawInstructions.put(BPMNShapType.ErrorEvent, new 2());
      graphDrawInstructions.put(BPMNShapType.EndEvent, new 3());
      graphDrawInstructions.put(BPMNShapType.CancelEvent, new 4());
      graphDrawInstructions.put(BPMNShapType.Task, new 5());
      graphDrawInstructions.put(BPMNShapType.UserTask, new 6());
      graphDrawInstructions.put(BPMNShapType.ScriptTask, new 7());
      graphDrawInstructions.put(BPMNShapType.ServiceTask, new 8());
      graphDrawInstructions.put(BPMNShapType.ReceiveTask, new 9());
      graphDrawInstructions.put(BPMNShapType.SendTask, new 10());
      graphDrawInstructions.put(BPMNShapType.ManualTask, new 11());
      graphDrawInstructions.put(BPMNShapType.ExclusiveGateway, new 12());
      graphDrawInstructions.put(BPMNShapType.InclusiveGateway, new 13());
      graphDrawInstructions.put(BPMNShapType.ParallelGateway, new 14());
      graphDrawInstructions.put(BPMNShapType.SubProcess, new 15());
      graphDrawInstructions.put(BPMNShapType.CallActivity, new 16());
      graphDrawInstructions.put(BPMNShapType.HPool, new 17());
      graphDrawInstructions.put(BPMNShapType.HLane, new 18());
      graphDrawInstructions.put(BPMNShapType.VPool, new 19());
      graphDrawInstructions.put(BPMNShapType.VLane, new 20());
   }
}