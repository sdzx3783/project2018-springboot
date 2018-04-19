package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tMessageFlowAssociation")
public class MessageFlowAssociation extends BaseElement {
	@XmlAttribute(required = true)
	protected QName innerMessageFlowRef;
	@XmlAttribute(required = true)
	protected QName outerMessageFlowRef;

	public QName getInnerMessageFlowRef() {
		return this.innerMessageFlowRef;
	}

	public void setInnerMessageFlowRef(QName value) {
		this.innerMessageFlowRef = value;
	}

	public QName getOuterMessageFlowRef() {
		return this.outerMessageFlowRef;
	}

	public void setOuterMessageFlowRef(QName value) {
		this.outerMessageFlowRef = value;
	}
}