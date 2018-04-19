package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tConversationAssociation")
public class ConversationAssociation extends BaseElement {
	@XmlAttribute(required = true)
	protected QName innerConversationNodeRef;
	@XmlAttribute(required = true)
	protected QName outerConversationNodeRef;

	public QName getInnerConversationNodeRef() {
		return this.innerConversationNodeRef;
	}

	public void setInnerConversationNodeRef(QName value) {
		this.innerConversationNodeRef = value;
	}

	public QName getOuterConversationNodeRef() {
		return this.outerConversationNodeRef;
	}

	public void setOuterConversationNodeRef(QName value) {
		this.outerConversationNodeRef = value;
	}
}