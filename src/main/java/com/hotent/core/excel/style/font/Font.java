package com.hotent.core.excel.style.font;

import com.hotent.core.excel.style.Color;
import com.hotent.core.excel.style.font.BoldWeight;
import com.hotent.core.excel.style.font.CharSet;
import com.hotent.core.excel.style.font.TypeOffset;
import com.hotent.core.excel.style.font.Underline;
import org.apache.poi.hssf.usermodel.HSSFFont;

public class Font {
	private HSSFFont font;

	public Font(HSSFFont font) {
		this.font = font;
	}

	public Font boldweight(BoldWeight boldweight) {
		this.font.setBoldweight(boldweight.getWeight());
		return this;
	}

	public BoldWeight boldweight() {
		return BoldWeight.instance(this.font.getBoldweight());
	}

	public Font charSet(CharSet charset) {
		this.font.setCharSet(charset.getCharset());
		return this;
	}

	public CharSet charSet() {
		return CharSet.instance(this.font.getCharSet());
	}

	public Font color(Color color) {
		if (color.equals(Color.AUTOMATIC)) {
			this.font.setColor((short) 32767);
		} else {
			this.font.setColor(color.getIndex());
		}

		return this;
	}

	public Color color() {
		return Color.instance(this.font.getColor());
	}

	public Font fontHeight(int height) {
		this.font.setFontHeight((short) height);
		return this;
	}

	public short fontHeight() {
		return this.font.getFontHeight();
	}

	public Font fontHeightInPoints(int height) {
		this.font.setFontHeightInPoints((short) height);
		return this;
	}

	public short fontHeightInPoints() {
		return this.font.getFontHeightInPoints();
	}

	public Font fontName(String name) {
		this.font.setFontName(name);
		return this;
	}

	public String fontName() {
		return this.font.getFontName();
	}

	public Font italic(boolean italic) {
		this.font.setItalic(italic);
		return this;
	}

	public boolean italic() {
		return this.font.getItalic();
	}

	public Font strikeout(boolean strikeout) {
		this.font.setStrikeout(strikeout);
		return this;
	}

	public boolean strikeout() {
		return this.font.getStrikeout();
	}

	public Font typeOffset(TypeOffset offset) {
		this.font.setTypeOffset(offset.getOffset());
		return this;
	}

	public TypeOffset typeOffset() {
		return TypeOffset.instance(this.font.getTypeOffset());
	}

	public Font underline(Underline underline) {
		this.font.setUnderline(underline.getLine());
		return this;
	}

	public Underline underline() {
		return Underline.instance(this.font.getUnderline());
	}
}