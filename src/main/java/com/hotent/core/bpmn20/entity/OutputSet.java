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
@XmlType(name = "tOutputSet", propOrder = {"dataOutputRefs", "optionalOutputRefs", "whileExecutingOutputRefs",
		"inputSetRefs"})
public class OutputSet extends BaseElement {
	@XmlElementRef(name = "dataOutputRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<Object>> dataOutputRefs;
	@XmlElementRef(name = "optionalOutputRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<Object>> optionalOutputRefs;
	@XmlElementRef(name = "whileExecutingOutputRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<Object>> whileExecutingOutputRefs;
	@XmlElementRef(name = "inputSetRefs", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL", type = JAXBElement.class)
	protected List<JAXBElement<Object>> inputSetRefs;
	@XmlAttribute
	protected String name;

	public List<JAXBElement<Object>> getDataOutputRefs() {
		if (this.dataOutputRefs == null) {
			this.dataOutputRefs = new ArrayList();
		}

		return this.dataOutputRefs;
	}

	public List<JAXBElement<Object>> getOptionalOutputRefs() {
		if (this.optionalOutputRefs == null) {
			this.optionalOutputRefs = new ArrayList();
		}

		return this.optionalOutputRefs;
	}

	public List<JAXBElement<Object>> getWhileExecutingOutputRefs() {
		if (this.whileExecutingOutputRefs == null) {
			this.whileExecutingOutputRefs = new ArrayList();
		}

		return this.whileExecutingOutputRefs;
	}

	public List<JAXBElement<Object>> getInputSetRefs() {
		if (this.inputSetRefs == null) {
			this.inputSetRefs = new ArrayList();
		}

		return this.inputSetRefs;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
}