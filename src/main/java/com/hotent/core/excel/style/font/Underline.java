package com.hotent.core.excel.style.font;

public enum Underline {
	NONE(0), SINGLE(1), DOUBLE(2), SINGLE_ACCOUNTING(33), DOUBLE_ACCOUNTING(34);

	private byte line;

	private Underline(byte line) {
		this.line = line;
	}

	public byte getLine() {
		return this.line;
	}

	public static Underline instance(byte line) {
		Underline[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			Underline e = arr$[i$];
			if (e.getLine() == line) {
				return e;
			}
		}

		return NONE;
	}
}