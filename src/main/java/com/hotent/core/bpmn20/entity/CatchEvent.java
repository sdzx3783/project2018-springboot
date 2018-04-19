package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BoundaryEvent;
import com.hotent.core.bpmn20.entity.DataOutput;
import com.hotent.core.bpmn20.entity.DataOutputAssociation;
import com.hotent.core.bpmn20.entity.Event;
import com.hotent.core.bpmn20.entity.EventDefinition;
import com.hotent.core.bpmn20.entity.IntermediateCatchEvent;
import com.hotent.core.bpmn20.entity.OutputSet;
import com.hotent.core.bpmn20.entity.StartEvent;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCatchEvent", propOrder = {"dataOutput", "dataOutputAssociation", "outputSet", "eventDefinition",
		"eventDefinitionRef"})
@XmlSeeAlso({StartEvent.class, IntermediateCatchEvent.class, BoundaryEvent.class})
public abstract class CatchEvent extends Event {
	protected List<DataOutput> dataOutput;
	protected List<DataOutputAssociation> dataOutputAssociation;
	protected OutputSet outputSet;
	@XmlElementRef(name = "eventDefinition", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<? extends EventDefinition>> eventDefinition;
	protected List<QName> eventDefinitionRef;
	@XmlAttribute
	protected Boolean parallelMultiple;

	public List<DataOutput> getDataOutput() {
		if (this.dataOutput == null) {
			this.dataOutput = new ArrayList();
		}

		return this.dataOutput;
	}

	public List<DataOutputAssociation> getDataOutputAssociation() {
		if (this.dataOutputAssociation == null) {
			this.dataOutputAssociation = new ArrayList();
		}

		return this.dataOutputAssociation;
	}

	public OutputSet getOutputSet() {
		return this.outputSet;
	}

	public void setOutputSet(OutputSet value) {
		this.outputSet = value;
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

	public boolean isParallelMultiple() {
		return this.parallelMultiple == null ? false : this.parallelMultiple.booleanValue();
	}

	public void setParallelMultiple(Boolean value) {
		this.parallelMultiple = value;
	}
}