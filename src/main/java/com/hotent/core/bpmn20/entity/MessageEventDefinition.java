package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.EventDefinition;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tMessageEventDefinition", propOrder = {"operationRef"})
public class MessageEventDefinition extends EventDefinition {
	protected QName operationRef;
	@XmlAttribute
	protected QName messageRef;

	public QName getOperationRef() {
		return this.operationRef;
	}

	public void setOperationRef(QName value) {
		this.operationRef = value;
	}

	public QName getMessageRef() {
		return this.messageRef;
	}

	public void setMessageRef(QName value) {
		this.messageRef = value;
	}
}