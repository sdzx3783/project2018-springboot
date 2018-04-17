package com.hotent.core.bpm.graph;

public enum ShapeType {
	STRAIGHT("straight"), FREE("free"), ORTHOGONAL("orthogonal"), OBLIQUE("oblique");

	private String text;

	private ShapeType(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public String toString() {
		return this.text;
	}

	public static ShapeType fromString(String text) {
		if (text != null) {
			ShapeType[] arr$ = values();
			int len$ = arr$.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				ShapeType type = arr$[i$];
				if (text.equalsIgnoreCase(type.text)) {
					return type;
				}
			}
		}

		return null;
	}
}