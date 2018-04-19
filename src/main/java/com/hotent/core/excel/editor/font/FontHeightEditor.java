package com.hotent.core.excel.editor.font;

import com.hotent.core.excel.editor.IFontEditor;
import com.hotent.core.excel.style.font.Font;

public class FontHeightEditor implements IFontEditor {
	private int height = 12;

	public void updateFont(Font font) {
		font.fontHeightInPoints(this.height);
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}