package com.hotent.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tAssociationDirection")
@XmlEnum
public enum AssociationDirection {
	@XmlEnumValue("None")
	NONE("None"), @XmlEnumValue("One")
	ONE("One"), @XmlEnumValue("Both")
	BOTH("Both");

	private final String value;

	private AssociationDirection(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static AssociationDirection fromValue(String v) {
		AssociationDirection[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			AssociationDirection c = arr$[i$];
			if (c.value.equals(v)) {
				return c;
			}
		}

		throw new IllegalArgumentException(v);
	}
}