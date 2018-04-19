package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.ParticipantMultiplicity;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tParticipant", propOrder = {"interfaceRef", "endPointRef", "participantMultiplicity"})
public class Participant extends BaseElement {
	protected List<QName> interfaceRef;
	protected List<QName> endPointRef;
	protected ParticipantMultiplicity participantMultiplicity;
	@XmlAttribute
	protected String name;
	@XmlAttribute
	protected QName processRef;

	public List<QName> getInterfaceRef() {
		if (this.interfaceRef == null) {
			this.interfaceRef = new ArrayList();
		}

		return this.interfaceRef;
	}

	public List<QName> getEndPointRef() {
		if (this.endPointRef == null) {
			this.endPointRef = new ArrayList();
		}

		return this.endPointRef;
	}

	public ParticipantMultiplicity getParticipantMultiplicity() {
		return this.participantMultiplicity;
	}

	public void setParticipantMultiplicity(ParticipantMultiplicity value) {
		this.participantMultiplicity = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public QName getProcessRef() {
		return this.processRef;
	}

	public void setProcessRef(QName value) {
		this.processRef = value;
	}
}