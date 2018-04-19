package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.CallConversation;
import com.hotent.core.bpmn20.entity.Conversation;
import com.hotent.core.bpmn20.entity.CorrelationKey;
import com.hotent.core.bpmn20.entity.SubConversation;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tConversationNode", propOrder = {"participantRef", "messageFlowRef", "correlationKey"})
@XmlSeeAlso({Conversation.class, CallConversation.class, SubConversation.class})
public abstract class ConversationNode extends BaseElement {
	protected List<QName> participantRef;
	protected List<QName> messageFlowRef;
	protected List<CorrelationKey> correlationKey;
	@XmlAttribute
	protected String name;

	public List<QName> getParticipantRef() {
		if (this.participantRef == null) {
			this.participantRef = new ArrayList();
		}

		return this.participantRef;
	}

	public List<QName> getMessageFlowRef() {
		if (this.messageFlowRef == null) {
			this.messageFlowRef = new ArrayList();
		}

		return this.messageFlowRef;
	}

	public List<CorrelationKey> getCorrelationKey() {
		if (this.correlationKey == null) {
			this.correlationKey = new ArrayList();
		}

		return this.correlationKey;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
}