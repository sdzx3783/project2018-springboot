package com.hotent.core.excel.editor;

import com.hotent.core.excel.ExcelContext;
import com.hotent.core.excel.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class AbstractEditor {
	protected HSSFWorkbook workBook;
	protected HSSFCellStyle tempCellStyle;
	protected HSSFFont tempFont;
	protected HSSFSheet workingSheet;
	protected ExcelContext ctx;

	protected AbstractEditor(ExcelContext context) {
		this.workBook = context.getWorkBook();
		this.workingSheet = context.getWorkingSheet();
		this.tempFont = context.getTempFont();
		this.tempCellStyle = context.getTempCellStyle();
		this.ctx = context;
	}

	protected HSSFRow getRow(int row) {
		return ExcelUtil.getHSSFRow(this.workingSheet, row);
	}

	protected HSSFCell getCell(int row, int col) {
		return ExcelUtil.getHSSFCell(this.workingSheet, row, col);
	}

	protected HSSFCell getCell(HSSFRow row, int col) {
		return ExcelUtil.getHSSFCell(row, col);
	}
}