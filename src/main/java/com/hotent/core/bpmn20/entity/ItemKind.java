package com.hotent.core.bpmn20.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tItemKind")
@XmlEnum
public enum ItemKind {
	@XmlEnumValue("Information")
	INFORMATION("Information"), @XmlEnumValue("Physical")
	PHYSICAL("Physical");

	private final String value;

	private ItemKind(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static ItemKind fromValue(String v) {
		ItemKind[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			ItemKind c = arr$[i$];
			if (c.value.equals(v)) {
				return c;
			}
		}

		throw new IllegalArgumentException(v);
	}
}