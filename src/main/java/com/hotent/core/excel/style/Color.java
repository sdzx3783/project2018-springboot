package com.hotent.core.excel.style;

public enum Color {
	AUTOMATIC(64), AQUA(49), BLACK(8), BLUE(12), BLUE_GREY(54), BRIGHT_GREEN(11), BROWN(60), CORAL(29), CORNFLOWER_BLUE(
			24), DARK_BLUE(18), DARK_GREEN(58), DARK_RED(16), DARK_TEAL(56), DARK_YELLOW(19), GOLD(
					51), GREEN(17), GREY_25_PERCENT(22), GREY_40_PERCENT(55), GREY_50_PERCENT(23), GREY_80_PERCENT(
							63), INDIGO(62), LAVENDER(46), LEMON_CHIFFON(26), LIGHT_BLUE(48), LIGHT_CORNFLOWER_BLUE(
									31), LIGHT_GREEN(42), LIGHT_ORANGE(52), LIGHT_TURQUOISE(41), LIGHT_YELLOW(43), LIME(
											50), MAROON(25), OLIVE_GREEN(59), ORANGE(53), ORCHID(28), PALE_BLUE(
													44), PINK(14), PLUM(61), RED(10), ROSE(45), ROYAL_BLUE(
															30), SEA_GREEN(57), SKY_BLUE(40), TAN(
																	47), TEAL(21), TURQUOISE(
																			15), VIOLET(20), WHITE(9), YELLOW(13);

	private short index;

	private Color(short index) {
		this.index = index;
	}

	public short getIndex() {
		return this.index;
	}

	public static Color instance(short index) {
		Color[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			Color e = arr$[i$];
			if (e.getIndex() == index) {
				return e;
			}
		}

		return AUTOMATIC;
	}
}