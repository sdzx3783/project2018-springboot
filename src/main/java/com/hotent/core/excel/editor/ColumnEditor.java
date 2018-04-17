package com.hotent.core.excel.editor;

import com.hotent.core.excel.ExcelContext;
import com.hotent.core.excel.editor.AbstractRegionEditor;
import com.hotent.core.excel.editor.CellEditor;
import com.hotent.core.excel.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.util.CellRangeAddress;

public class ColumnEditor extends AbstractRegionEditor<ColumnEditor> {
	private int col;
	private int startRow;

	public ColumnEditor(int col, int startRow, ExcelContext context) {
		super(context);
		this.col = 0;
		this.startRow = 0;
		this.col = col;
		this.startRow = startRow;
	}

	public ColumnEditor(int col, ExcelContext context) {
		this(col, 0, context);
	}

	public ColumnEditor value(Object[] rowData) {
		this.value(rowData, this.startRow);
		return this;
	}

	public ColumnEditor value(Object[] rowData, int startRow) {
		if (startRow < 0) {
			startRow = 0;
		}

		this.insertData(rowData, this.col, startRow);
		return this;
	}

	public ColumnEditor autoWidth() {
		this.workingSheet.autoSizeColumn((short) this.col, false);
		this.workingSheet.setColumnWidth(this.col, this.workingSheet.getColumnWidth(this.col) + 1000);
		return this;
	}

	public ColumnEditor height(float[] heights) {
		CellEditor cellEditorLeft = this.newLeftCellEditor();
		cellEditorLeft.height(heights);
		return this;
	}

	public CellEditor cell(int row, int... rows) {
		CellEditor cellEditor = new CellEditor(row, this.col, this.ctx);
		int[] arr$ = rows;
		int len$ = rows.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			int r = arr$[i$];
			cellEditor.add(r, this.col);
		}

		return cellEditor;
	}

	private void insertData(Object[] rowData, int col, int startRow) {
		short i = 0;
		Object[] arr$ = rowData;
		int len$ = rowData.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			Object obj = arr$[i$];
			CellEditor cellEditor = new CellEditor(startRow + i, col, this.ctx);
			cellEditor.value(obj);
			++i;
		}

	}

	protected CellEditor newBottomCellEditor() {
		int lastRowNum = ExcelUtil.getLastRowNum(this.workingSheet);
		CellEditor cellEditor = new CellEditor(this.ctx);
		cellEditor.add(lastRowNum, this.col);
		return cellEditor;
	}

	protected CellEditor newCellEditor() {
		CellEditor cellEditor = new CellEditor(this.ctx);
		int lastRowNum = ExcelUtil.getLastRowNum(this.workingSheet);
		int firstRowNum = this.startRow;

		for (int i = firstRowNum; i <= lastRowNum; ++i) {
			HSSFRow row = this.getRow(i);
			cellEditor.add(row.getRowNum(), this.col);
		}

		return cellEditor;
	}

	protected CellEditor newLeftCellEditor() {
		return this.newCellEditor();
	}

	protected CellEditor newRightCellEditor() {
		return this.newCellEditor();
	}

	protected CellEditor newTopCellEditor() {
		int firstRowNum = this.startRow;
		CellEditor cellEditor = new CellEditor(this.ctx);
		cellEditor.add(firstRowNum, this.col);
		return cellEditor;
	}

	protected CellRangeAddress getCellRange() {
		int firstRowNum = this.startRow;
		int lastRowNum = ExcelUtil.getLastRowNum(this.workingSheet);
		return new CellRangeAddress(firstRowNum, lastRowNum, this.col, this.col);
	}

	protected int getCol() {
		return this.col;
	}
}