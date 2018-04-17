package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDataState")
public class DataState extends BaseElement {
	@XmlAttribute
	protected String name;

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
}