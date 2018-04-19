package com.hotent.core.excel.editor.font;

import com.hotent.core.excel.editor.IFontEditor;
import com.hotent.core.excel.style.Color;
import com.hotent.core.excel.style.font.Font;

public class FontColorEditor implements IFontEditor {
	private Color color;

	public FontColorEditor() {
		this.color = Color.BLACK;
	}

	public void updateFont(Font font) {
		font.color(this.color);
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}