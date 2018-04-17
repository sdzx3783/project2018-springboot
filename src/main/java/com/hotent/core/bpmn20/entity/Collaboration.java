package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Artifact;
import com.hotent.core.bpmn20.entity.Choreography;
import com.hotent.core.bpmn20.entity.ConversationAssociation;
import com.hotent.core.bpmn20.entity.ConversationLink;
import com.hotent.core.bpmn20.entity.ConversationNode;
import com.hotent.core.bpmn20.entity.CorrelationKey;
import com.hotent.core.bpmn20.entity.GlobalConversation;
import com.hotent.core.bpmn20.entity.MessageFlow;
import com.hotent.core.bpmn20.entity.MessageFlowAssociation;
import com.hotent.core.bpmn20.entity.Participant;
import com.hotent.core.bpmn20.entity.ParticipantAssociation;
import com.hotent.core.bpmn20.entity.RootElement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCollaboration", propOrder = {"participant", "messageFlow", "artifact", "conversationNode",
		"conversationAssociation", "participantAssociation", "messageFlowAssociation", "correlationKey",
		"choreographyRef", "conversationLink"})
@XmlSeeAlso({GlobalConversation.class, Choreography.class})
public class Collaboration extends RootElement {
	protected List<Participant> participant;
	protected List<MessageFlow> messageFlow;
	@XmlElementRef(name = "artifact", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends Artifact>> artifact;
	@XmlElementRef(name = "conversationNode", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends ConversationNode>> conversationNode;
	protected List<ConversationAssociation> conversationAssociation;
	protected List<ParticipantAssociation> participantAssociation;
	protected List<MessageFlowAssociation> messageFlowAssociation;
	protected List<CorrelationKey> correlationKey;
	protected List<QName> choreographyRef;
	protected List<ConversationLink> conversationLink;
	@XmlAttribute
	protected String name;
	@XmlAttribute
	protected Boolean isClosed;

	public List<Participant> getParticipant() {
		if (this.participant == null) {
			this.participant = new ArrayList();
		}

		return this.participant;
	}

	public List<MessageFlow> getMessageFlow() {
		if (this.messageFlow == null) {
			this.messageFlow = new ArrayList();
		}

		return this.messageFlow;
	}

	public List<JAXBElement<? extends Artifact>> getArtifact() {
		if (this.artifact == null) {
			this.artifact = new ArrayList();
		}

		return this.artifact;
	}

	public List<JAXBElement<? extends ConversationNode>> getConversationNode() {
		if (this.conversationNode == null) {
			this.conversationNode = new ArrayList();
		}

		return this.conversationNode;
	}

	public List<ConversationAssociation> getConversationAssociation() {
		if (this.conversationAssociation == null) {
			this.conversationAssociation = new ArrayList();
		}

		return this.conversationAssociation;
	}

	public List<ParticipantAssociation> getParticipantAssociation() {
		if (this.participantAssociation == null) {
			this.participantAssociation = new ArrayList();
		}

		return this.participantAssociation;
	}

	public List<MessageFlowAssociation> getMessageFlowAssociation() {
		if (this.messageFlowAssociation == null) {
			this.messageFlowAssociation = new ArrayList();
		}

		return this.messageFlowAssociation;
	}

	public List<CorrelationKey> getCorrelationKey() {
		if (this.correlationKey == null) {
			this.correlationKey = new ArrayList();
		}

		return this.correlationKey;
	}

	public List<QName> getChoreographyRef() {
		if (this.choreographyRef == null) {
			this.choreographyRef = new ArrayList();
		}

		return this.choreographyRef;
	}

	public List<ConversationLink> getConversationLink() {
		if (this.conversationLink == null) {
			this.conversationLink = new ArrayList();
		}

		return this.conversationLink;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public boolean isIsClosed() {
		return this.isClosed == null ? false : this.isClosed.booleanValue();
	}

	public void setIsClosed(Boolean value) {
		this.isClosed = value;
	}
}