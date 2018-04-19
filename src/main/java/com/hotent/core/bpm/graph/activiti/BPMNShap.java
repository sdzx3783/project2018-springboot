package com.hotent.core.bpm.graph.activiti;

import com.hotent.core.bpm.graph.activiti.BPMNShapType;
import java.util.Properties;

public class BPMNShap {
	private BPMNShapType type;
	private String name;
	private String bpmnElement;
	private Boolean isHorizontal;
	private Boolean isExpanded;
	private Boolean isMarkerVisible;
	private Boolean isMessageVisible;
	private String participantBandKind;
	private String choreographyActivityShape;
	private double x;
	private double y;
	private double width;
	private double height;
	private Properties properties;

	public BPMNShapType getType() {
		return this.type;
	}

	public void setType(BPMNShapType type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBpmnElement() {
		return this.bpmnElement;
	}

	public void setBpmnElement(String bpmnElement) {
		this.bpmnElement = bpmnElement;
	}

	public Boolean isHorizontal() {
		return this.isHorizontal;
	}

	public void setHorizontal(Boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}

	public Boolean isExpanded() {
		return this.isExpanded;
	}

	public void setExpanded(Boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public Boolean isMarkerVisible() {
		return this.isMarkerVisible;
	}

	public void setMarkerVisible(Boolean isMarkerVisible) {
		this.isMarkerVisible = isMarkerVisible;
	}

	public Boolean isMessageVisible() {
		return this.isMessageVisible;
	}

	public void setMessageVisible(Boolean isMessageVisible) {
		this.isMessageVisible = isMessageVisible;
	}

	public String getParticipantBandKind() {
		return this.participantBandKind;
	}

	public void setParticipantBandKind(String participantBandKind) {
		this.participantBandKind = participantBandKind;
	}

	public String getChoreographyActivityShape() {
		return this.choreographyActivityShape;
	}

	public void setChoreographyActivityShape(String choreographyActivityShape) {
		this.choreographyActivityShape = choreographyActivityShape;
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

	public double getWidth() {
		return this.width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public Properties getProperties() {
		return this.properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String toString() {
		return "BPMNShap [type=" + this.type + ", name=" + this.name + ", bpmnElement=" + this.bpmnElement
				+ ", isHorizontal=" + this.isHorizontal + ", isExpanded=" + this.isExpanded + ", isMarkerVisible="
				+ this.isMarkerVisible + ", isMessageVisible=" + this.isMessageVisible + ", participantBandKind="
				+ this.participantBandKind + ", choreographyActivityShape=" + this.choreographyActivityShape + ", x="
				+ this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", properties="
				+ this.properties + "]";
	}
}