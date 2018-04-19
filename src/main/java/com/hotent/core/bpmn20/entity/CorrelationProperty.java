package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.CorrelationPropertyRetrievalExpression;
import com.hotent.core.bpmn20.entity.RootElement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCorrelationProperty", propOrder = {"correlationPropertyRetrievalExpression"})
public class CorrelationProperty extends RootElement {
	@XmlElement(required = true)
	protected List<CorrelationPropertyRetrievalExpression> correlationPropertyRetrievalExpression;
	@XmlAttribute
	protected String name;
	@XmlAttribute
	protected QName type;

	public List<CorrelationPropertyRetrievalExpression> getCorrelationPropertyRetrievalExpression() {
		if (this.correlationPropertyRetrievalExpression == null) {
			this.correlationPropertyRetrievalExpression = new ArrayList();
		}

		return this.correlationPropertyRetrievalExpression;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public QName getType() {
		return this.type;
	}

	public void setType(QName value) {
		this.type = value;
	}
}