package com.hotent.core.bpmn20.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tExtensionElements", propOrder = {"any"})
public class ExtensionElements {
	@XmlAnyElement(lax = true)
	protected List<Object> any;

	public List<Object> getAny() {
		if (this.any == null) {
			this.any = new ArrayList();
		}

		return this.any;
	}
}