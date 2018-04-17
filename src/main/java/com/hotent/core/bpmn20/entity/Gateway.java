package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.ComplexGateway;
import com.hotent.core.bpmn20.entity.EventBasedGateway;
import com.hotent.core.bpmn20.entity.ExclusiveGateway;
import com.hotent.core.bpmn20.entity.FlowNode;
import com.hotent.core.bpmn20.entity.GatewayDirection;
import com.hotent.core.bpmn20.entity.InclusiveGateway;
import com.hotent.core.bpmn20.entity.ParallelGateway;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tGateway")
@XmlSeeAlso({ComplexGateway.class, InclusiveGateway.class, EventBasedGateway.class, ParallelGateway.class,
		ExclusiveGateway.class})
public class Gateway extends FlowNode {
	@XmlAttribute
	protected GatewayDirection gatewayDirection;

	public GatewayDirection getGatewayDirection() {
		return this.gatewayDirection == null ? GatewayDirection.UNSPECIFIED : this.gatewayDirection;
	}

	public void setGatewayDirection(GatewayDirection value) {
		this.gatewayDirection = value;
	}
}