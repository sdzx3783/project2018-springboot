package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tInputOutputBinding")
public class InputOutputBinding extends BaseElement {
	@XmlAttribute(required = true)
	protected QName operationRef;
	@XmlAttribute(required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object inputDataRef;
	@XmlAttribute(required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object outputDataRef;

	public QName getOperationRef() {
		return this.operationRef;
	}

	public void setOperationRef(QName value) {
		this.operationRef = value;
	}

	public Object getInputDataRef() {
		return this.inputDataRef;
	}

	public void setInputDataRef(Object value) {
		this.inputDataRef = value;
	}

	public Object getOutputDataRef() {
		return this.outputDataRef;
	}

	public void setOutputDataRef(Object value) {
		this.outputDataRef = value;
	}
}