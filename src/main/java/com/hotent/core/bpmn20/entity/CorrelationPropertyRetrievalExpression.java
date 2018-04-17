package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.FormalExpression;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCorrelationPropertyRetrievalExpression", propOrder = {"messagePath"})
public class CorrelationPropertyRetrievalExpression extends BaseElement {
	@XmlElement(required = true)
	protected FormalExpression messagePath;
	@XmlAttribute(required = true)
	protected QName messageRef;

	public FormalExpression getMessagePath() {
		return this.messagePath;
	}

	public void setMessagePath(FormalExpression value) {
		this.messagePath = value;
	}

	public QName getMessageRef() {
		return this.messageRef;
	}

	public void setMessageRef(QName value) {
		this.messageRef = value;
	}
}