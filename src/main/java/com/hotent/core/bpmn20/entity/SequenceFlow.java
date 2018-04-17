package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Expression;
import com.hotent.core.bpmn20.entity.FlowElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tSequenceFlow", propOrder = {"conditionExpression"})
public class SequenceFlow extends FlowElement {
	protected Expression conditionExpression;
	@XmlAttribute(required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object sourceRef;
	@XmlAttribute(required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object targetRef;
	@XmlAttribute
	protected Boolean isImmediate;

	public Expression getConditionExpression() {
		return this.conditionExpression;
	}

	public void setConditionExpression(Expression value) {
		this.conditionExpression = value;
	}

	public Object getSourceRef() {
		return this.sourceRef;
	}

	public void setSourceRef(Object value) {
		this.sourceRef = value;
	}

	public Object getTargetRef() {
		return this.targetRef;
	}

	public void setTargetRef(Object value) {
		this.targetRef = value;
	}

	public Boolean isIsImmediate() {
		return this.isImmediate;
	}

	public void setIsImmediate(Boolean value) {
		this.isImmediate = value;
	}
}