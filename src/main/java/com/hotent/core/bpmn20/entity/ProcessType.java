package com.hotent.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tProcessType")
@XmlEnum
public enum ProcessType {
	@XmlEnumValue("None")
	NONE("None"), @XmlEnumValue("Public")
	PUBLIC("Public"), @XmlEnumValue("Private")
	PRIVATE("Private");

	private final String value;

	private ProcessType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static ProcessType fromValue(String v) {
		ProcessType[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			ProcessType c = arr$[i$];
			if (c.value.equals(v)) {
				return c;
			}
		}

		throw new IllegalArgumentException(v);
	}
}