package com.hotent.core.bpm.graph;

import com.hotent.core.bpm.graph.PortType;

public class Port {
	private PortType portType;
	private double x;
	private double y;
	private double verticalOffset;
	private double horizontalOffset;
	private String nodePartReference;
	private boolean clipOnShape;

	public Port() {
	}

	public Port(PortType portType, double x, double y, double horizontalOffset, double verticalOffset,
			String nodePartReference, boolean clipOnShape) {
		this.portType = portType;
		this.x = x;
		this.y = y;
		this.verticalOffset = verticalOffset;
		this.horizontalOffset = horizontalOffset;
		this.nodePartReference = nodePartReference;
		this.clipOnShape = clipOnShape;
	}

	public PortType getPortType() {
		return this.portType;
	}

	public void setPortType(PortType portType) {
		this.portType = portType;
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVerticalOffset() {
		return this.verticalOffset;
	}

	public void setVerticalOffset(double verticalOffset) {
		this.verticalOffset = verticalOffset;
	}

	public double getHorizontalOffset() {
		return this.horizontalOffset;
	}

	public void setHorizontalOffset(double horizontalOffset) {
		this.horizontalOffset = horizontalOffset;
	}

	public String getNodePartReference() {
		return this.nodePartReference;
	}

	public void setNodePartReference(String nodePartReference) {
		this.nodePartReference = nodePartReference;
	}

	public boolean isClipOnShape() {
		return this.clipOnShape;
	}

	public void setClipOnShape(boolean clipOnShape) {
		this.clipOnShape = clipOnShape;
	}
}