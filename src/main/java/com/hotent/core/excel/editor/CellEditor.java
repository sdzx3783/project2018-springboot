package com.hotent.core.excel.editor;

import com.hotent.core.excel.ExcelContext;
import com.hotent.core.excel.editor.AbstractEditor;
import com.hotent.core.excel.editor.ColumnEditor;
import com.hotent.core.excel.editor.IFontEditor;
import com.hotent.core.excel.editor.RowEditor;
import com.hotent.core.excel.editor.SheetEditor;
import com.hotent.core.excel.editor.font.BoldFontEditor;
import com.hotent.core.excel.editor.font.FontColorEditor;
import com.hotent.core.excel.editor.font.FontHeightEditor;
import com.hotent.core.excel.editor.font.ItalicFontEditor;
import com.hotent.core.excel.editor.listener.CellValueListener;
import com.hotent.core.excel.style.Align;
import com.hotent.core.excel.style.BorderStyle;
import com.hotent.core.excel.style.Color;
import com.hotent.core.excel.style.FillPattern;
import com.hotent.core.excel.style.VAlign;
import com.hotent.core.excel.style.font.Font;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddressList;

public class CellEditor extends AbstractEditor {
	private static ItalicFontEditor italicFont = new ItalicFontEditor();
	private static BoldFontEditor boldFont = new BoldFontEditor();
	private static FontColorEditor fontColor = new FontColorEditor();
	private static FontHeightEditor fontHeight = new FontHeightEditor();
	private List<HSSFCell> workingCell;

	public CellEditor(int row, int col, ExcelContext context) {
		this(context);
		this.add(row, col);
	}

	public CellEditor(ExcelContext context) {
		super(context);
		this.workingCell = new ArrayList(2);
	}

