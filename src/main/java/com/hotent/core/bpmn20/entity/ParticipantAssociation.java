package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tParticipantAssociation", propOrder = {"innerParticipantRef", "outerParticipantRef"})
public class ParticipantAssociation extends BaseElement {
	@XmlElement(required = true)
	protected QName innerParticipantRef;
	@XmlElement(required = true)
	protected QName outerParticipantRef;

	public QName getInnerParticipantRef() {
		return this.innerParticipantRef;
	}

	public void setInnerParticipantRef(QName value) {
		this.innerParticipantRef = value;
	}

	public QName getOuterParticipantRef() {
		return this.outerParticipantRef;
	}

	public void setOuterParticipantRef(QName value) {
		this.outerParticipantRef = value;
	}
}