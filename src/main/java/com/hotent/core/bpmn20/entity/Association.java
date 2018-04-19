package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Artifact;
import com.hotent.core.bpmn20.entity.AssociationDirection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tAssociation")
public class Association extends Artifact {
	@XmlAttribute(required = true)
	protected QName sourceRef;
	@XmlAttribute(required = true)
	protected QName targetRef;
	@XmlAttribute
	protected AssociationDirection associationDirection;

	public QName getSourceRef() {
		return this.sourceRef;
	}

	public void setSourceRef(QName value) {
		this.sourceRef = value;
	}

	public QName getTargetRef() {
		return this.targetRef;
	}

	public void setTargetRef(QName value) {
		this.targetRef = value;
	}

	public AssociationDirection getAssociationDirection() {
		return this.associationDirection == null ? AssociationDirection.NONE : this.associationDirection;
	}

	public void setAssociationDirection(AssociationDirection value) {
		this.associationDirection = value;
	}
}