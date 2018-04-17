package com.hotent.core.bpmn20.entity.omgdc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Bounds")
public class Bounds {
	@XmlAttribute(required = true)
	protected double x;
	@XmlAttribute(required = true)
	protected double y;
	@XmlAttribute(required = true)
	protected double width;
	@XmlAttribute(required = true)
	protected double height;

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

	public double getWidth() {
		return this.width;
	}

	public void setWidth(double value) {
		this.width = value;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double value) {
		this.height = value;
	}
}