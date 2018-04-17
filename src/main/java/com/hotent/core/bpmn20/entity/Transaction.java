package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.SubProcess;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tTransaction")
public class Transaction extends SubProcess {
	@XmlAttribute
	protected String method;

	public String getMethod() {
		return this.method == null ? "##Compensate" : this.method;
	}

	public void setMethod(String value) {
		this.method = value;
	}
}