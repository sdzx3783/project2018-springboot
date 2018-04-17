package com.hotent.core.bpmn20.entity.omgdc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Point")
public class Point {
	@XmlAttribute(required = true)
	protected double x;
	@XmlAttribute(required = true)
	protected double y;

	public double getX() {
		return this.x;
	}

	public void setX(double value) {
		this.x = value;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double value) {
		this.y = value;
	}
}