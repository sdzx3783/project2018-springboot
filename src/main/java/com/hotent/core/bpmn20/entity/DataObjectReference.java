package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.DataState;
import com.hotent.core.bpmn20.entity.FlowElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDataObjectReference", propOrder = {"dataState"})
public class DataObjectReference extends FlowElement {
	protected DataState dataState;
	@XmlAttribute
	protected QName itemSubjectRef;
	@XmlAttribute
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object dataObjectRef;

	public DataState getDataState() {
		return this.dataState;
	}

	public void setDataState(DataState value) {
		this.dataState = value;
	}

	public QName getItemSubjectRef() {
		return this.itemSubjectRef;
	}

	public void setItemSubjectRef(QName value) {
		this.itemSubjectRef = value;
	}

	public Object getDataObjectRef() {
		return this.dataObjectRef;
	}

	public void setDataObjectRef(Object value) {
		this.dataObjectRef = value;
	}
}