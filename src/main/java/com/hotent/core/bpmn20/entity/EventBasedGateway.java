package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.EventBasedGatewayType;
import com.hotent.core.bpmn20.entity.Gateway;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tEventBasedGateway")
public class EventBasedGateway extends Gateway {
	@XmlAttribute
	protected Boolean instantiate;
	@XmlAttribute
	protected EventBasedGatewayType eventGatewayType;

	public boolean isInstantiate() {
		return this.instantiate == null ? false : this.instantiate.booleanValue();
	}

	public void setInstantiate(Boolean value) {
		this.instantiate = value;
	}

	public EventBasedGatewayType getEventGatewayType() {
		return this.eventGatewayType == null ? EventBasedGatewayType.EXCLUSIVE : this.eventGatewayType;
	}

	public void setEventGatewayType(EventBasedGatewayType value) {
		this.eventGatewayType = value;
	}
}