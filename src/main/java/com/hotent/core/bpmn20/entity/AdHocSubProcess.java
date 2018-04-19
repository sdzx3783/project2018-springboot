package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.AdHocOrdering;
import com.hotent.core.bpmn20.entity.Expression;
import com.hotent.core.bpmn20.entity.SubProcess;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tAdHocSubProcess", propOrder = {"completionCondition"})
public class AdHocSubProcess extends SubProcess {
	protected Expression completionCondition;
	@XmlAttribute
	protected Boolean cancelRemainingInstances;
	@XmlAttribute
	protected AdHocOrdering ordering;

	public Expression getCompletionCondition() {
		return this.completionCondition;
	}

	public void setCompletionCondition(Expression value) {
		this.completionCondition = value;
	}

	public boolean isCancelRemainingInstances() {
		return this.cancelRemainingInstances == null ? true : this.cancelRemainingInstances.booleanValue();
	}

	public void setCancelRemainingInstances(Boolean value) {
		this.cancelRemainingInstances = value;
	}

	public AdHocOrdering getOrdering() {
		return this.ordering;
	}

	public void setOrdering(AdHocOrdering value) {
		this.ordering = value;
	}
}