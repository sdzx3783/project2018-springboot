package com.hotent.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tMultiInstanceFlowCondition")
@XmlEnum
public enum MultiInstanceFlowCondition {
	@XmlEnumValue("None")
	NONE("None"), @XmlEnumValue("One")
	ONE("One"), @XmlEnumValue("All")
	ALL("All"), @XmlEnumValue("Complex")
	COMPLEX("Complex");

	private final String value;

	private MultiInstanceFlowCondition(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static MultiInstanceFlowCondition fromValue(String v) {
		MultiInstanceFlowCondition[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			MultiInstanceFlowCondition c = arr$[i$];
			if (c.value.equals(v)) {
				return c;
			}
		}

		throw new IllegalArgumentException(v);
	}
}