	public CellEditor value(Object value) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			this.setCellValue(cell, value, (String) null);
		}

		return this;
	}

	public CellEditor value(Object value, String pattern) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			this.setCellValue(cell, value, pattern);
		}

		return this;
	}

	public Object value() {
		if (this.workingCell.size() == 1) {
			return this.getCellValue((HSSFCell) this.workingCell.get(0));
		} else {
			Object[] vals = new Object[this.workingCell.size()];
			int i = 0;

			HSSFCell cell;
			for (Iterator i$ = this.workingCell.iterator(); i$.hasNext(); vals[i++] = this.getCellValue(cell)) {
				cell = (HSSFCell) i$.next();
			}

			return vals;
		}
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			str.append(cell.toString()).append("\t");
		}

		if (str.length() > 0) {
			str.deleteCharAt(str.length() - 1);
		}

		return str.toString();
	}

	protected CellEditor add(int row, int col) {
		HSSFCell cell = this.getCell(row, col);
		this.workingCell.add(cell);
		return this;
	}

	protected CellEditor add(RowEditor row, int col) {
		HSSFCell cell = this.getCell(row.getHSSFRow(), col);
		this.workingCell.add(cell);
		return this;
	}

	protected CellEditor add(int row, ColumnEditor col) {
		return this.add(row, col.getCol());
	}

	protected CellEditor add(CellEditor cell) {
		this.workingCell.addAll(cell.getWorkingCell());
		return this;
	}

	public CellEditor border(BorderStyle borderStyle, Color borderColor) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setBorderBottom(borderStyle.getBorderType());
			this.tempCellStyle.setBorderTop(borderStyle.getBorderType());
			this.tempCellStyle.setBorderLeft(borderStyle.getBorderType());
			this.tempCellStyle.setBorderRight(borderStyle.getBorderType());
			this.tempCellStyle.setBottomBorderColor(borderColor.getIndex());
			this.tempCellStyle.setTopBorderColor(borderColor.getIndex());
			this.tempCellStyle.setLeftBorderColor(borderColor.getIndex());
			this.tempCellStyle.setRightBorderColor(borderColor.getIndex());
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor borderLeft(BorderStyle borderStyle, Color borderColor) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setBorderLeft(borderStyle.getBorderType());
			this.tempCellStyle.setLeftBorderColor(borderColor.getIndex());
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor borderRight(BorderStyle borderStyle, Color borderColor) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setBorderRight(borderStyle.getBorderType());
			this.tempCellStyle.setRightBorderColor(borderColor.getIndex());
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor borderTop(BorderStyle borderStyle, Color borderColor) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setBorderTop(borderStyle.getBorderType());
			this.tempCellStyle.setTopBorderColor(borderColor.getIndex());
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor borderBottom(BorderStyle borderStyle, Color borderColor) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setBorderBottom(borderStyle.getBorderType());
			this.tempCellStyle.setBottomBorderColor(borderColor.getIndex());
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor font(IFontEditor fontEditor) {
		Map fontCache = this.ctx.getFontCache();

		HSSFCell cell;
		for (Iterator i$ = this.workingCell.iterator(); i$.hasNext(); this.updateCellStyle(cell)) {
			cell = (HSSFCell) i$.next();
			HSSFFont font = cell.getCellStyle().getFont(this.workBook);
			this.copyFont(font, this.tempFont);
			fontEditor.updateFont(new Font(this.tempFont));
			int fontHash = this.tempFont.hashCode() - this.tempFont.getIndex();
			this.tempCellStyle.cloneStyleFrom(cell.getCellStyle());
			if (fontCache.containsKey(Integer.valueOf(fontHash))) {
				this.tempCellStyle.setFont((HSSFFont) fontCache.get(Integer.valueOf(fontHash)));
			} else {
				HSSFFont newFont = this.workBook.createFont();
				this.copyFont(this.tempFont, newFont);
				this.tempCellStyle.setFont(newFont);
				int newFontHash = newFont.hashCode() - newFont.getIndex();
				fontCache.put(Integer.valueOf(newFontHash), newFont);
			}
		}

		return this;
	}

	public CellEditor bold() {
		this.font(boldFont);
		return this;
	}

	public CellEditor fontHeightInPoint(int height) {
		fontHeight.setHeight(height);
		this.font(fontHeight);
		return this;
	}

	public CellEditor color(Color color) {
		fontColor.setColor(color);
		this.font(fontColor);
		return this;
	}

	public CellEditor italic() {
		this.font(italicFont);
		return this;
	}

	public CellEditor bgColor(Color bg) {
		return this.bgColor(bg, FillPattern.SOLID_FOREGROUND);
	}

	public CellEditor bgColor(Color bg, FillPattern fillPattern) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setFillPattern(fillPattern.getFillPattern());
			this.tempCellStyle.setFillForegroundColor(bg.getIndex());
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor align(Align align) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setAlignment(align.getAlignment());
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor vAlign(VAlign align) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setVerticalAlignment(align.getAlignment());
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor warpText(boolean autoWarp) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setWrapText(autoWarp);
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor hidden(boolean hidden) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setHidden(hidden);
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor indent(int indent) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setIndention((short) indent);
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor lock(boolean locked) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setLocked(locked);
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor rotate(int rotation) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			this.tempCellStyle.setRotation((short) rotation);
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor comment(String content) {
		HSSFPatriarch patr = this.ctx.getHSSFPatriarch(this.workingSheet);
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFComment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(),
					cell.getRowIndex(), (short) (cell.getColumnIndex() + 3), cell.getRowIndex() + 4));
			comment.setString(new HSSFRichTextString(content));
			cell.setCellComment(comment);
		}

		return this;
	}

	public CellEditor style(HSSFCellStyle style) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			cell.setCellStyle(style);
		}

		return this;
	}

	public CellEditor dataFormat(String format) {
		short index = HSSFDataFormat.getBuiltinFormat(format);
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFCellStyle style = cell.getCellStyle();
			this.tempCellStyle.cloneStyleFrom(style);
			if (index == -1) {
				HSSFDataFormat dataFormat = this.ctx.getWorkBook().createDataFormat();
				index = dataFormat.getFormat(format);
			}

			this.tempCellStyle.setDataFormat(index);
			this.updateCellStyle(cell);
		}

		return this;
	}

	public CellEditor width(int width) {
		return this.width(new int[]{width});
	}

	protected CellEditor width(int[] widths) {
		int i = -1;
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			if (i >= widths.length - 1) {
				break;
			}

			HSSFSheet arg9999 = this.workingSheet;
			int arg10000 = cell.getColumnIndex();
			++i;
			arg9999.setColumnWidth(arg10000, widths[i]);
		}

		return this;
	}

	public CellEditor addWidth(int width) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			int w = this.workingSheet.getColumnWidth(cell.getColumnIndex());
			this.workingSheet.setColumnWidth(cell.getColumnIndex(), width + w);
		}

		return this;
	}

	public CellEditor height(float height) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFRow row = this.getRow(cell.getRowIndex());
			row.setHeightInPoints(height);
		}

		return this;
	}

	protected CellEditor height(float[] heights) {
		int i = -1;
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			if (i >= heights.length - 1) {
				break;
			}

			HSSFRow row = this.getRow(cell.getRowIndex());
			++i;
			row.setHeightInPoints(heights[i]);
		}

		return this;
	}

	public CellEditor addHeight(float height) {
		Iterator i$ = this.workingCell.iterator();

		while (i$.hasNext()) {
			HSSFCell cell = (HSSFCell) i$.next();
			HSSFRow row = this.getRow(cell.getRowIndex());
			float h = row.getHeightInPoints();
			row.setHeightInPoints(height + h);
		}

		return this;
	}

	public RowEditor row() {
		return new RowEditor(((HSSFCell) this.workingCell.get(0)).getRowIndex(), this.ctx);
	}

	public ColumnEditor colunm() {
		return new ColumnEditor(((HSSFCell) this.workingCell.get(0)).getColumnIndex(), this.ctx);
	}

	public SheetEditor sheet() {
		return new SheetEditor(((HSSFCell) this.workingCell.get(0)).getSheet(), this.ctx);
	}

	public HSSFCell toHSSFCell() {
		return this.workingCell.size() > 0 ? (HSSFCell) this.workingCell.get(0) : null;
	}

	public CellEditor activeCell() {
		if (this.workingCell.size() > 0) {
			((HSSFCell) this.workingCell.get(0)).setAsActiveCell();
		}

		return this;
	}

	private void updateCellStyle(HSSFCell cell) {
		Map styleCache = this.ctx.getStyleCache();
		int tempStyleHash = this.tempCellStyle.hashCode() - this.tempCellStyle.getIndex();
		if (styleCache.containsKey(Integer.valueOf(tempStyleHash))) {
			cell.setCellStyle((HSSFCellStyle) styleCache.get(Integer.valueOf(tempStyleHash)));
		} else {
			HSSFCellStyle newStyle = this.workBook.createCellStyle();
			newStyle.cloneStyleFrom(this.tempCellStyle);
			cell.setCellStyle(newStyle);
			int newStyleHash = newStyle.hashCode() - newStyle.getIndex();
			styleCache.put(Integer.valueOf(newStyleHash), newStyle);
		}

	}

	private void copyFont(HSSFFont src, HSSFFont dest) {
		dest.setBoldweight(src.getBoldweight());
		dest.setCharSet(src.getCharSet());
		dest.setColor(src.getColor());
		dest.setFontHeight(src.getFontHeight());
		dest.setFontHeightInPoints(src.getFontHeightInPoints());
		dest.setFontName(src.getFontName());
		dest.setItalic(src.getItalic());
		dest.setStrikeout(src.getStrikeout());
		dest.setTypeOffset(src.getTypeOffset());
		dest.setUnderline(src.getUnderline());
	}

	private void setCellValue(HSSFCell cell, Object value, String pattern) {
		if (value != null) {
			if (!(value instanceof Double) && !(value instanceof Float) && !(value instanceof Long)
					&& !(value instanceof Integer) && !(value instanceof Short) && !(value instanceof BigDecimal)
					&& !(value instanceof Byte)) {
				if (value instanceof Boolean) {
					cell.setCellValue(((Boolean) value).booleanValue());
					cell.setCellType(4);
				} else if (value != null && value.toString().startsWith("=")) {
					cell.setCellFormula(value.toString().substring(1));
					cell.setCellType(2);
				} else if (value instanceof Date) {
					if (pattern == null || pattern.trim().equals("")) {
						pattern = this.ctx.getDefaultStyle().getDefaultDatePattern();
					}

					cell.setCellValue((Date) value);
				} else {
					cell.setCellValue(new HSSFRichTextString(value == null ? "" : value.toString()));
					cell.setCellType(1);
				}
			} else {
				cell.setCellValue(Double.valueOf(value.toString()).doubleValue());
				cell.setCellType(0);
			}

			if (pattern != null) {
				this.dataFormat(pattern);
			}

			this.invokeListener(cell, value);
		}
	}

	private Object getCellValue(HSSFCell cell) {
		int cellType = cell.getCellType();
		switch (cellType) {
			case 0 :
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue();
				}

				return Double.valueOf(cell.getNumericCellValue());
			case 1 :
				return cell.getRichStringCellValue().toString();
			case 2 :
				return cell.getCellFormula();
			case 3 :
				return "";
			case 4 :
				return Boolean.valueOf(cell.getBooleanCellValue());
			case 5 :
				return Byte.valueOf(cell.getErrorCellValue());
			default :
				return "";
		}
	}

	private void invokeListener(HSSFCell cell, Object value) {
		StackTraceElement[] st = (new Throwable()).getStackTrace();

		try {
			StackTraceElement[] sheetIndex = st;
			int listeners = st.length;

			for (int i$ = 0; i$ < listeners; ++i$) {
				StackTraceElement l = sheetIndex[i$];
				Class[] interfacesList = Class.forName(l.getClassName()).getInterfaces();
				Class[] arr$ = interfacesList;
				int len$ = interfacesList.length;

				for (int i$1 = 0; i$1 < len$; ++i$1) {
					Class clazz = arr$[i$1];
					if (clazz.getSimpleName().equals("CellValueListener")) {
						return;
					}
				}
			}
		} catch (ClassNotFoundException arg12) {
			;
		}

		int arg13 = this.workBook.getSheetIndex(cell.getSheet());
		List arg14 = this.ctx.getListenerList(arg13);
		Iterator arg15 = arg14.iterator();

		while (arg15.hasNext()) {
			CellValueListener arg16 = (CellValueListener) arg15.next();
			arg16.onValueChange(this, value, cell.getRowIndex(), cell.getColumnIndex(), this.ctx.getExcel());
		}

	}

	protected List<HSSFCell> getWorkingCell() {
		return this.workingCell;
	}

	private double null2Double(Object s) {
		double v = 0.0D;
		if (s == null) {
			return v;
		} else {
			try {
				v = Double.parseDouble(s.toString());
			} catch (Exception arg4) {
				;
			}

			return v;
		}
	}

	public void addValidationData(CellRangeAddressList regions, String[] explicitListValues) {
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(explicitListValues);
		HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
		this.workingSheet.addValidationData(data_validation);
	}
}