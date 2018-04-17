package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Expression;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tFormalExpression")
public class FormalExpression extends Expression {
	@XmlAttribute
	@XmlSchemaType(name = "anyURI")
	protected String language;
	@XmlAttribute
	protected QName evaluatesToTypeRef;

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String value) {
		this.language = value;
	}

	public QName getEvaluatesToTypeRef() {
		return this.evaluatesToTypeRef;
	}

	public void setEvaluatesToTypeRef(QName value) {
		this.evaluatesToTypeRef = value;
	}
}