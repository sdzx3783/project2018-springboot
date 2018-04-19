package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.LaneSet;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tLane", propOrder = {"partitionElement", "flowNodeRef", "childLaneSet"})
public class Lane extends BaseElement {
	protected BaseElement partitionElement;
	@XmlElementRef(name = "flowNodeRef", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<Object>> flowNodeRef;
	protected LaneSet childLaneSet;
	@XmlAttribute
	protected String name;
	@XmlAttribute
	protected QName partitionElementRef;

	public BaseElement getPartitionElement() {
		return this.partitionElement;
	}

	public void setPartitionElement(BaseElement value) {
		this.partitionElement = value;
	}

	public List<JAXBElement<Object>> getFlowNodeRef() {
		if (this.flowNodeRef == null) {
			this.flowNodeRef = new ArrayList();
		}

		return this.flowNodeRef;
	}

	public LaneSet getChildLaneSet() {
		return this.childLaneSet;
	}

	public void setChildLaneSet(LaneSet value) {
		this.childLaneSet = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public QName getPartitionElementRef() {
		return this.partitionElementRef;
	}

	public void setPartitionElementRef(QName value) {
		this.partitionElementRef = value;
	}
}