package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tInputSet", propOrder = {"dataInputRefs", "optionalInputRefs", "whileExecutingInputRefs",
		"outputSetRefs"})
public class InputSet extends BaseElement {
	@XmlElementRef(name = "dataInputRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<Object>> dataInputRefs;
	@XmlElementRef(name = "optionalInputRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<Object>> optionalInputRefs;
	@XmlElementRef(name = "whileExecutingInputRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<Object>> whileExecutingInputRefs;
	@XmlElementRef(name = "outputSetRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<Object>> outputSetRefs;
	@XmlAttribute
	protected String name;

	public List<JAXBElement<Object>> getDataInputRefs() {
		if (this.dataInputRefs == null) {
			this.dataInputRefs = new ArrayList();
		}

		return this.dataInputRefs;
	}

	public List<JAXBElement<Object>> getOptionalInputRefs() {
		if (this.optionalInputRefs == null) {
			this.optionalInputRefs = new ArrayList();
		}

		return this.optionalInputRefs;
	}

	public List<JAXBElement<Object>> getWhileExecutingInputRefs() {
		if (this.whileExecutingInputRefs == null) {
			this.whileExecutingInputRefs = new ArrayList();
		}

		return this.whileExecutingInputRefs;
	}

	public List<JAXBElement<Object>> getOutputSetRefs() {
		if (this.outputSetRefs == null) {
			this.outputSetRefs = new ArrayList();
		}

		return this.outputSetRefs;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
}