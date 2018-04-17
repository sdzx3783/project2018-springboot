package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Choreography;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tGlobalChoreographyTask")
public class GlobalChoreographyTask extends Choreography {
	@XmlAttribute
	protected QName initiatingParticipantRef;

	public QName getInitiatingParticipantRef() {
		return this.initiatingParticipantRef;
	}

	public void setInitiatingParticipantRef(QName value) {
		this.initiatingParticipantRef = value;
	}
}