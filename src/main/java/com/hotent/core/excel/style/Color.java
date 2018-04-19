package com.hotent.core.excel.style;

public enum Color {
	AUTOMATIC((short)64), AQUA((short)49), BLACK((short)8), BLUE((short)12), BLUE_GREY((short)54), BRIGHT_GREEN((short)11), BROWN((short)60), CORAL((short)29), CORNFLOWER_BLUE(
			(short)24), DARK_BLUE((short)18), DARK_GREEN((short)58), DARK_RED((short)16), DARK_TEAL((short)56), DARK_YELLOW((short)19), GOLD(
					(short)51), GREEN((short)17), GREY_25_PERCENT((short)22), GREY_40_PERCENT((short)55), GREY_50_PERCENT((short)23), GREY_80_PERCENT(
							(short)63), INDIGO((short)62), LAVENDER((short)46), LEMON_CHIFFON((short)26), LIGHT_BLUE((short)48), LIGHT_CORNFLOWER_BLUE(
									(short)31), LIGHT_GREEN((short)42), LIGHT_ORANGE((short)52), LIGHT_TURQUOISE((short)41), LIGHT_YELLOW((short)43), LIME(
											(short)50), MAROON((short)25), OLIVE_GREEN((short)59), ORANGE((short)53), ORCHID((short)28), PALE_BLUE(
													(short)44), PINK((short)14), PLUM((short)61), RED((short)10), ROSE((short)45), ROYAL_BLUE(
															(short)30), SEA_GREEN((short)57), SKY_BLUE((short)40), TAN(
																	(short)47), TEAL((short)21), TURQUOISE(
																			(short)15), VIOLET((short)20), WHITE((short)9), YELLOW((short)13);

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