package com.hotent.core.excel.editor.font;

import com.hotent.core.excel.editor.IFontEditor;
import com.hotent.core.excel.style.font.BoldWeight;
import com.hotent.core.excel.style.font.Font;

public class BoldFontEditor implements IFontEditor {
	public void updateFont(Font font) {
		font.boldweight(BoldWeight.BOLD);
	}
}