package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.CancelEventDefinition;
import com.hotent.core.bpmn20.entity.CompensateEventDefinition;
import com.hotent.core.bpmn20.entity.ConditionalEventDefinition;
import com.hotent.core.bpmn20.entity.ErrorEventDefinition;
import com.hotent.core.bpmn20.entity.EscalationEventDefinition;
import com.hotent.core.bpmn20.entity.LinkEventDefinition;
import com.hotent.core.bpmn20.entity.MessageEventDefinition;
import com.hotent.core.bpmn20.entity.RootElement;
import com.hotent.core.bpmn20.entity.SignalEventDefinition;
import com.hotent.core.bpmn20.entity.TerminateEventDefinition;
import com.hotent.core.bpmn20.entity.TimerEventDefinition;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tEventDefinition")
@XmlSeeAlso({TimerEventDefinition.class, CancelEventDefinition.class, MessageEventDefinition.class,
		ErrorEventDefinition.class, ConditionalEventDefinition.class, TerminateEventDefinition.class,
		LinkEventDefinition.class, EscalationEventDefinition.class, CompensateEventDefinition.class,
		SignalEventDefinition.class})
public abstract class EventDefinition extends RootElement {
}