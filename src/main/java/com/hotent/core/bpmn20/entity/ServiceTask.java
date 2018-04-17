package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Task;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tServiceTask")
public class ServiceTask extends Task {
	@XmlAttribute
	protected String implementation;
	@XmlAttribute
	protected QName operationRef;

	public String getImplementation() {
		return this.implementation == null ? "##WebService" : this.implementation;
	}

	public void setImplementation(String value) {
		this.implementation = value;
	}

	public QName getOperationRef() {
		return this.operationRef;
	}

	public void setOperationRef(QName value) {
		this.operationRef = value;
	}
}