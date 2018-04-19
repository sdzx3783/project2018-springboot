package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Task;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tBusinessRuleTask")
public class BusinessRuleTask extends Task {
	@XmlAttribute
	protected String implementation;

	public String getImplementation() {
		return this.implementation == null ? "##unspecified" : this.implementation;
	}

	public void setImplementation(String value) {
		this.implementation = value;
	}
}