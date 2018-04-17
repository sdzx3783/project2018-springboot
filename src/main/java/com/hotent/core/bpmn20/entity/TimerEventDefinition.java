package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.EventDefinition;
import com.hotent.core.bpmn20.entity.Expression;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tTimerEventDefinition", propOrder = {"timeDate", "timeDuration", "timeCycle"})
public class TimerEventDefinition extends EventDefinition {
	protected Expression timeDate;
	protected Expression timeDuration;
	protected Expression timeCycle;

	public Expression getTimeDate() {
		return this.timeDate;
	}

	public void setTimeDate(Expression value) {
		this.timeDate = value;
	}

	public Expression getTimeDuration() {
		return this.timeDuration;
	}

	public void setTimeDuration(Expression value) {
		this.timeDuration = value;
	}

	public Expression getTimeCycle() {
		return this.timeCycle;
	}

	public void setTimeCycle(Expression value) {
		this.timeCycle = value;
	}
}