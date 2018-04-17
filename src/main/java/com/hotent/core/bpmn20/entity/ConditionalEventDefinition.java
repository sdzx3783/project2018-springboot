package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.EventDefinition;
import com.hotent.core.bpmn20.entity.Expression;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tConditionalEventDefinition", propOrder = {"condition"})
public class ConditionalEventDefinition extends EventDefinition {
	@XmlElement(required = true)
	protected Expression condition;

	public Expression getCondition() {
		return this.condition;
	}

	public void setCondition(Expression value) {
		this.condition = value;
	}
}