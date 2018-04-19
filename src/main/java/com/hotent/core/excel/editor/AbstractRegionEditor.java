package com.hotent.core.excel.editor;

import com.hotent.core.excel.ExcelContext;
import com.hotent.core.excel.editor.AbstractEditor;
import com.hotent.core.excel.editor.CellEditor;
import com.hotent.core.excel.editor.IFontEditor;
import com.hotent.core.excel.style.Align;
import com.hotent.core.excel.style.BorderStyle;
import com.hotent.core.excel.style.Color;
import com.hotent.core.excel.style.FillPattern;
import com.hotent.core.excel.style.VAlign;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

public abstract class AbstractRegionEditor<T> extends AbstractEditor {
	protected AbstractRegionEditor(ExcelContext context) {
		super(context);
	}

	protected abstract CellEditor newCellEditor();

	protected abstract CellEditor newTopCellEditor();

	protected abstract CellEditor newBottomCellEditor();

	protected abstract CellEditor newLeftCellEditor();

	protected abstract CellEditor newRightCellEditor();

	protected abstract CellRangeAddress getCellRange();

	public T borderOuter(BorderStyle borderStyle, Color borderColor) {
		this.borderBottom(borderStyle, borderColor);
		this.borderLeft(borderStyle, borderColor);
		this.borderRight(borderStyle, borderColor);
		this.borderTop(borderStyle, borderColor);
		return (T) this;
	}

	public T borderFull(BorderStyle borderStyle, Color borderColor) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.border(borderStyle, borderColor);
		return (T) this;
	}

	public T borderLeft(BorderStyle borderStyle, Color borderColor) {
		CellEditor cellEditorLeft = this.newLeftCellEditor();
		cellEditorLeft.borderLeft(borderStyle, borderColor);
		return (T) this;
	}

	public T borderRight(BorderStyle borderStyle, Color borderColor) {
		CellEditor cellEditorRight = this.newRightCellEditor();
		cellEditorRight.borderRight(borderStyle, borderColor);
		return (T) this;
	}

	public T borderTop(BorderStyle borderStyle, Color borderColor) {
		CellEditor cellEditorTop = this.newTopCellEditor();
		cellEditorTop.borderTop(borderStyle, borderColor);
		return (T) this;
	}

	public T borderBottom(BorderStyle borderStyle, Color borderColor) {
		CellEditor cellEditorBottom = this.newBottomCellEditor();
		cellEditorBottom.borderBottom(borderStyle, borderColor);
		return (T) this;
	}

	public T font(IFontEditor fontEditor) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.font(fontEditor);
		return (T) this;
	}

	public T bgColor(Color bg) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.bgColor(bg);
		return (T) this;
	}

	public T bgColor(Color bg, FillPattern fillPattern) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.bgColor(bg, fillPattern);
		return (T) this;
	}

	public T align(Align align) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.align(align);
		return (T) this;
	}

	public T vAlign(VAlign align) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.vAlign(align);
		return (T) this;
	}

	public T warpText(boolean autoWarp) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.warpText(autoWarp);
		return (T) this;
	}

	public T merge() {
		this.workingSheet.addMergedRegion(this.getCellRange());
		return (T) this;
	}

	public T style(HSSFCellStyle style) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.style(style);
		return (T) this;
	}

	public T hidden(boolean hidden) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.hidden(hidden);
		return (T) this;
	}

	public T indent(int indent) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.indent(indent);
		return (T) this;
	}

	public T lock(boolean locked) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.lock(locked);
		return (T) this;
	}

	public T rotate(int rotation) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.rotate(rotation);
		return (T) this;
	}

	public T width(int width) {
		CellEditor cellEditor = this.newTopCellEditor();
		cellEditor.width(width);
		return (T) this;
	}

	public T addWidth(int width) {
		CellEditor cellEditor = this.newTopCellEditor();
		cellEditor.addWidth(width);
		return (T) this;
	}

	public T height(float height) {
		CellEditor cellEditor = this.newLeftCellEditor();
		cellEditor.height(height);
		return (T) this;
	}

	public T addHeight(float height) {
		CellEditor cellEditor = this.newLeftCellEditor();
		cellEditor.addHeight(height);
		return (T) this;
	}

	public T dataFormat(String format) {
		CellEditor cellEditor = this.newLeftCellEditor();
		cellEditor.dataFormat(format);
		return (T) this;
	}

	public Object[] value() {
		CellEditor cellEditor = this.newCellEditor();
		return cellEditor.getWorkingCell().size() == 1
				? new Object[]{cellEditor.value()}
				: (Object[]) ((Object[]) cellEditor.value());
	}

	public String toString() {
		CellEditor cellEditor = this.newCellEditor();
		return cellEditor.toString();
	}

	public T bold() {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.bold();
		return (T) this;
	}

	public T color(Color color) {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.color(color);
		return (T) this;
	}

	public T italic() {
		CellEditor cellEditor = this.newCellEditor();
		cellEditor.italic();
		return (T) this;
	}

	public T addValidationData(String[] explicitListValues) {
		CellEditor cellEditor = this.newCellEditor();
		CellRangeAddress cellRange = this.getCellRange();
		CellRangeAddressList regions = new CellRangeAddressList(cellRange.getFirstRow(), cellRange.getLastRow(),
				cellRange.getFirstColumn(), cellRange.getLastColumn());
		cellEditor.addValidationData(regions, explicitListValues);
		return (T) this;
	}
}