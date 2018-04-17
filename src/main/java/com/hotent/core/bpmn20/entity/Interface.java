package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.Operation;
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
@XmlType(name = "tInterface", propOrder = {"operation"})
public class Interface extends RootElement {
	@XmlElement(required = true)
	protected List<Operation> operation;
	@XmlAttribute(required = true)
	protected String name;
	@XmlAttribute
	protected QName implementationRef;

	public List<Operation> getOperation() {
		if (this.operation == null) {
			this.operation = new ArrayList();
		}

		return this.operation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public QName getImplementationRef() {
		return this.implementationRef;
	}

	public void setImplementationRef(QName value) {
		this.implementationRef = value;
	}
}