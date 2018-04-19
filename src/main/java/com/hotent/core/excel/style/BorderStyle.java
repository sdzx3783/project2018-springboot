package com.hotent.core.excel.style;

public enum BorderStyle {
	NONE((short)0), THIN((short)1), MEDIUM((short)2), THICK((short)5), DASHED((short)3), HAIR((short)4), DOUBLE((short)6), DOTTED((short)7), MEDIUM_DASHED((short)8), DASH_DOT(
			(short)9), MEDIUM_DASH_DOT((short)10), DASH_DOT_DOT((short)11), MEDIUM_DASH_DOT_DOT((short)12), SLANTED_DASH_DOT((short)13);

	private short borderType;

	private BorderStyle(short borderType) {
		this.borderType = borderType;
	}

	public short getBorderType() {
		return this.borderType;
	}
}