package com.hotent.core.excel.style;

public enum Align {
	GENERAL((short) 0), LEFT((short) 1), CENTER((short) 2), RIGHT((short) 3), FILL((short) 4), JUSTIFY((short) 5), CENTER_SELECTION((short) 6);

	private short alignment;

	private Align(short alignment) {
		this.alignment = alignment;
	}

	public short getAlignment() {
		return this.alignment;
	}
}