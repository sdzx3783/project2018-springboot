package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Gateway;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tInclusiveGateway")
public class InclusiveGateway extends Gateway {
	@XmlAttribute(name = "default")
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object _default;

	public Object getDefault() {
		return this._default;
	}

	public void setDefault(Object value) {
		this._default = value;
	}
}