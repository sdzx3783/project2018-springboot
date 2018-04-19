package com.hotent.core.bpm.graph.activiti;

import com.hotent.core.bpm.graph.activiti.DirectionType;
import java.awt.geom.Point2D.Double;
import java.util.Iterator;
import java.util.List;

public class BPMNEdge {
	private List<Double> points;
	private String name;
	private Double midpoint;
	private DirectionType direction;
	private String sourceRef;
	private String targetRef;

	public List<Double> getPoints() {
		return this.points;
	}

	public void setPoints(List<Double> points) {
		this.points = points;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMidpoint() {
		return this.midpoint;
	}

	public void setMidpoint(Double midpoint) {
		this.midpoint = midpoint;
	}

	public DirectionType getDirection() {
		return this.direction;
	}

	public void setDirection(DirectionType direction) {
		this.direction = direction;
	}

	public String getSourceRef() {
		return this.sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getTargetRef() {
		return this.targetRef;
	}

	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}

	public String toString() {
		String str = "";

		Double point;
		for (Iterator i$ = this.points.iterator(); i$.hasNext(); str = str + point.getX() + ":" + point.getY() + "  ") {
			point = (Double) i$.next();
		}

		return "BPMNEdge [points=" + str + ", name=" + this.name + " <midpoint=" + this.midpoint.getX() + ":"
				+ this.midpoint.getY() + ">]";
	}
}