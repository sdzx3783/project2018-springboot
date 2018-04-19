package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.DataState;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tProperty", propOrder = {"dataState"})
public class Property extends BaseElement {
	protected DataState dataState;
	@XmlAttribute
	protected String name;
	@XmlAttribute
	protected QName itemSubjectRef;

	public DataState getDataState() {
		return this.dataState;
	}

	public void setDataState(DataState value) {
		this.dataState = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public QName getItemSubjectRef() {
		return this.itemSubjectRef;
	}

	public void setItemSubjectRef(QName value) {
		this.itemSubjectRef = value;
	}
}