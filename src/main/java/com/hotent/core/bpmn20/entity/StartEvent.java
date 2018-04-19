package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.CatchEvent;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tStartEvent")
public class StartEvent extends CatchEvent {
	@XmlAttribute
	protected Boolean isInterrupting;

	public boolean isIsInterrupting() {
		return this.isInterrupting == null ? true : this.isInterrupting.booleanValue();
	}

	public void setIsInterrupting(Boolean value) {
		this.isInterrupting = value;
	}
}