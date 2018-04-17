package com.hotent.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tEventBasedGatewayType")
@XmlEnum
public enum EventBasedGatewayType {
	@XmlEnumValue("Exclusive")
	EXCLUSIVE("Exclusive"), @XmlEnumValue("Parallel")
	PARALLEL("Parallel");

	private final String value;

	private EventBasedGatewayType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static EventBasedGatewayType fromValue(String v) {
		EventBasedGatewayType[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			EventBasedGatewayType c = arr$[i$];
			if (c.value.equals(v)) {
				return c;
			}
		}

		throw new IllegalArgumentException(v);
	}
}