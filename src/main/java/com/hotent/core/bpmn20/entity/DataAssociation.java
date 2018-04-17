package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Assignment;
import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.DataInputAssociation;
import com.hotent.core.bpmn20.entity.DataOutputAssociation;
import com.hotent.core.bpmn20.entity.FormalExpression;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDataAssociation", propOrder = {"sourceRef", "targetRef", "transformation", "assignment"})
@XmlSeeAlso({DataInputAssociation.class, DataOutputAssociation.class})
public class DataAssociation extends BaseElement {
	@XmlElementRef(name = "sourceRef", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<Object>> sourceRef;
	@XmlElement(required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object targetRef;
	protected FormalExpression transformation;
	protected List<Assignment> assignment;

	public List<JAXBElement<Object>> getSourceRef() {
		if (this.sourceRef == null) {
			this.sourceRef = new ArrayList();
		}

		return this.sourceRef;
	}

	public Object getTargetRef() {
		return this.targetRef;
	}

	public void setTargetRef(Object value) {
		this.targetRef = value;
	}

	public FormalExpression getTransformation() {
		return this.transformation;
	}

	public void setTransformation(FormalExpression value) {
		this.transformation = value;
	}

	public List<Assignment> getAssignment() {
		if (this.assignment == null) {
			this.assignment = new ArrayList();
		}

		return this.assignment;
	}
}