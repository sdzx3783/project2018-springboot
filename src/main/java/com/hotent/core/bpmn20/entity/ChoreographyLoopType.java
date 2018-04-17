package com.hotent.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tChoreographyLoopType")
@XmlEnum
public enum ChoreographyLoopType {
	@XmlEnumValue("None")
	NONE("None"), @XmlEnumValue("Standard")
	STANDARD("Standard"), @XmlEnumValue("MultiInstanceSequential")
	MULTI_INSTANCE_SEQUENTIAL("MultiInstanceSequential"), @XmlEnumValue("MultiInstanceParallel")
	MULTI_INSTANCE_PARALLEL("MultiInstanceParallel");

	private final String value;

	private ChoreographyLoopType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static ChoreographyLoopType fromValue(String v) {
		ChoreographyLoopType[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			ChoreographyLoopType c = arr$[i$];
			if (c.value.equals(v)) {
				return c;
			}
		}

		throw new IllegalArgumentException(v);
	}
}