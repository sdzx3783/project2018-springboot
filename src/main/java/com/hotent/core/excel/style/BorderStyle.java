package com.hotent.core.excel.style;

public enum BorderStyle {
	NONE(0), THIN(1), MEDIUM(2), THICK(5), DASHED(3), HAIR(4), DOUBLE(6), DOTTED(7), MEDIUM_DASHED(8), DASH_DOT(
			9), MEDIUM_DASH_DOT(10), DASH_DOT_DOT(11), MEDIUM_DASH_DOT_DOT(12), SLANTED_DASH_DOT(13);

	private short borderType;

	private BorderStyle(short borderType) {
		this.borderType = borderType;
	}

	public short getBorderType() {
		return this.borderType;
	}
}