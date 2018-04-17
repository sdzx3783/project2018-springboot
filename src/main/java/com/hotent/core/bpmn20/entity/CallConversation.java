package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.ConversationNode;
import com.hotent.core.bpmn20.entity.ParticipantAssociation;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCallConversation", propOrder = {"participantAssociation"})
public class CallConversation extends ConversationNode {
	protected List<ParticipantAssociation> participantAssociation;
	@XmlAttribute
	protected QName calledCollaborationRef;

	public List<ParticipantAssociation> getParticipantAssociation() {
		if (this.participantAssociation == null) {
			this.participantAssociation = new ArrayList();
		}

		return this.participantAssociation;
	}

	public QName getCalledCollaborationRef() {
		return this.calledCollaborationRef;
	}

	public void setCalledCollaborationRef(QName value) {
		this.calledCollaborationRef = value;
	}
}