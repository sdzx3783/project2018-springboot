package com.hotent.core.excel.style.font;

public enum BoldWeight {
	NORMAL(400), BOLD(700);

	private short weight;

	private BoldWeight(short weight) {
		this.weight = weight;
	}

	public short getWeight() {
		return this.weight;
	}

	public static BoldWeight instance(short weight) {
		BoldWeight[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			BoldWeight e = arr$[i$];
			if (e.getWeight() == weight) {
				return e;
			}
		}

		return NORMAL;
	}
}