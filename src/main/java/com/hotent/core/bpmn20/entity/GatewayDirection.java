package com.hotent.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tGatewayDirection")
@XmlEnum
public enum GatewayDirection {
	@XmlEnumValue("Unspecified")
	UNSPECIFIED("Unspecified"), @XmlEnumValue("Converging")
	CONVERGING("Converging"), @XmlEnumValue("Diverging")
	DIVERGING("Diverging"), @XmlEnumValue("Mixed")
	MIXED("Mixed");

	private final String value;

	private GatewayDirection(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static GatewayDirection fromValue(String v) {
		GatewayDirection[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			GatewayDirection c = arr$[i$];
			if (c.value.equals(v)) {
				return c;
			}
		}

		throw new IllegalArgumentException(v);
	}
}