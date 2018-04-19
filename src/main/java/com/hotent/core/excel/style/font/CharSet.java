package com.hotent.core.excel.style.font;

public enum CharSet {
	ANSI((byte)0), DEFAULT((byte)1), SYMBOL((byte)2);

	private byte charset;

	private CharSet(byte charset) {
		this.charset = charset;
	}

	public byte getCharset() {
		return this.charset;
	}

	public static CharSet instance(byte charset) {
		return instance((int) charset);
	}

	public static CharSet instance(int charset) {
		CharSet[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			CharSet e = arr$[i$];
			if (e.getCharset() == charset) {
				return e;
			}
		}

		return DEFAULT;
	}
}