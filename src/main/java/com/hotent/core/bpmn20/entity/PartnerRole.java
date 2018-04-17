package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.RootElement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPartnerRole", propOrder = {"participantRef"})
public class PartnerRole extends RootElement {
	protected List<QName> participantRef;
	@XmlAttribute
	protected String name;

	public List<QName> getParticipantRef() {
		if (this.participantRef == null) {
			this.participantRef = new ArrayList();
		}

		return this.participantRef;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
}