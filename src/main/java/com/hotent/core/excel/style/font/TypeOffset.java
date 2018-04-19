package com.hotent.core.excel.style.font;

public enum TypeOffset {
	NONE((short)0), SUPER((short)1), SUB((short)2);

	private short offset;

	private TypeOffset(short offset) {
		this.offset = offset;
	}

	public short getOffset() {
		return this.offset;
	}

	public static TypeOffset instance(short offset) {
		TypeOffset[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			TypeOffset e = arr$[i$];
			if (e.getOffset() == offset) {
				return e;
			}
		}

		return NONE;
	}
}