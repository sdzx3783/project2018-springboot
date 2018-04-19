package com.hotent.core.bpmn20.entity.bpmndi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ParticipantBandKind")
@XmlEnum
public enum ParticipantBandKind {
	@XmlEnumValue("top_initiating")
	TOP_INITIATING("top_initiating"), @XmlEnumValue("middle_initiating")
	MIDDLE_INITIATING("middle_initiating"), @XmlEnumValue("bottom_initiating")
	BOTTOM_INITIATING("bottom_initiating"), @XmlEnumValue("top_non_initiating")
	TOP_NON_INITIATING("top_non_initiating"), @XmlEnumValue("middle_non_initiating")
	MIDDLE_NON_INITIATING("middle_non_initiating"), @XmlEnumValue("bottom_non_initiating")
	BOTTOM_NON_INITIATING("bottom_non_initiating");

	private final String value;

	private ParticipantBandKind(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static ParticipantBandKind fromValue(String v) {
		ParticipantBandKind[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			ParticipantBandKind c = arr$[i$];
			if (c.value.equals(v)) {
				return c;
			}
		}

		throw new IllegalArgumentException(v);
	}
}