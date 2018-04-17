package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Artifact;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tGroup")
public class Group extends Artifact {
	@XmlAttribute
	protected QName categoryValueRef;

	public QName getCategoryValueRef() {
		return this.categoryValueRef;
	}

	public void setCategoryValueRef(QName value) {
		this.categoryValueRef = value;
	}
}