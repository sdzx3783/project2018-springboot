package com.hotent.core.excel.editor;

import com.hotent.core.excel.ExcelContext;
import com.hotent.core.excel.editor.AbstractEditor;
import com.hotent.core.excel.editor.IPrintSetup;
import com.hotent.core.excel.editor.listener.CellValueListener;
import com.hotent.core.excel.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddressList;

public class SheetEditor extends AbstractEditor {
	private HSSFSheet sheet;
	private int sheetIndex;

	public SheetEditor(HSSFSheet sheet, ExcelContext context) {
		super(context);
		this.sheet = sheet;
		this.sheetIndex = this.workBook.getSheetIndex(this.sheet);
	}

	public SheetEditor header(String left, String center, String right) {
		HSSFHeader header = this.sheet.getHeader();
		header.setLeft(left == null ? "" : left);
		header.setCenter(center == null ? "" : center);
		header.setRight(right == null ? "" : right);
		return this;
	}

	public SheetEditor footer(String left, String center, String right) {
		HSSFFooter footer = this.sheet.getFooter();
		footer.setLeft(left == null ? "" : left);
		footer.setCenter(center == null ? "" : center);
		footer.setRight(right == null ? "" : right);
		return this;
	}

	public SheetEditor sheetName(String name) {
		this.sheetName(name, false);
		return this;
	}

	public SheetEditor sheetName(String name, boolean autoRename) {
		if (autoRename) {
			String newName = new String(name);

			for (HSSFSheet sheet = this.workBook.getSheet(name); sheet != null; sheet = this.workBook
					.getSheet(newName)) {
				newName = newName + "_";
			}

			this.workBook.setSheetName(this.sheetIndex, newName);
		} else {
			this.workBook.setSheetName(this.sheetIndex, name);
		}

		return this;
	}

	public SheetEditor active() {
		this.workBook.setActiveSheet(this.sheetIndex);
		return this;
	}

	public SheetEditor freeze(int row, int col) {
		if (row < 0) {
			row = 0;
		}

		if (col < 0) {
			col = 0;
		}

		this.sheet.createFreezePane(col, row, col, row);
		return this;
	}

	public int getLastRowNum() {
		return ExcelUtil.getLastRowNum(this.sheet);
	}

	public SheetEditor displayGridlines(boolean show) {
		this.sheet.setDisplayGridlines(show);
		return this;
	}

	public SheetEditor printGridlines(boolean newPrintGridlines) {
		this.sheet.setPrintGridlines(newPrintGridlines);
		return this;
	}

	public SheetEditor fitToPage(boolean isFit) {
		this.sheet.setFitToPage(isFit);
		return this;
	}

	public SheetEditor horizontallyCenter(boolean isCenter) {
		this.sheet.setHorizontallyCenter(isCenter);
		return this;
	}

	public SheetEditor password(String pw) {
		this.sheet.protectSheet(pw);
		return this;
	}

	public SheetEditor printSetup(IPrintSetup printSetup) {
		printSetup.setup(this.sheet.getPrintSetup());
		return this;
	}

	public SheetEditor autobreaks(boolean b) {
		this.sheet.setAutobreaks(b);
		return this;
	}

	public SheetEditor addCellValueListener(CellValueListener listener) {
		this.ctx.getListenerList(this.sheetIndex).add(listener);
		return this;
	}

	public SheetEditor removeCellValueListener(CellValueListener listener) {
		this.ctx.getListenerList(this.sheetIndex).remove(listener);
		return this;
	}

	public HSSFSheet toHSSFSheet() {
		return this.sheet;
	}

	public int getSheetIndex() {
		return this.sheetIndex;
	}

	public SheetEditor groupRow(int fromRow, int toRow) {
		this.sheet.groupRow(fromRow, toRow);
		return this;
	}

	public SheetEditor groupColumn(int fromColumn, int toColumn) {
		this.sheet.groupColumn(fromColumn, toColumn);
		return this;
	}

	public SheetEditor setDefaultColumnStyle(int column, String format) {
		HSSFCellStyle cellStyle = this.workBook.createCellStyle();
		HSSFDataFormat dataFormat = this.workBook.createDataFormat();
		cellStyle.setDataFormat(dataFormat.getFormat(format));
		this.sheet.setDefaultColumnStyle(column, cellStyle);
		return this;
	}

	public SheetEditor addValidationData(int firstRow, int column, String[] explicitListValues) {
		this.addValidationData(firstRow, '￿', column, column, explicitListValues);
		return this;
	}

	public SheetEditor addValidationData(int firstRow, int lastRow, int firstColumn, int lastColumn,
			String[] explicitListValues) {
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(explicitListValues);
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstColumn, lastColumn);
		HSSFDataValidation validation = new HSSFDataValidation(regions, constraint);
		validation.setShowErrorBox(true);
		this.sheet.addValidationData(validation);
		return this;
	}
}