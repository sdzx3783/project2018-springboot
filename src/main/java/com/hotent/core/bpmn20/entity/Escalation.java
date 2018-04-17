package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.RootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tEscalation")
public class Escalation extends RootElement {
	@XmlAttribute
	protected String name;
	@XmlAttribute
	protected String escalationCode;
	@XmlAttribute
	protected QName structureRef;

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getEscalationCode() {
		return this.escalationCode;
	}

	public void setEscalationCode(String value) {
		this.escalationCode = value;
	}

	public QName getStructureRef() {
		return this.structureRef;
	}

	public void setStructureRef(QName value) {
		this.structureRef = value;
	}
}