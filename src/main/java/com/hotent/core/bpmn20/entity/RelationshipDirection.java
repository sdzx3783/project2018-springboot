package com.hotent.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tRelationshipDirection")
@XmlEnum
public enum RelationshipDirection {
	@XmlEnumValue("None")
	NONE("None"), @XmlEnumValue("Forward")
	FORWARD("Forward"), @XmlEnumValue("Backward")
	BACKWARD("Backward"), @XmlEnumValue("Both")
	BOTH("Both");

	private final String value;

	private RelationshipDirection(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static RelationshipDirection fromValue(String v) {
		RelationshipDirection[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			RelationshipDirection c = arr$[i$];
			if (c.value.equals(v)) {
				return c;
			}
		}

		throw new IllegalArgumentException(v);
	}
}