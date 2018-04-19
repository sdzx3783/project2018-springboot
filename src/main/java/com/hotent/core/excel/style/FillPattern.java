package com.hotent.core.excel.style;

public enum FillPattern {
	NO_FILL((short)0), SOLID_FOREGROUND((short)1), FINE_DOTS((short)2), ALT_BARS((short)3), SPARSE_DOTS((short)4), THICK_HORZ_BANDS((short)5), THICK_VERT_BANDS(
			(short)6), THICK_BACKWARD_DIAG((short)7), THICK_FORWARD_DIAG((short)8), BIG_SPOTS((short)9), BRICKS((short)10), THIN_HORZ_BANDS(
					(short)11), THIN_VERT_BANDS((short)12), THIN_BACKWARD_DIAG(
							(short)13), THIN_FORWARD_DIAG((short)14), SQUARES((short)15), DIAMONDS((short)16), LESS_DOTS((short)17), LEAST_DOTS((short)18);

	private short fillPattern;

	private FillPattern(short fillPattern) {
		this.fillPattern = fillPattern;
	}

	public short getFillPattern() {
		return this.fillPattern;
	}
}