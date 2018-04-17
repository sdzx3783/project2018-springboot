package com.hotent.core.excel.style;

public enum Align {
	GENERAL(0), LEFT(1), CENTER(2), RIGHT(3), FILL(4), JUSTIFY(5), CENTER_SELECTION(6);

	private short alignment;

	private Align(short alignment) {
		this.alignment = alignment;
	}

	public short getAlignment() {
		return this.alignment;
	}
}