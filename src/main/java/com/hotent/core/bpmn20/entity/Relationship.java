package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.RelationshipDirection;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tRelationship", propOrder = {"source", "target"})
public class Relationship extends BaseElement {
	@XmlElement(required = true)
	protected List<QName> source;
	@XmlElement(required = true)
	protected List<QName> target;
	@XmlAttribute(required = true)
	protected String type;
	@XmlAttribute
	protected RelationshipDirection direction;

	public List<QName> getSource() {
		if (this.source == null) {
			this.source = new ArrayList();
		}

		return this.source;
	}

	public List<QName> getTarget() {
		if (this.target == null) {
			this.target = new ArrayList();
		}

		return this.target;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String value) {
		this.type = value;
	}

	public RelationshipDirection getDirection() {
		return this.direction;
	}

	public void setDirection(RelationshipDirection value) {
		this.direction = value;
	}
}