package com.hotent.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tAdHocOrdering")
@XmlEnum
public enum AdHocOrdering {
	@XmlEnumValue("Parallel")
	PARALLEL("Parallel"), @XmlEnumValue("Sequential")
	SEQUENTIAL("Sequential");

	private final String value;

	private AdHocOrdering(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static AdHocOrdering fromValue(String v) {
		AdHocOrdering[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			AdHocOrdering c = arr$[i$];
			if (c.value.equals(v)) {
				return c;
			}
		}

		throw new IllegalArgumentException(v);
	}
}