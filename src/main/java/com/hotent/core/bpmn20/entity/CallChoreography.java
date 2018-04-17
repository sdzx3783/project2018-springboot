package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.ChoreographyActivity;
import com.hotent.core.bpmn20.entity.ParticipantAssociation;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCallChoreography", propOrder = {"participantAssociation"})
public class CallChoreography extends ChoreographyActivity {
	protected List<ParticipantAssociation> participantAssociation;
	@XmlAttribute
	protected QName calledChoreographyRef;

	public List<ParticipantAssociation> getParticipantAssociation() {
		if (this.participantAssociation == null) {
			this.participantAssociation = new ArrayList();
		}

		return this.participantAssociation;
	}

	public QName getCalledChoreographyRef() {
		return this.calledChoreographyRef;
	}

	public void setCalledChoreographyRef(QName value) {
		this.calledChoreographyRef = value;
	}
}