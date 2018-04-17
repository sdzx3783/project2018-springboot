package com.hotent.core.bpmn20.entity.bpmndi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "MessageVisibleKind")
@XmlEnum
public enum MessageVisibleKind {
	@XmlEnumValue("initiating")
	INITIATING("initiating"), @XmlEnumValue("non_initiating")
	NON_INITIATING("non_initiating");

	private final String value;

	private MessageVisibleKind(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static MessageVisibleKind fromValue(String v) {
		MessageVisibleKind[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			MessageVisibleKind c = arr$[i$];
			if (c.value.equals(v)) {
				return c;
			}
		}

		throw new IllegalArgumentException(v);
	}
}