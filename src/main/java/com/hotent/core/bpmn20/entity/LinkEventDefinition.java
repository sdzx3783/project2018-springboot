package com.hotent.core.bpmn20.entity;

import com.hotent.core.bpmn20.entity.EventDefinition;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tLinkEventDefinition", propOrder = {"source", "target"})
public class LinkEventDefinition extends EventDefinition {
	protected List<QName> source;
	protected QName target;
	@XmlAttribute(required = true)
	protected String name;

	public List<QName> getSource() {
		if (this.source == null) {
			this.source = new ArrayList();
		}

		return this.source;
	}

	public QName getTarget() {
		return this.target;
	}

	public void setTarget(QName value) {
		this.target = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
}