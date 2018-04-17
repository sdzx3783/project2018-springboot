package com.hotent.core.excel.style;

public enum VAlign {
	TOP(0), CENTER(1), BOTTOM(2), JUSTIFY(3);

	private short alignment;

	private VAlign(short alignment) {
		this.alignment = alignment;
	}

	public short getAlignment() {
		return this.alignment;
	}
}