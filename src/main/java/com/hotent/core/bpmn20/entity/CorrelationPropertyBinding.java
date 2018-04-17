package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.BaseElement;
import com.hotent.core.bpmn20.entity.FormalExpression;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCorrelationPropertyBinding", propOrder = {"dataPath"})
public class CorrelationPropertyBinding extends BaseElement {
	@XmlElement(required = true)
	protected FormalExpression dataPath;
	@XmlAttribute(required = true)
	protected QName correlationPropertyRef;

	public FormalExpression getDataPath() {
		return this.dataPath;
	}

	public void setDataPath(FormalExpression value) {
		this.dataPath = value;
	}

	public QName getCorrelationPropertyRef() {
		return this.correlationPropertyRef;
	}

	public void setCorrelationPropertyRef(QName value) {
		this.correlationPropertyRef = value;
	}
}