package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.DataInput;
import com.hotent.core.bpmn20.entity.DataInputAssociation;
import com.hotent.core.bpmn20.entity.EndEvent;
import com.hotent.core.bpmn20.entity.Event;
import com.hotent.core.bpmn20.entity.EventDefinition;
import com.hotent.core.bpmn20.entity.ImplicitThrowEvent;
import com.hotent.core.bpmn20.entity.InputSet;
import com.hotent.core.bpmn20.entity.IntermediateThrowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tThrowEvent", propOrder = {"dataInput", "dataInputAssociation", "inputSet", "eventDefinition",
		"eventDefinitionRef"})
@XmlSeeAlso({IntermediateThrowEvent.class, ImplicitThrowEvent.class, EndEvent.class})
public abstract class ThrowEvent extends Event {
	protected List<DataInput> dataInput;
	protected List<DataInputAssociation> dataInputAssociation;
	protected InputSet inputSet;
	@XmlElementRef(name = "eventDefinition", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends EventDefinition>> eventDefinition;
	protected List<QName> eventDefinitionRef;

	public List<DataInput> getDataInput() {
		if (this.dataInput == null) {
			this.dataInput = new ArrayList();
		}

		return this.dataInput;
	}

	public List<DataInputAssociation> getDataInputAssociation() {
		if (this.dataInputAssociation == null) {
			this.dataInputAssociation = new ArrayList();
		}

		return this.dataInputAssociation;
	}

	public InputSet getInputSet() {
		return this.inputSet;
	}

	public void setInputSet(InputSet value) {
		this.inputSet = value;
	}

	public List<JAXBElement<? extends EventDefinition>> getEventDefinition() {
		if (this.eventDefinition == null) {
			this.eventDefinition = new ArrayList();
		}

		return this.eventDefinition;
	}

	public List<QName> getEventDefinitionRef() {
		if (this.eventDefinitionRef == null) {
			this.eventDefinitionRef = new ArrayList();
		}

		return this.eventDefinitionRef;
	}
}