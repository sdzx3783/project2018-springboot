package com.hotent.core.excel;

import com.hotent.core.excel.style.Align;
import com.hotent.core.excel.style.BorderStyle;
import com.hotent.core.excel.style.Color;
import com.hotent.core.excel.style.FillPattern;
import com.hotent.core.excel.style.VAlign;

public class DefaultExcelStyle {
	private short fontSize = 12;
	private String fontName = "宋体";
	private Color backgroundColor;
	private FillPattern fillPattern;
	private Align align;
	private VAlign vAlign;
	private Color fontColor;
	private String defaultDatePattern;
	private Color borderColor;
	private BorderStyle borderStyle;

	public DefaultExcelStyle() {
		this.backgroundColor = Color.AUTOMATIC;
		this.fillPattern = FillPattern.NO_FILL;
		this.align = Align.GENERAL;
		this.vAlign = VAlign.CENTER;
		this.fontColor = Color.AUTOMATIC;
		this.defaultDatePattern = "yyyy-mm-dd hh:mm：ss";
		this.borderColor = Color.AUTOMATIC;
		this.borderStyle = BorderStyle.NONE;
	}

	public Color getBorderColor() {
		return this.borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public BorderStyle getBorderStyle() {
		return this.borderStyle;
	}

	public void setBorderStyle(BorderStyle borderStyle) {
		this.borderStyle = borderStyle;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = (short) fontSize;
	}

	public short getFontSize() {
		return this.fontSize;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public String getFontName() {
		return this.fontName;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	public void setFillPattern(FillPattern fillPattern) {
		this.fillPattern = fillPattern;
	}

	public FillPattern getFillPattern() {
		return this.fillPattern;
	}

	public void setAlign(Align align) {
		this.align = align;
	}

	public Align getAlign() {
		return this.align;
	}

	public void setVAlign(VAlign vAlign) {
		this.vAlign = vAlign;
	}

	public VAlign getVAlign() {
		return this.vAlign;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public Color getFontColor() {
		return this.fontColor;
	}

	public void setDefaultDatePattern(String defaultDatePattern) {
		this.defaultDatePattern = defaultDatePattern;
	}

	public String getDefaultDatePattern() {
		return this.defaultDatePattern;
	}
}