package com.hotent.core.excel.style;

public enum VAlign {
	TOP((short)0), CENTER((short)1), BOTTOM((short)2), JUSTIFY((short)3);

	private short alignment;

	private VAlign(short alignment) {
		this.alignment = alignment;
	}

	public short getAlignment() {
		return this.alignment;
	}
}