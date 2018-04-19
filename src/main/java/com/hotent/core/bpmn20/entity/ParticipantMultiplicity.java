package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tParticipantMultiplicity")
public class ParticipantMultiplicity extends BaseElement {
	@XmlAttribute
	protected Integer minimum;
	@XmlAttribute
	protected Integer maximum;

	public int getMinimum() {
		return this.minimum == null ? 0 : this.minimum.intValue();
	}

	public void setMinimum(Integer value) {
		this.minimum = value;
	}

	public int getMaximum() {
		return this.maximum == null ? 1 : this.maximum.intValue();
	}

	public void setMaximum(Integer value) {
		this.maximum = value;
	}
}