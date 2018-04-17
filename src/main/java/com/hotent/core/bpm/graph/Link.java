package com.hotent.core.bpm.graph;

import com.hotent.core.bpm.graph.Point;
import com.hotent.core.bpm.graph.Port;
import com.hotent.core.bpm.graph.Shape;
import com.hotent.core.bpm.graph.ShapeType;
import java.util.List;

public class Link {
	private ShapeType shapeType;
	private Shape startNode;
	private Shape endNode;
	private Port startPort;
	private Port endPort;
	private Point fallbackStartPoint;
	private Point fallbackEndPoint;
	private List<Point> intermediatePoints;

	public ShapeType getShapeType() {
		return this.shapeType;
	}

	public void setShapeType(ShapeType shapeType) {
		this.shapeType = shapeType;
	}

	public Shape getStartNode() {
		return this.startNode;
	}

	public void setStartNode(Shape startNode) {
		this.startNode = startNode;
	}

	public Shape getEndNode() {
		return this.endNode;
	}

	public void setEndNode(Shape endNode) {
		this.endNode = endNode;
	}

	public Port getStartPort() {
		return this.startPort;
	}

	public void setStartPort(Port startPort) {
		this.startPort = startPort;
	}

	public Port getEndPort() {
		return this.endPort;
	}

	public void setEndPort(Port endPort) {
		this.endPort = endPort;
	}

	public Point getFallbackStartPoint() {
		return this.fallbackStartPoint;
	}

	public void setFallbackStartPoint(Point fallbackStartPoint) {
		this.fallbackStartPoint = fallbackStartPoint;
	}

	public Point getFallbackEndPoint() {
		return this.fallbackEndPoint;
	}

	public void setFallbackEndPoint(Point fallbackEndPoint) {
		this.fallbackEndPoint = fallbackEndPoint;
	}

	public List<Point> getIntermediatePoints() {
		return this.intermediatePoints;
	}

	public void setIntermediatePoints(List<Point> intermediatePoints) {
		this.intermediatePoints = intermediatePoints;
	}
}